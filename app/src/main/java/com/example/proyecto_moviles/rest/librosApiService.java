package com.example.proyecto_moviles.rest;


import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.Modelo.Request;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface librosApiService {
    @GET("libros/obtener-libros")
    Call<Request> getLibros();
    @GET("libros/obtener-libro/{isbn}")
    Call<Request> obtenerPorISBN(@Path("isbn") String isbn);

    @GET("libros/obtener-libros")
    Call<Request> obtenerRecomendados();
}
