package com.example.proyecto_moviles.rest;



import com.example.proyecto_moviles.domain.Comentario;

import com.example.proyecto_moviles.rest.dto.Request;
import com.example.proyecto_moviles.rest.dto.InputObtenerComentarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;


public interface LibrosApiClient {
    @GET("libros/obtener-libros")
    Call<Request> getLibros();

    @GET("libros/obtener-libro")
    Call<Request> obtenerPorISBN(@Query("isbn") String isbn);

    @GET("libros/obtener-libros")
    Call<Request> obtenerRecomendados();



    @GET("categorias/listado")
    Call<Request>getCategorias();


    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Body InputObtenerComentarios body);

    /*
    @FormUrlEncoded
    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Field("lib_id") int lib_id);
    */


}
