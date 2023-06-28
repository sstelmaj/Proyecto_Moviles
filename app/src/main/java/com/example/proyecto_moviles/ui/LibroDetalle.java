package com.example.proyecto_moviles.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.Modelo.Comentario;
import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.comentariosAdapter;
import com.example.proyecto_moviles.adapter.librosAdapter;
import com.example.proyecto_moviles.rest.dao.ObtenerComentarios;
import com.example.proyecto_moviles.rest.librosApiAdapter;
import com.example.proyecto_moviles.utils.OnItemClickListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LibroDetalle extends AppCompatActivity {

    private SharedPreferences prefs;

    private List<Comentario> comentarios;
    private comentariosAdapter adapter;

    private RecyclerView recyclerView;

    Libro item;
    TextView titulo;
    TextView descripcion;

    TextView autor;
    TextView fecha;
    TextView idioma;

    int libroId;


    ImageView imagen;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.libro_detalle);

        recyclerView = findViewById(R.id.detalle_listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent i = getIntent();
        item = (Libro)i.getSerializableExtra("libro");

        titulo = (TextView)findViewById(R.id.detalleTitulo);
        descripcion = (TextView)findViewById(R.id.detalleDescripcion);
        autor = (TextView)findViewById(R.id.detalleAutor);
        fecha = (TextView)findViewById(R.id.detalleCategoria);
        idioma = (TextView)findViewById(R.id.detalleIdioma);
        imagen = (ImageView)findViewById(R.id.detalleImagen);


        titulo.setText(item.getTitulo());
        descripcion.setText(item.getDescripcion());
        autor.setText(item.getAutores());
        fecha.setText(item.getFechaLanzamiento());
        idioma.setText(item.getIdioma());

        libroId = item.getId();

        Picasso.get()
                .load(item.getImagen())
                .into(imagen);
        //Toast.makeText(LibroDetalle.this, item.getTitulo(), Toast.LENGTH_LONG).show();

        connectAndGetApiData();
        //saveBookToLastSeen(item.getTitulo());
    }


    private void saveBookToLastSeen(String bookISBN) {
        prefs = getSharedPreferences("MisPreferencias.UltimosVistos", Context.MODE_PRIVATE);
        String ultimosVistos1 = prefs.getString("ultimosVistos1", null);

        SharedPreferences.Editor editor = prefs.edit();
        if (bookISBN != null && !ultimosVistos1.equals(bookISBN)) {
            if (ultimosVistos1 == null) {
                editor.putString("ultimosVistos1", bookISBN);
            } else {
                editor.putString("ultimosVistos2", ultimosVistos1);
                editor.putString("ultimosVistos1", bookISBN);
            }
        }

        editor.apply();
    }

    public void connectAndGetApiData() {
        ObtenerComentarios body = new ObtenerComentarios(libroId);
        Call<List<Comentario>> call = librosApiAdapter.getApiService().getComentarios(body);
        call.enqueue(new Callback<List<Comentario>>(){
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    List<Comentario> comentarios = response.body();
                    adapter = new comentariosAdapter(comentarios, R.layout.detalle_comentario, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
            }
        });
    }
}
