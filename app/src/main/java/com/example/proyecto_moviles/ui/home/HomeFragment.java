package com.example.proyecto_moviles.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.Modelo.Libro;

import com.example.proyecto_moviles.adapter.HomeRecommendedAdapter;
import com.example.proyecto_moviles.adapter.LastSeenAdapter;
import com.example.proyecto_moviles.databinding.FragmentHomeBinding;
import com.example.proyecto_moviles.rest.librosApiAdapter;

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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
        Call<List<Libro>> call = librosApiAdapter.getApiService().getLibros();
        call.enqueue(new Callback<List<Libro>>(){
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful()) {
                    List<Libro> libros=response.body();
                    recommendedAdapter = new HomeRecommendedAdapter(libros, getActivity());
                    rv_recomendados.setAdapter(recommendedAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
            }
        });
    }
}