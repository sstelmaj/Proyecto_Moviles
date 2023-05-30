package com.example.proyecto_moviles.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto_moviles.Modelo.Libro;

import com.example.proyecto_moviles.databinding.FragmentHomeBinding;

import java.util.Date;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private TextView titulo;

    private Libro libroPrueba1 = new Libro();
    private Libro libroPrueba2= new Libro();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        libroPrueba1.setId(1);
        libroPrueba1.setIsbn("978-8490353787");
        libroPrueba1.setTitulo("Introducción a la programación con Python");
        libroPrueba1.setDescripcion("Este libro es una introducción a la programación en Python, un lenguaje de programación muy popular y poderoso que se utiliza en una gran variedad de campos, desde la informática hasta la biología.");
        libroPrueba1.setImagen("https://m.media-amazon.com/images/I/41QZyv4q+GL._SY346_.jpg");
        libroPrueba1.setCategoria(1);
        libroPrueba1.setSubCategoria(3);
        libroPrueba1.setUrl("https://www.amazon.com/-/es/Nilo-Ney-Coutinho-Menezes-ebook/dp/B072YX6K8R");
        libroPrueba1.setStock(10);
        libroPrueba1.setAutores("John Guttag");
        libroPrueba1.setEdicion("3");
        //libroPrueba1.setFechaLanzamiento( (Date)"2015-07-28");        "lib_fecha_lanzamiento": "2015-07-28",
        libroPrueba1.setNovedades('N');
        libroPrueba1.setIdioma("Español");
        libroPrueba1.setDisponible('N');
        libroPrueba1.setVigente('S');
        libroPrueba1.setPuntuacion(4.7);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}