package com.example.proyecto_moviles.ui.favorites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.proyecto_moviles.Modelo.Categoria;
import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.librosAdapter;
import com.example.proyecto_moviles.rest.librosApiAdapter;
import com.example.proyecto_moviles.ui.LibroDetalle;
import com.example.proyecto_moviles.utils.OnItemClickListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogFiltros extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = requireContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_filtros,null);

        ChipGroup grupoEtiquetas=dialogView.findViewById(R.id.grupoEtiquetas);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setTitle("Filtros de búsqueda")
                .setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar al hacer clic en el botón "Aplicar"
                    }
                })
                .setNegativeButton("Cancelar", null);

        return builder.create();
    }


    /*public void connectAndGetApiData() {
        Call<Request> call = librosApiAdapter.getApiService().getCategorias();
        call.enqueue(new Callback<Request>(){
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    Request request = response.body();
                    JsonArray datos = request.getData();
                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        List<Categoria> categorias = objectMapper.readValue(String.valueOf(datos), new TypeReference<List<Categoria>>() { });
                        for(Categoria cat: categorias){
                            System.out.println(cat.getNombre());
                        }
                    } catch (IOException e) {
                        System.out.println("ERROR");
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<Request> call, Throwable t) {
            }
        });
    }*/
}
