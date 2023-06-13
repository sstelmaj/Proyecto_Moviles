package com.example.proyecto_moviles.rest;


import com.example.proyecto_moviles.Modelo.Libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface librosApiService {
    @GET("libros/obtener-libros")
    Call<List<Libro>> getLibros();
}
