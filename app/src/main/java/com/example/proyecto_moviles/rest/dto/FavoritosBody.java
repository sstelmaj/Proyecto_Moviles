package com.example.proyecto_moviles.rest.dto;

import com.google.gson.annotations.SerializedName;

public class FavoritosBody {
    @SerializedName("fav_usu_id")
    private String fav_usu_id;

    @SerializedName("fav_lib_id")
    private int fav_lib_id;

    public FavoritosBody(String fav_usu_id, int fav_lib_id) {
        this.fav_usu_id = fav_usu_id;
        this.fav_lib_id = fav_lib_id;
    }
}
