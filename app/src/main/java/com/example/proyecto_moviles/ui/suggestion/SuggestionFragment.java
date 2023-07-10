package com.example.proyecto_moviles.ui.suggestion;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_moviles.databinding.FragmentSuggestionBinding;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.InputSugerencia;
import com.example.proyecto_moviles.rest.dto.RequestWithDataArray;
import com.example.proyecto_moviles.utils.TextValidator;
import com.google.android.material.textfield.TextInputLayout;

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

        // Ocultar bottom navigation
//        getActivity().findViewById(R.id.menuBottomNav).setVisibility(View.GONE);

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
        Call<RequestWithDataArray> call = LibrosApiService.getApiService().sendSuggestion(token, inputSugerencia);
        call.enqueue(new Callback<RequestWithDataArray>() {
            @Override
            public void onResponse(Call<RequestWithDataArray> call, Response<RequestWithDataArray> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Sugerencia enviada correctamente!", Toast.LENGTH_LONG).show();
                    clearForm();
                } else {
                    Toast.makeText(requireActivity(), "Ha ocurrido un error, vuelva a intentar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestWithDataArray> call, Throwable t) {
                Toast.makeText(requireActivity(), "Ha ocurrido un error de servidor, intentelo más tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }

}