package com.example.proyecto_moviles.domain;

import com.google.gson.annotations.SerializedName;

public class SubCategoria {
    @SerializedName("id")
    int id;
    @SerializedName("nombre")
    String subCategoria;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String subCategoria) {
        this.subCategoria = subCategoria;
    }
}
