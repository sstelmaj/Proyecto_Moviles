package com.example.proyecto_moviles.ui.book_detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.CommentsAdapter;
import com.example.proyecto_moviles.databinding.FragmentBookDetailBinding;
import com.example.proyecto_moviles.domain.Comentario;
import com.example.proyecto_moviles.domain.Libro;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.InputObtenerComentarios;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailFragment extends Fragment {
    private SharedPreferences prefs;
    private FragmentBookDetailBinding binding;

    private List<Comentario> comentarios;
    private CommentsAdapter adapter;

    private RecyclerView recyclerView;

    private Libro libro;
    private TextView titulo;
    private TextView descripcion;
    private ImageView imagen;
    private TextView autor;
    private TextView fecha;
    private TextView idioma;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                libro = getArguments().getSerializable("libro", Libro.class);
            else
                libro = (Libro) getArguments().getSerializable("libro");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.detalleListRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        titulo = binding.detalleTitulo;
        titulo.setText(libro.getTitulo());

        descripcion = binding.detalleDescripcion;
        descripcion.setText(libro.getDescripcion());

        autor = binding.detalleAutor;
        autor.setText(libro.getAutores());

        fecha = binding.detalleCategoria;
        fecha.setText(libro.getFechaLanzamiento());

        idioma = binding.detalleIdioma;
        idioma.setText(libro.getIdioma());

        imagen = binding.detalleImagen;
        Picasso.get()
                .load(libro.getImagen())
                .into(imagen);

        connectAndGetApiData();
        saveBookToLastSeen(libro.getIsbn());

        return root;
    }

    private void saveBookToLastSeen(String bookISBN) {
        prefs = getActivity().getSharedPreferences("MisPreferencias.UltimosVistos", Context.MODE_PRIVATE);
        String ultimosVistos1 = prefs.getString("ultimosVistos1", "");
        String ultimosVistos2 = prefs.getString("ultimosVistos2", "");

        SharedPreferences.Editor editor = prefs.edit();
        if (!ultimosVistos1.equals(bookISBN)) {
            editor.putString("ultimosVistos2", ultimosVistos1);
        } else {
            editor.putString("ultimosVistos2", ultimosVistos2);
        }
        editor.putString("ultimosVistos1", bookISBN);
        editor.apply();
    }

    public void connectAndGetApiData() {
        InputObtenerComentarios body = new InputObtenerComentarios(libro.getId());
        Call<List<Comentario>> call = LibrosApiService.getApiService().getComentarios(body);
        call.enqueue(new Callback<List<Comentario>>(){
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful()) {
                    List<Comentario> comentarios = response.body();
                    adapter = new CommentsAdapter(comentarios, R.layout.comment, getActivity().getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
            }
        });
    }
}