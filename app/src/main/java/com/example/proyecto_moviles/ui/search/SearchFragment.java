package com.example.proyecto_moviles.ui.search;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.proyecto_moviles.ListElement;
import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.librosAdapter;
import com.example.proyecto_moviles.databinding.FragmentHomeBinding;
import com.example.proyecto_moviles.databinding.FragmentSearchBinding;
import com.example.proyecto_moviles.rest.librosApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;

    private FragmentSearchBinding binding;

    private List<Libro> libros;
    private String tipoFiltro="Todo";

    private librosAdapter adapter;

    private RecyclerView recyclerView;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText buscador=binding.etBuscador;
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 2);

        recyclerView = binding.RecyclerViewSearch;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        connectAndGetApiData();
        //init();
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

        RadioGroup buttonGroup = binding.buttonGroup;
        buttonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Deseleccionar los demás botones
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    radioButton.setChecked(radioButton.getId() == checkedId);
                }

                // Aplicar acciones según el botón seleccionado
                switch (checkedId) {
                    case R.id.buttonTodo:
                        tipoFiltro="Todo";
                        break;
                    case R.id.buttonTitulo:
                        tipoFiltro="Titulo";
                        break;
                    case R.id.buttonAutor:
                        tipoFiltro="Autor";
                        break;
                    case R.id.buttonMateria:
                        tipoFiltro="Materia";
                        break;
                }
            }
        });
        return root;
    }

    public void connectAndGetApiData() {
        Call<List<Libro>> call = librosApiAdapter.getApiService().getLibros();
        call.enqueue(new Callback<List<Libro>>(){
            @Override
            public void onResponse(@NonNull Call<List<Libro>> call, @NonNull Response<List<Libro>> response) {
                if (response.isSuccessful()) {
                    libros=response.body();
                    adapter = new librosAdapter(libros, R.layout.list_item_libro, getActivity());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {
            }
        });
    }

    public void filtradoDeLibros(String filtro) {
        //if (!filtro.equals("")) {
            List<Libro> librosFiltrados = new ArrayList<>();
            for (Libro l : libros) {
                if(tipoFiltro.equals("Todo")){
                    if(l.getTitulo().contains(filtro)||l.getAutores().contains(filtro)){
                        librosFiltrados.add(l);
                    };
                }else if(tipoFiltro.equals("Titulo")){
                    if (l.getTitulo().contains(filtro)) {
                        librosFiltrados.add(l);
                    }
                }else{
                    if (l.getAutores().contains(filtro)) {
                        librosFiltrados.add(l);
                    }
                }
            }
            adapter = new librosAdapter(librosFiltrados, R.layout.list_item_libro, getActivity());
            recyclerView.setAdapter(adapter);
        }
    //}

}