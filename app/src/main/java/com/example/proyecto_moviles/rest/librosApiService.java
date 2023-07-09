package com.example.proyecto_moviles.rest;



import com.example.proyecto_moviles.Modelo.Categoria;

import com.example.proyecto_moviles.Modelo.Comentario;

import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.rest.dao.FavoritosBody;
import com.example.proyecto_moviles.rest.dao.ObtenerComentarios;

import java.util.List;

import okhttp3.Authenticator;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.Header;
import retrofit2.http.Path;

import retrofit2.http.POST;
import retrofit2.http.Query;


public interface librosApiService {
    @GET("libros/obtener-libros")
    Call<Request> getLibros();

    @GET("libros/obtener-libro/{isbn}")
    Call<Request> obtenerPorISBN(@Path("isbn") String isbn);

    @GET("libros/obtener-libros")
    Call<Request> obtenerRecomendados();



    @GET("categorias/listado")
    Call<Request>getCategorias();


    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Body ObtenerComentarios body);

    @DELETE("favoritos/delete")
    Call<Request> eliminarFavorito(@Header("Authorization") String token, @Query("id") int idLibro);

    @POST("favoritos/create")
    Call<Request> agregarFavorito(@Header("Authorization") String token, @Body FavoritosBody favoritosBody);

    @GET("favoritos/obtener-favoritos")
    Call<Request> obtenerFavoritos(@Header("Authorization") String token);

    /*
    @FormUrlEncoded
    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Field("lib_id") int lib_id);
    */


}
