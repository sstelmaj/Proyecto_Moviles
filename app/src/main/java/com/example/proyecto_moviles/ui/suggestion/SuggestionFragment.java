package com.example.proyecto_moviles.ui.suggestion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_moviles.MainActivity;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.HomeRecommendedAdapter;
import com.example.proyecto_moviles.databinding.FragmentHomeBinding;
import com.example.proyecto_moviles.databinding.FragmentSuggestionBinding;
import com.example.proyecto_moviles.domain.Libro;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.InputSugerencia;
import com.example.proyecto_moviles.rest.dto.Request;
import com.example.proyecto_moviles.ui.LibroDetalle;
import com.example.proyecto_moviles.ui.home.HomeFragment;
import com.example.proyecto_moviles.utils.TextValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionFragment extends Fragment {

    private SharedPreferences prefs;
    private FragmentSuggestionBinding binding;
    private boolean isValidForm = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSuggestionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // change the title in the toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("BibliUtec - Agregar Sugerencia");

        binding.txtName.getEditText().addTextChangedListener(new TextValidator(binding.txtName.getEditText()) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.isEmpty()) {
                    binding.txtName.setError("Campo obligatorio");
                    isValidForm = false;
                } else {
                    binding.txtName.setError(null);
                    isValidForm = true;
                }
            }
        });

        binding.txtReason.getEditText().addTextChangedListener(new TextValidator(binding.txtReason.getEditText()) {
            @Override
            public void validate(TextView textView, String text) {
                validateField(binding.txtReason, text);
            }
        });

        binding.btnSendSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarSugerencia();
            }
        });

        return root;
    }

    private void validateField(TextInputLayout textInputLayout, String text) {
        if (text.isEmpty()) {
            textInputLayout.setError("Campo obligatorio");
            isValidForm = false;
        } else {
            textInputLayout.setError(null);
            isValidForm = true;
        }
    }

    private void enviarSugerencia(){
        // VALIDACION DE FORMULARIO
        validateField(binding.txtName, binding.txtName.getEditText().getText().toString());
        validateField(binding.txtReason, binding.txtReason.getEditText().getText().toString());
        if (!isValidForm) return;

        // ENVIO DE SUGERENCIA
        prefs = requireActivity().getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        String usuarioId = prefs.getString("usuarioId", null);
        String name = binding.txtName.getEditText().getText().toString();
        String reason = binding.txtReason.getEditText().getText().toString();

        InputSugerencia inputSugerencia = new InputSugerencia(reason, name, usuarioId);
        sendSuggestion(inputSugerencia);
    }

    private void clearForm() {
        binding.txtName.getEditText().setText("");
        binding.txtIsbn.getEditText().setText("");
        binding.txtLink.getEditText().setText("");
        binding.txtReason.getEditText().setText("");

        binding.txtName.setError(null);
        binding.txtIsbn.setError(null);
        binding.txtLink.setError(null);
        binding.txtReason.setError(null);
    }

    public void sendSuggestion(InputSugerencia inputSugerencia) {
        String token = prefs.getString("token", null);
        Call<Request> call = LibrosApiService.getApiService().sendSuggestion(token, inputSugerencia);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Sugerencia enviada correctamente!", Toast.LENGTH_LONG).show();
                    clearForm();
                } else {
                    Toast.makeText(requireActivity(), "Ha ocurrido un error, vuelva a intentar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Toast.makeText(requireActivity(), "Ha ocurrido un error de servidor, intentelo más tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }

}