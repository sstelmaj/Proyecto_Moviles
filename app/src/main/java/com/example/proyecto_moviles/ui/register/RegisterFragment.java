package com.example.proyecto_moviles.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto_moviles.databinding.FragmentRegisterBinding;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.RegistroUsuarioDto;
import com.example.proyecto_moviles.rest.dto.RequestWithDataArray;
import com.example.proyecto_moviles.ui.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.fabdelgado.ciuy.*;

public class RegisterFragment extends Fragment {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    //Validator validator = new Validator();

    private @NonNull FragmentRegisterBinding binding;

    private boolean isValidForm = false;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        return root;
    }

    private void registrar(){
        // VALIDACION DE FORMULARIO
        validateField(binding.txtDocument, binding.txtDocument.getEditText().getText().toString());
        validateField(binding.txtNombre, binding.txtNombre.getEditText().getText().toString());
        validateField(binding.txtApellido, binding.txtApellido.getEditText().getText().toString());
        validateField(binding.txtMail, binding.txtMail.getEditText().getText().toString());
        validateField(binding.txtTelefono, binding.txtTelefono.getEditText().getText().toString());
        validateField(binding.txtPassword, binding.txtPassword.getEditText().getText().toString());
        validateField(binding.txtPassword2, binding.txtPassword2.getEditText().getText().toString());
        if (!isValidForm) return;

        Log.d("LoginFragment", "Iniciando sesion");


        String documento = binding.txtDocument.getEditText().getText().toString();
        String nombre = binding.txtNombre.getEditText().getText().toString();
        String apellido = binding.txtApellido.getEditText().getText().toString();
        String mail = binding.txtMail.getEditText().getText().toString();
        String telefono = binding.txtTelefono.getEditText().getText().toString();
        String password1 = binding.txtPassword.getEditText().getText().toString();
        String password2 = binding.txtPassword2.getEditText().getText().toString();

        if (!validarPassword(password1, password2)){
            Toast.makeText(((MainActivity) requireActivity()), "Las contraseñas no coinciden, vuelve a intentarlo.", Toast.LENGTH_SHORT).show();
            binding.txtPassword.getEditText().setText("");
            binding.txtPassword2.getEditText().setText("");
            binding.txtPassword.setError("No coinciden");
            binding.txtPassword2.setError("No coinciden");
            return;
        }
        if (!validarMail(mail)){
            Toast.makeText(((MainActivity) requireActivity()), "El mail no es valido.", Toast.LENGTH_SHORT).show();
            binding.txtMail.getEditText().setText("");
            binding.txtMail.setError("No valido");
            return;
        }
        //validarDocumento(documento);

        RegistroUsuarioDto registroUsuarioDto = new RegistroUsuarioDto(documento, nombre, apellido, mail, telefono, password1);
        registroUsuario(registroUsuarioDto);
        return;
    }

    private void registroUsuario(RegistroUsuarioDto datosUsuario) {

        Call<RequestWithDataArray> call = LibrosApiService.getApiService().registrarUsuario(datosUsuario.getDocumento(), datosUsuario.getNombre(), datosUsuario.getApellido(), datosUsuario.getMail(), datosUsuario.getTelefono(), datosUsuario.getClave());
        call.enqueue(new Callback<RequestWithDataArray>() {
            @Override
            public void onResponse(Call<RequestWithDataArray> call, Response<RequestWithDataArray> response) {
                if (response.isSuccessful()) {
                    if(response.body().getCodigo() == 104){
                        Toast.makeText(((MainActivity) requireActivity()), "La cedula no es valida, intente nuevamente", Toast.LENGTH_SHORT).show();
                        binding.txtDocument.getEditText().setText("");
                        binding.txtDocument.setError("No valido");
                    } else if (response.body().getCodigo() == 106){
                        Toast.makeText(((MainActivity) requireActivity()), "El mail no es valido, intente nuevamente", Toast.LENGTH_SHORT).show();
                        binding.txtMail.getEditText().setText("");
                        binding.txtMail.setError("No valido");
                    } else {
                        NavHostFragment.findNavController(RegisterFragment.this).popBackStack();
                    }
                }
            }
            @Override
            public void onFailure(Call<RequestWithDataArray> call, Throwable t) {
                Toast.makeText(((MainActivity) requireActivity()), "Ha ocurrido un error de servidor, intentelo más tarde", Toast.LENGTH_SHORT).show();
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

    private boolean validarPassword(String password1, String password2){
        return (password1.equals(password2));
    }

    private static boolean validarMail(String mail) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
        return matcher.matches();
    }


    /*
    private boolean validarDocumento(String documento){
        return validator.validateCi(documento);
    }

     */

}