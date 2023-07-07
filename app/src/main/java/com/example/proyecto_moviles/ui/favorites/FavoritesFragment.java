package com.example.proyecto_moviles.ui.favorites;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyecto_moviles.domain.Libro;
import com.example.proyecto_moviles.rest.dto.Request;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.BooksAdapter;
import com.example.proyecto_moviles.databinding.FragmentFavoritesBinding;
//import com.example.proyecto_moviles.databinding.FragmentSearchBinding;
import com.example.proyecto_moviles.rest.LibrosApiService;
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

    private FragmentFavoritesBinding binding;
    private RecyclerView recyclerView;
    private List<Libro> libros;
    private BooksAdapter adapter;


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
                libros.sort((Comparator.comparing(Libro::getTitulo)));
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
        Call<Request> call = LibrosApiService.getApiService().getLibros();
        call.enqueue(new Callback<Request>(){
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    Request request = response.body();
                    JsonArray datos = request.getData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    //List<Libro> libros = null;

                    try {
                        libros = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<Libro>>() { });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    adapter = new BooksAdapter(libros, R.layout.list_item_libro_favorito, getActivity());
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Libro item) {
                            if (item != null) {
                               navigateToDetail(item);
                            } else {
                                Toast.makeText(requireActivity(), "No se ha seleccionado ningún libro", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
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
        List<Libro> librosFiltrados = new ArrayList<>();
        if(libros!=null){
            for(Libro l: libros) {
                if (l.getTitulo().contains(filtro)) {
                    librosFiltrados.add(l);
                }
            }
            adapter = new BooksAdapter(librosFiltrados, R.layout.list_item_libro_favorito, getActivity());
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(Libro item) {
                    if (item != null) {
                        navigateToDetail(item);
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

    private void navigateToDetail(Libro item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("libro", item);
        Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.action_nav_favorites_to_book_details, bundle);
    }
}