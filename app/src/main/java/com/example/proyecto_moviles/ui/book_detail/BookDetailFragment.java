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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.CommentsAdapter;
import com.example.proyecto_moviles.adapter.favoritosAdapter;
import com.example.proyecto_moviles.databinding.FragmentBookDetailBinding;
import com.example.proyecto_moviles.domain.Comentario;
import com.example.proyecto_moviles.domain.Libro;
import com.example.proyecto_moviles.domain.LibroFavorito;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.FavoritosBody;
import com.example.proyecto_moviles.rest.dto.InputObtenerComentarios;
import com.example.proyecto_moviles.rest.dto.InputPostComentario;
import com.example.proyecto_moviles.rest.dto.RequestWithDataArray;
import com.example.proyecto_moviles.utils.OnItemClickListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailFragment extends Fragment {
    private SharedPreferences prefs;
    private FragmentBookDetailBinding binding;

    private List<Comentario> comentarios;
    private CommentsAdapter adapter;
    private List<LibroFavorito> libros = null;
    private LibroFavorito libroFavorito;

    private RecyclerView recyclerView;

    private Libro libro;
    private TextView titulo;
    private TextView descripcion;
    private ImageView imagen;
    private TextView autor;
    private TextView fecha;
    private TextView idioma;
    private boolean esFavorito = false;

    private ImageButton enviarComentario;
    private TextInputEditText inputComentario;
    private String comentario;
    private ToggleButton botonFavorito;


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

        enviarComentario = binding.detalleEnviarComentario;
        inputComentario = binding.detalleInputComentario;
        botonFavorito = binding.detalleFavorito;

        imagen = binding.detalleImagen;
        Picasso.get()
                .load(libro.getImagen())
                .into(imagen);

        connectAndGetApiData();
        saveBookToLastSeen(libro.getIsbn());

        obtenerLibrosFavoritos();

        enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comentario = inputComentario.getText().toString();
                if (comentario.equals("")){
                    inputComentario.setError("Escribe un comentario !");
                } else {
                    postearComentario(comentario);
                    inputComentario.setText("");
                    inputComentario.clearFocus();
                }
            }
        });

        botonFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (botonFavorito.isChecked()){
                    setLibroFavorito();
                } else {
                    eliminarLibroFavorito();
                }
            }
        });

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

    public void postearComentario(String comentario) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));
        String date = dateFormat.format(new Date());

        SharedPreferences userPrefs = requireActivity().getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        String usuarioIdString = userPrefs.getString("id", null);
        int usuarioId = Integer.parseInt(usuarioIdString);

        InputPostComentario inputPostComentario = new InputPostComentario(date, comentario, usuarioId, libro.getId());

        String token = "Bearer " + userPrefs.getString("token", null);

        Call<RequestWithDataArray> call = LibrosApiService.getApiService().postComentario(token, inputPostComentario);
        call.enqueue(new Callback<RequestWithDataArray>() {
            @Override
            public void onResponse(Call<RequestWithDataArray> call, Response<RequestWithDataArray> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Comentario publicado !", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(requireActivity(), "Ha ocurrido un error, vuelva a intentar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestWithDataArray> call, Throwable t) {
                Toast.makeText(requireActivity(), "Ha ocurrido un error de servidor, intentelo más tarde", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void esFavorito(){
        if (libros == null) {
            return;
        }
        for (LibroFavorito l : libros) {
            if (l.getId() == libro.getId()) {
                esFavorito = true;
                botonFavorito.setChecked(true);
                libroFavorito = l;
                return;
            }
        }
    }

    public void obtenerLibrosFavoritos() {
        prefs = requireActivity().getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        String tokenUsuario = prefs.getString("token", null);
        Call<RequestWithDataArray> call = LibrosApiService.getApiService().obtenerFavoritos("Bearer " + tokenUsuario);
        call.enqueue(new Callback<RequestWithDataArray>() {
            @Override
            public void onResponse(Call<RequestWithDataArray> call, Response<RequestWithDataArray> response) {
                if (response.isSuccessful()) {
                    RequestWithDataArray request = response.body();
                    JsonArray datos = request.getData();
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        System.out.println("ENTRO EN ON RESPONSE");
                        System.out.println("ENTRO EN ON RESPONSE");
                        System.out.println("ENTRO EN ON RESPONSE");
                        System.out.println("ENTRO EN ON RESPONSE");
                        System.out.println("ENTRO EN ON RESPONSE");
                        System.out.println("ENTRO EN ON RESPONSE");
                        System.out.println("ENTRO EN ON RESPONSE");
                        System.out.println("ENTRO EN ON RESPONSE");
                        libros = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<LibroFavorito>>() {
                        });
                        esFavorito();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                //for (LibroFavorito l : libros) {
                //    if (l.getId() == libro.getId()) {
                //        //es favorito
                //        botonFavorito.setChecked(true);
                //    }
                //}

            }
            @Override
            public void onFailure(Call<RequestWithDataArray> call, Throwable t) {
            }
        });
    }

    public void setLibroFavorito(){
        prefs = requireActivity().getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        String tokenUsuario = prefs.getString("token", null);
        FavoritosBody favoritosBody=new FavoritosBody(prefs.getString("id", null), libro.getId());
        Call<RequestWithDataArray> call = LibrosApiService.getApiService().agregarFavorito("Bearer "+tokenUsuario, favoritosBody);
        call.enqueue(new Callback<RequestWithDataArray>() {
            @Override
            public void onResponse(Call<RequestWithDataArray> call, Response<RequestWithDataArray> response) {
                System.out.println("CORRECTO AGREGADO A FAVORITO");
                System.out.println("CORRECTO AGREGADO A FAVORITO");
                System.out.println("CORRECTO AGREGADO A FAVORITO");
                System.out.println("CORRECTO AGREGADO A FAVORITO");
            }

            @Override
            public void onFailure(Call<RequestWithDataArray> call, Throwable t) {
                System.out.println("MAL AGREGADO A FAVORITO");
                System.out.println("MAL AGREGADO A FAVORITO");
                System.out.println("MAL AGREGADO A FAVORITO");
                System.out.println("MAL AGREGADO A FAVORITO");
            }
        });
    }

    public void eliminarLibroFavorito(){
        prefs = requireActivity().getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        String tokenUsuario = prefs.getString("token", null);
        if (libroFavorito == null){
            botonFavorito.setChecked(false);
            return;
        }
        Call<RequestWithDataArray> call = LibrosApiService.getApiService().eliminarFavorito("Bearer " + tokenUsuario, libroFavorito.getFav_id());
        call.enqueue(new Callback<RequestWithDataArray>() {
            @Override
            public void onResponse(Call<RequestWithDataArray> call, Response<RequestWithDataArray> response) {
                System.out.println("CORRECTO ELIMINADO A FAVORITO");
                System.out.println("CORRECTO ELIMINADO A FAVORITO");
                System.out.println("CORRECTO ELIMINADO A FAVORITO");
                System.out.println("CORRECTO ELIMINADO A FAVORITO");
            }

            @Override
            public void onFailure(Call<RequestWithDataArray> call, Throwable t) {
                System.out.println("MAL ELIMINADO A FAVORITO");
                System.out.println("MAL ELIMINADO A FAVORITO");
                System.out.println("MAL ELIMINADO A FAVORITO");
                System.out.println("MAL ELIMINADO A FAVORITO");
            }
        });
    }

}