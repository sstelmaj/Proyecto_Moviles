package com.example.proyecto_moviles.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.databinding.FragmentProfileBinding;
import com.example.proyecto_moviles.ui.MainActivity;

public class ProfileFragment extends Fragment {
    private SharedPreferences usuarioLogueadoPreferences;
    private FragmentProfileBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        usuarioLogueadoPreferences = ((MainActivity) requireActivity()).getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkIfLogged();

        String nombre = usuarioLogueadoPreferences.getString("firstname", null);
        String apellido = usuarioLogueadoPreferences.getString("lastname", null);
        String email = usuarioLogueadoPreferences.getString("email", null);
        String documento = usuarioLogueadoPreferences.getString("document", null);
        String telefono = usuarioLogueadoPreferences.getString("phone", null);

        binding.profileName.setText(nombre + " " + apellido);
        binding.txtFirstname.setText(nombre);
        binding.txtLastname.setText(apellido);
        binding.txtEmail.setText(email);
        binding.txtCi.setText(documento);
        binding.txtPhone.setText(telefono);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }

    private void cerrarSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setMessage("¿Estás seguro que deseas cerrar sesión?")
                .setCancelable(false)
                .setPositiveButton("Sí", (dialog, id) -> {
                    // User clicked OK button
                    logout();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    // User cancelled the dialog
                    dialog.cancel();
                })
                .create()
                .show();
    }

    private void logout() {
        SharedPreferences.Editor editor = usuarioLogueadoPreferences.edit();
        editor.clear();
        editor.apply();

        navController.navigate(R.id.login);
    }

    private void checkIfLogged() {
        navController = Navigation.findNavController(getView());
        String token = usuarioLogueadoPreferences.getString("token", null);
        if (token == null) {
            navController.navigate(R.id.login);
        }
    }
}