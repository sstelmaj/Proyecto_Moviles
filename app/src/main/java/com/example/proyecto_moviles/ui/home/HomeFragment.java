package com.example.proyecto_moviles.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.Modelo.Libro;


import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.librosAdapter;

import com.example.proyecto_moviles.adapter.HomeRecommendedAdapter;
import com.example.proyecto_moviles.adapter.LastSeenAdapter;

import com.example.proyecto_moviles.databinding.FragmentHomeBinding;
import com.example.proyecto_moviles.rest.librosApiAdapter;
import com.example.proyecto_moviles.ui.LibroDetalle;
import com.example.proyecto_moviles.utils.OnItemClickListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeRecommendedAdapter recommendedAdapter;
    private LastSeenAdapter lastSeenAdapter;
    private List<Libro> librosRecomendados;
    private List<Libro> librosUltimosVistos;
    private RecyclerView rv_recomendados;
    private RecyclerView rv_ultimos_vistos;
    private LinearLayoutManager layoutManagerRecomendados;
    private LinearLayoutManager layoutManagerUltimosVistos;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rv_recomendados = binding.horizontalRecommended;
        rv_ultimos_vistos = binding.ultimosVistosList;

//        connectAndGetApiData();
        librosRecomendados = new ArrayList<>();
        librosRecomendados.add(new Libro("Sherlock Holmes", "Artur Conan Doyle"));
        librosRecomendados.add(new Libro("Harry Potter", "J.K Rowling"));
        librosRecomendados.add(new Libro("Eragorn", "Edward Kyle"));
        librosRecomendados.add(new Libro("La secta", "Camilla Läckberg"));
        librosRecomendados.add(new Libro("Sherlock Holmes 2", "Artur Conan Doyle"));
        librosRecomendados.add(new Libro("Harry Potter 2", "J.K Rowling"));
        librosRecomendados.add(new Libro("Eragorn 2", "Edward Kyle"));
        librosRecomendados.add(new Libro("La secta 2", "Camilla Läckberg"));

        layoutManagerRecomendados = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendedAdapter = new HomeRecommendedAdapter(librosRecomendados, this.getContext());
        rv_recomendados.setLayoutManager(layoutManagerRecomendados);
        rv_recomendados.setAdapter(recommendedAdapter);

        librosUltimosVistos = new ArrayList<>();
        librosUltimosVistos.add(new Libro("Viaje al centro de la tierra", "Julio Verne"));
        librosUltimosVistos.add(new Libro("Memoria del fuego", "Eduardo Galeano"));
        layoutManagerUltimosVistos = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        lastSeenAdapter = new LastSeenAdapter(librosUltimosVistos, this.getContext());
        rv_ultimos_vistos.setLayoutManager(layoutManagerUltimosVistos);
        rv_ultimos_vistos.setAdapter(lastSeenAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void connectAndGetApiData() {
        Call<Request> call = librosApiAdapter.getApiService().getLibros();
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {

                    Request request = response.body();
                    JsonArray datos = request.getData();

                    ObjectMapper objectMapper = new ObjectMapper();

                    List<Libro> libros = null;
                    try {
                        libros = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<Libro>>() {
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(libros.get(0).getTitulo());
                    System.out.println(libros.get(1).getTitulo());
                    System.out.println(libros.get(2).getTitulo());
                    System.out.println(libros.get(3).getTitulo());
                    System.out.println(libros.get(4).getTitulo());
                    System.out.println(libros.get(5).getTitulo());
                    System.out.println(libros.get(6).getTitulo());
                    /*
                    for (JsonElement e : datos){
                        JsonObject dato = e.getAsJsonObject();
                        String id = dato.get("id").getAsString();
                        System.out.println(id);
                        System.out.println(id);
                        System.out.println(id);
                        System.out.println(id);
                    }
                     */

                    recommendedAdapter = new HomeRecommendedAdapter(libros, getActivity());
                    rv_recomendados.setAdapter(recommendedAdapter);
/*
                    List<Libro> libros = response.body();
                    adapter = new librosAdapter(libros, R.layout.list_element, requireActivity());
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Libro item) {
                            if (item != null) {
                                Intent i = new Intent(requireActivity(), LibroDetalle.class);
                                Log.d("LibroDetalle", "Libro seleccionado: " + item);
                                Log.d("LibroDetalle", "Título: " + item.getTitulo());
                                i.putExtra("libro", item);
                                startActivity(i);
                            } else {
                                Toast.makeText(requireActivity(), "No se ha seleccionado ningún libro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireActivity(), "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
*/
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Toast.makeText(requireActivity(), "Error en la llamada a la API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}