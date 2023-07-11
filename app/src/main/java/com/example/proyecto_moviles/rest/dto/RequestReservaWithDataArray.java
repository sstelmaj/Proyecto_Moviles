package com.example.proyecto_moviles.rest.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class RequestReservaWithDataArray {
    @SerializedName("error")
    boolean error;
    @SerializedName("reserva")
    JsonArray data;
    @SerializedName("libro")
    JsonObject libro;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public JsonArray getData() {
        return data;
    }

    public void setData(JsonArray data) {
        this.data = data;
    }

    public JsonObject getLibro() {
        return libro;
    }

    public void setLibro(JsonObject libro) {
        this.libro = libro;
    }
}
