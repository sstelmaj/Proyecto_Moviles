package com.example.proyecto_moviles.rest.dao;

import com.google.gson.annotations.SerializedName;

public class FavoritosBody {
    @SerializedName("fav_usu_id")
    private int fav_usu_id;

    @SerializedName("fav_lib_id")
    private int fav_lib_id;

    public FavoritosBody(int fav_usu_id, int fav_lib_id) {
        this.fav_usu_id = fav_usu_id;
        this.fav_lib_id = fav_lib_id;
    }
}
