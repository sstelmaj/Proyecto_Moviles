package com.example.proyecto_moviles.rest;


import com.example.proyecto_moviles.Modelo.Comentario;
import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.rest.dao.ObtenerComentarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface librosApiService {
    @GET("libros/obtener-libros")
    Call<Request> getLibros();


    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Body ObtenerComentarios body);


    /*
    @FormUrlEncoded
    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Field("lib_id") int lib_id);
    */


}
