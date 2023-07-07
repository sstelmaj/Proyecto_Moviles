package com.example.proyecto_moviles.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.HomeRecommendedAdapter;
import com.example.proyecto_moviles.adapter.LastSeenAdapter;
import com.example.proyecto_moviles.databinding.FragmentHomeBinding;
import com.example.proyecto_moviles.domain.Libro;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.Request;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private SharedPreferences prefs;
    private FragmentHomeBinding binding;
    private HomeRecommendedAdapter recommendedAdapter;
    private LastSeenAdapter lastSeenAdapter;
    private RecyclerView rv_recomendados;
    private RecyclerView rv_ultimos_vistos;
    private LinearLayoutManager layoutManagerRecomendados;
    private LinearLayoutManager layoutManagerUltimosVistos;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.fabAddSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_home_to_add_suggestion);
            }
        });

        rv_recomendados = binding.horizontalRecommended;
        rv_ultimos_vistos = binding.ultimosVistosList;
        loadRecommendedBooks();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        prefs = requireActivity().getSharedPreferences("MisPreferencias.UltimosVistos", Context.MODE_PRIVATE);
        String ultimosVistos1 = prefs.getString("ultimosVistos1", null);
        String ultimosVistos2 = prefs.getString("ultimosVistos2", null);
        loadLastSeenBooks(ultimosVistos1, ultimosVistos2);
    }

    public void loadRecommendedBooks() {
        Call<Request> call = LibrosApiService.getApiService().obtenerRecomendados();
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    Request request = response.body();
                    JsonArray datos = request.getData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Libro> libros = null;

                    try {
                        libros = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<Libro>>() { });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    libros = libros.subList(0, 5);

                    layoutManagerRecomendados = new LinearLayoutManager(HomeFragment.this.getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recommendedAdapter = new HomeRecommendedAdapter(libros, getActivity());
                    rv_recomendados.setLayoutManager(layoutManagerRecomendados);
                    recommendedAdapter.setOnItemClickListener(item -> {
                        if (item != null) {
                            navigateToBookDetail(item);
                        } else {
                            Toast.makeText(requireActivity(), "No se ha seleccionado ningún libro", Toast.LENGTH_SHORT).show();
                        }
                    });
                    rv_recomendados.setAdapter(recommendedAdapter);
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

    public void loadLastSeenBooks(String isbn1, String isbn2) {

        Call<Request> call = LibrosApiService.getApiService().getLibros();
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    Request request = response.body();
                    JsonArray datos = request.getData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Libro> libros;

                    try {
                        libros = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<Libro>>() { });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    List<Libro> librosUltimosVistos = new ArrayList<>();
                    for (Libro libro : libros) {
                        if (libro.getIsbn().equals(isbn1) || libro.getIsbn().equals(isbn2)) {
                            librosUltimosVistos.add(libro);
                            librosUltimosVistos.sort((o1, o2) -> o1.getIsbn().equals(isbn1) ? -1 : 1);
                        }
                    }

                    layoutManagerUltimosVistos = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    lastSeenAdapter = new LastSeenAdapter(librosUltimosVistos, getContext());
                    rv_ultimos_vistos.setLayoutManager(layoutManagerUltimosVistos);
                    lastSeenAdapter.setOnItemClickListener(item -> {
                        if (item != null) {
                            navigateToBookDetail(item);
                        } else {
                            Toast.makeText(requireActivity(), "No se ha seleccionado ningún libro", Toast.LENGTH_SHORT).show();
                        }
                    });
                    rv_ultimos_vistos.setAdapter(lastSeenAdapter);

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

    public void navigateToBookDetail(Libro libro) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("libro", libro);
        Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.action_nav_home_to_book_detail, bundle);
    }
}