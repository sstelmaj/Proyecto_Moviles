package com.example.proyecto_moviles.rest;


import com.example.proyecto_moviles.Modelo.Comentario;
import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.Modelo.Request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface librosApiService {
    @GET("libros/obtener-libros")
    Call<Request> getLibros();

    @GET("comentarios?comet_lib_id=")
    Call<List<Comentario>> getComentarios();
}
