package com.example.proyecto_moviles.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.Modelo.Libro;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.librosAdapter;
import com.example.proyecto_moviles.databinding.FragmentHomeBinding;
import com.example.proyecto_moviles.rest.librosApiAdapter;
import com.example.proyecto_moviles.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    //private Retrofit retrofit;
    private librosAdapter adapter;

    private RecyclerView recyclerView;
    private Libro libroPrueba1 = new Libro();
    private Libro libroPrueba2 = new Libro();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        /*libroPrueba1.setId(1);
        libroPrueba1.setIsbn("978-8490353787");
        libroPrueba1.setTitulo("Introducción a la programación con Python");
        libroPrueba1.setDescripcion("Este libro es una introducción a la programación en Python, un lenguaje de programación muy popular y poderoso que se utiliza en una gran variedad de campos, desde la informática hasta la biología.");
        libroPrueba1.setImagen("https://m.media-amazon.com/images/I/41QZyv4q+GL._SY346_.jpg");
        libroPrueba1.setCategoria(1);
        libroPrueba1.setSubCategoria(3);
        libroPrueba1.setUrl("https://www.amazon.com/-/es/Nilo-Ney-Coutinho-Menezes-ebook/dp/B072YX6K8R");
        libroPrueba1.setStock(10);
        libroPrueba1.setAutores("John Guttag");
        libroPrueba1.setEdicion("3");
        //libroPrueba1.setFechaLanzamiento( (Date)"2015-07-28");        "lib_fecha_lanzamiento": "2015-07-28",
        libroPrueba1.setNovedades('N');
        libroPrueba1.setIdioma("Español");
        libroPrueba1.setDisponible('N');
        libroPrueba1.setVigente('S');
        libroPrueba1.setPuntuacion(4.7);

        final TextView titulo = binding.textView2;
        titulo.setText(libroPrueba1.getTitulo());*/
        connectAndGetApiData();


        /*List<Libro> libros = new ArrayList<>();
        libros.add(libroPrueba1);
        libros.add(libroPrueba1);*/

        //enlazo el clicklistener al adapter
        /*adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Libro item) {
                //Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, MovieDetail.class);
                i.putExtra("pelicula", item);

                startActivity(i);
                //finish(); //Si quiero cerrar la actividad y quitarla del stack

            }
        });
        recyclerView.setAdapter(adapter);*/

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void connectAndGetApiData() {
        Call<List<Libro>> call = librosApiAdapter.getApiService().getLibros();
        call.enqueue(new Callback<List<Libro>>(){
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful()) {
                    List<Libro> libros=response.body();
                    adapter = new librosAdapter(libros, R.layout.list_item_libro, getActivity());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
            }
        });
    }
}