package com.example.proyecto_moviles.rest;



import com.example.proyecto_moviles.domain.Comentario;
import com.example.proyecto_moviles.rest.dto.FavoritosBody;
import com.example.proyecto_moviles.rest.dto.DatosUsuarioDto;
import com.example.proyecto_moviles.rest.dto.InputPostComentario;
import com.example.proyecto_moviles.rest.dto.InputSugerencia;
import com.example.proyecto_moviles.rest.dto.RequestReservaWithDataArray;
import com.example.proyecto_moviles.rest.dto.RegistroUsuarioDto;
import com.example.proyecto_moviles.rest.dto.RequestWithDataArray;
import com.example.proyecto_moviles.rest.dto.InputObtenerComentarios;
import com.example.proyecto_moviles.rest.dto.RequestWithDataObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface LibrosApiClient {
    @GET("libros/obtener-libros")
    Call<RequestWithDataArray> getLibros();

    @GET("libros/obtener-libro")
    Call<RequestReservaWithDataArray> obtenerPorISBN(@Query("isbn") String isbn);

    @GET("libros/obtener-libros")
    Call<RequestWithDataArray> obtenerRecomendados();

    @GET("categorias/listado")
    Call<RequestWithDataArray>getCategorias();

    @FormUrlEncoded
    @POST("usuarios/login")
    Call<RequestWithDataObject> login(@Field("documento") String documento, @Field("clave") String clave);

    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Body InputObtenerComentarios body);

    @POST("/sugerencias")
    Call<RequestWithDataArray> sendSuggestion(@Header("Authorization") String token, @Body InputSugerencia body);


    @DELETE("favoritos/delete")
    Call<RequestWithDataArray> eliminarFavorito(@Header("Authorization") String token, @Query("id") int idLibro);

    @POST("favoritos/create")
    Call<RequestWithDataArray> agregarFavorito(@Header("Authorization") String token, @Body FavoritosBody favoritosBody);

    @GET("favoritos/obtener-favoritos")
    Call<RequestWithDataArray> obtenerFavoritos(@Header("Authorization") String token);

    @GET("reservas/obtener")
    Call<RequestReservaWithDataArray> obtenerReservas(@Header("Authorization") String token, @Query("id") String idUsuario);

    
    @POST("/comentarios")
    Call<RequestWithDataArray> postComentario(@Header("Authorization") String token, @Body InputPostComentario body);

    @FormUrlEncoded
    @POST("/usuarios/registro")
    Call<RequestWithDataArray> registrarUsuario(
            @Field("documento") String documento,
            @Field("nombre") String nombre,
            @Field("apellido") String apellido,
            @Field("mail") String mail,
            @Field("telefono") String telefono,
            @Field("clave") String clave
    );

    /*
    @POST("/usuarios/registro")
    Call<RequestWithDataArray> registrarUsuario(@Body RegistroUsuarioDto body);
    */


    /*

    @POST("comentarios/vigentes")
    Call<List<Comentario>> getComentarios(@Field("lib_id") int lib_id);
    */


}
