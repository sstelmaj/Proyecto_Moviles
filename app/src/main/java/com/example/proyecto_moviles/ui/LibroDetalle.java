package com.example.proyecto_moviles.ui;

import android.content.Context;
import android.content.Intent;
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

public class LibroDetalle extends AppCompatActivity {

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
        Toast.makeText(LibroDetalle.this, item.getTitulo(), Toast.LENGTH_LONG).show();

        connectAndGetApiData();
    }

    public void connectAndGetApiData() {
        Call<List<Comentario>> call = librosApiAdapter.getApiService2(libroId).getComentarios();
        call.enqueue(new Callback<List<Comentario>>(){
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {

                    List<Comentario> comentarios = response.body();

                    System.out.println(comentarios.get(0).getComentario());
                    System.out.println(comentarios.get(1).getComentario());
                    System.out.println(comentarios.get(2).getComentario());
                    System.out.println(comentarios.get(3).getComentario());
                    System.out.println(comentarios.get(4).getComentario());
                    System.out.println(comentarios.get(5).getComentario());
                    System.out.println(comentarios.get(0).getComentario());
                    System.out.println(comentarios.get(1).getComentario());
                    System.out.println(comentarios.get(2).getComentario());
                    System.out.println(comentarios.get(3).getComentario());
                    System.out.println(comentarios.get(4).getComentario());
                    System.out.println(comentarios.get(5).getComentario());
                    System.out.println(comentarios.get(0).getComentario());
                    System.out.println(comentarios.get(1).getComentario());
                    System.out.println(comentarios.get(2).getComentario());
                    System.out.println(comentarios.get(3).getComentario());
                    System.out.println(comentarios.get(4).getComentario());
                    System.out.println(comentarios.get(5).getComentario());

                    /*
                    Request request = response.body();
                    JsonArray datos = request.getData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Comentario> comentarios = null;

                    request.getData().

                    try {
                        comentarios = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<Libro>>() { });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    */

                    adapter = new comentariosAdapter(comentarios, R.layout.detalle_comentario, getApplicationContext());

                    /*
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Comentario item) {
                            if (item != null) {
                                Intent i = new Intent(context, LibroDetalle.class);
                                Log.d("LibroDetalle", "Libro seleccionado: " + item);
                                Log.d("LibroDetalle", "Título: " + item.getTitulo());
                                i.putExtra("libro", item);
                                startActivity(i);
                            } else {
                                Toast.makeText(context, "No se ha seleccionado ningún libro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    */

                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
            }
        });
    }
}
