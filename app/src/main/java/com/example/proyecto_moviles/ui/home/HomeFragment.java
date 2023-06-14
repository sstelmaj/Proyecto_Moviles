package com.example.proyecto_moviles.ui.home;

import android.content.Intent;
import android.os.Bundle;
//import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.ListAdapter;
import com.example.proyecto_moviles.ListElement;
import com.example.proyecto_moviles.Modelo.Libro;

import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.librosAdapter;
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
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private librosAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.listRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        connectAndGetApiData();

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
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Toast.makeText(requireActivity(), "Error en la llamada a la API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}