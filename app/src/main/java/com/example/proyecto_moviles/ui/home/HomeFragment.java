package com.example.proyecto_moviles.ui.home;

import android.content.Intent;
import android.os.Bundle;
//import android.telecom.Call;
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

import com.example.proyecto_moviles.ListAdapter;
import com.example.proyecto_moviles.ListElement;
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

    private List<ListElement> elementos;

    private RecyclerView recyclerView;
    //private Libro libroPrueba1 = new Libro();
    //private Libro libroPrueba2 = new Libro();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.listRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        connectAndGetApiData();
        //init();

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
                    adapter = new librosAdapter(libros, R.layout.list_element, getActivity());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
            }
        });
    }

    public void init(){
        elementos = new ArrayList<>();
        elementos.add(new ListElement("black", "Sherlock Holmes", "Artur Conan Doyle", "Misterio"));
        elementos.add(new ListElement("black", "Harry Potter", "J.K Rowling", "Fantasia"));
        elementos.add(new ListElement("black", "Eragorn", "Edward Kyle", "Fantasia"));
        elementos.add(new ListElement("black", "La secta", "Camilla Läckberg", "Novela Ligera"));
        elementos.add(new ListElement("black", "Romper el círculo", "Colleen Hoover", "Novela contemporánea"));
        elementos.add(new ListElement("black", "Hábitos atómicos", "James Clear", "Autoayuda"));

        ListAdapter listAdapter = new ListAdapter(elementos, this.getContext());
        RecyclerView recyclerView = binding.listRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(listAdapter);
    }
}