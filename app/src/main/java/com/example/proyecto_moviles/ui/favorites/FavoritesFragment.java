package com.example.proyecto_moviles.ui.favorites;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.Modelo.LibroFavorito;
import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.favoritosAdapter;
import com.example.proyecto_moviles.adapter.librosAdapter;
import com.example.proyecto_moviles.databinding.FragmentFavoritesBinding;
//import com.example.proyecto_moviles.databinding.FragmentSearchBinding;
import com.example.proyecto_moviles.rest.librosApiAdapter;
import com.example.proyecto_moviles.ui.LibroDetalle;
import com.example.proyecto_moviles.utils.OnImageClickListener;
import com.example.proyecto_moviles.utils.OnItemClickListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel mViewModel;

    private FragmentFavoritesBinding binding;
    private RecyclerView recyclerView;
    private List<LibroFavorito> libros;
    private favoritosAdapter adapter;


    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentFavoritesBinding.inflate(inflater,container,false);
        View root= binding.getRoot();
        Button btnOrdenar=binding.btnOrdenar;
        Button btnFiltros=binding.btnFiltrar;
        EditText buscador=binding.etBuscador;
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 2);

        recyclerView = binding.RecyclerViewFavoritos;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        btnOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                libros.sort((Comparator.comparing(LibroFavorito::getTitulo)));
                filtradoDeLibros(buscador.getText().toString());
            }
        });

        btnFiltros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFilterDialog();
            }
        });

        buscador.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                String filtro=buscador.getText().toString();
                filtradoDeLibros(filtro);
            }
        });
        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        connectAndGetApiData();
        return root;
    }
    public void connectAndGetApiData() {
        Call<Request> call = librosApiAdapter.getApiService().obtenerFavoritos("Bearer 64aa1393ba4ed");
        call.enqueue(new Callback<Request>(){
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    Request request = response.body();
                    JsonArray datos = request.getData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    //List<Libro> libros = null;

                    try {
                        libros = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<LibroFavorito>>() { });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    adapter = new favoritosAdapter(libros, R.layout.list_item_libro_favorito, getActivity());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<Request> call, Throwable t) {
            }
        });
    }

    public void filtradoDeLibros(String filtro) {
        //if (!filtro.equals("")) {
        List<LibroFavorito> librosFiltrados = new ArrayList<>();
        if(libros!=null){
            for(LibroFavorito l: libros) {
                if (l.getTitulo().contains(filtro)) {
                    librosFiltrados.add(l);
                }
            }
            adapter = new favoritosAdapter(librosFiltrados, R.layout.list_item_libro_favorito, getActivity());
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
        }else{
            connectAndGetApiData();
        }
    }

    public void showFilterDialog() {
        DialogFiltros filterDialog = new DialogFiltros();
        filterDialog.show(this.getChildFragmentManager(), "filtros_dialog");
    }

    public void eliminarFavorito(int id){
        libros.remove(libros.get(id));
       // Call<Request> call = librosApiAdapter.getApiService().eliminarFavorito("64a4ba06d07e9",id);
        /*call.enqueue(new Callback<Request>(){
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                adapter.changeEstadoFavorito();
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {

            }
        });*/
    }
}