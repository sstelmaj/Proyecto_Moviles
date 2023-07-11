package com.example.proyecto_moviles.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.databinding.FragmentLoginBinding;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.DatosUsuarioDto;
import com.example.proyecto_moviles.rest.dto.LoginDataDto;
import com.example.proyecto_moviles.rest.dto.RequestWithDataObject;
import com.example.proyecto_moviles.ui.MainActivity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private SharedPreferences usuarioLogueadoPreferences;
    private FragmentLoginBinding binding;
    private boolean isValidForm = false;
    private NavController navController;

    private SavedStateHandle savedStateHandle;
    public static String LOGIN_SUCCESSFUL = "LOGIN_SUCCESSFUL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        usuarioLogueadoPreferences = ((MainActivity) requireActivity()).getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        navController = Navigation.findNavController(view);
        savedStateHandle = Navigation.findNavController(view)
            .getPreviousBackStackEntry()
            .getSavedStateHandle();
        savedStateHandle.set(LOGIN_SUCCESSFUL, false);

//        ((MainActivity) requireActivity()).getSupportActionBar().hide();


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController = Navigation.findNavController(getView());
                navController.navigate(R.id.registerFragment);
            }
        });
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

    private void iniciarSesion(){
        // VALIDACION DE FORMULARIO
        validateField(binding.txtDocument, binding.txtDocument.getEditText().getText().toString());
        validateField(binding.txtPassword, binding.txtPassword.getEditText().getText().toString());
        if (!isValidForm) return;

        Log.d("LoginFragment", "Iniciando sesion");

        // INICIAR SESION
        String documento = binding.txtDocument.getEditText().getText().toString();
        String clave = binding.txtPassword.getEditText().getText().toString();

        Log.d("LoginFragment", "Documento: " + documento);
        Log.d("LoginFragment", "Clave: " + clave);

        login(documento, clave);
    }

    public void login(String documento, String clave) {
        Call<RequestWithDataObject> call = LibrosApiService.getApiService().login(documento, clave);
        call.enqueue(new Callback<RequestWithDataObject>() {
            @Override
            public void onResponse(Call<RequestWithDataObject> call, Response<RequestWithDataObject> response) {
                if (response.isSuccessful()) {
                    RequestWithDataObject request = response.body();
                    if(request.getCodigo() == 0) {
                        JsonObject datos = request.getData();
                        ObjectMapper objectMapper = new ObjectMapper();
                        LoginDataDto loginData;
                        DatosUsuarioDto datosUsuario;

                        try {
                            loginData = objectMapper.readValue(String.valueOf(datos), new TypeReference<LoginDataDto>() { });
                            datosUsuario = objectMapper.readValue(loginData.getDatosUsuario(), DatosUsuarioDto.class);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        SharedPreferences.Editor editor = usuarioLogueadoPreferences.edit();
                        editor.putString("token", loginData.getToken());
                        editor.putString("id", String.valueOf(datosUsuario.getId()));
                        editor.putString("firstname", datosUsuario.getNombre());
                        editor.putString("lastname", datosUsuario.getApellido());
                        editor.putString("email", datosUsuario.getMail());
                        editor.putString("document", datosUsuario.getDocumento());
                        editor.putString("phone", datosUsuario.getTelefono());
                        editor.apply();
                        clearForm();

                        savedStateHandle.set(LOGIN_SUCCESSFUL, true);
                        NavHostFragment.findNavController(LoginFragment.this).popBackStack();
//                        Navigation.findNavController(((MainActivity) requireActivity()), R.id.mainNavHost).navigate(R.id.fragment_home);
                    } else {
                        String mensaje = request.getMensaje();
                        Toast.makeText(((MainActivity) requireActivity()), mensaje, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(((MainActivity) requireActivity()), "Ha ocurrido un error, vuelva a intentar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RequestWithDataObject> call, Throwable t) {
                Toast.makeText(((MainActivity) requireActivity()), "Ha ocurrido un error de servidor, intentelo más tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        binding.txtDocument.getEditText().setText("");
        binding.txtPassword.getEditText().setText("");

        binding.txtDocument.setError(null);
        binding.txtPassword.setError(null);
    }


    private void checkIfLogged() {
        String userId = usuarioLogueadoPreferences.getString("userId", null);
        Log.d("USER ID", userId);
        if (userId != null) {
            navController.navigate(R.id.fragment_home);
        }
    }
}