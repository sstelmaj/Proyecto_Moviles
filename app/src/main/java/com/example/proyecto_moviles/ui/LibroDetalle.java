package com.example.proyecto_moviles.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LibroDetalle extends AppCompatActivity {

    private SharedPreferences prefs;
    Libro item;
    TextView titulo;
    TextView descripcion;

    TextView autor;
    TextView fecha;
    TextView idioma;

    private Context context;

    ImageView imagen;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.libro_detalle);

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

        Picasso.get()
                .load(item.getImagen())
                .into(imagen);
        Toast.makeText(LibroDetalle.this, item.getTitulo(), Toast.LENGTH_LONG).show();

        saveBookToLastSeen(item.getTitulo());
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
}
