package com.example.proyecto_moviles.ui.reservations;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.adapter.ReservationsAdapter;
import com.example.proyecto_moviles.databinding.FragmentReservationsBinding;
import com.example.proyecto_moviles.domain.Libro;
import com.example.proyecto_moviles.domain.LibroUnico;
import com.example.proyecto_moviles.domain.Reserva;
import com.example.proyecto_moviles.rest.LibrosApiService;
import com.example.proyecto_moviles.rest.dto.RequestReservaWithDataArray;
import com.example.proyecto_moviles.ui.MainActivity;
import com.example.proyecto_moviles.ui.home.HomeFragment;
import com.example.proyecto_moviles.utils.OnItemClickListener2;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationsFragment extends Fragment {

    private FragmentReservationsBinding binding;

    private RecyclerView recyclerView;
    private SharedPreferences usuarioLogueadoPreferences;
    private LinearLayoutManager layoutManagerReservas;
    private List<LibroUnico> libros=new ArrayList<>();
    private List<Reserva> reservas;
    private ReservationsAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReservationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        usuarioLogueadoPreferences = ((MainActivity) requireActivity()).getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        layoutManagerReservas = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView = binding.RecyclerViewReservas;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManagerReservas);
        connectAndGetApiDataReservas();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void connectAndGetApiDataReservas() {
        String tokenUsuario=this.usuarioLogueadoPreferences.getString("token",null);
        String idUsuario=this.usuarioLogueadoPreferences.getString("id",null);
        Call<RequestReservaWithDataArray> call = LibrosApiService.getApiService().obtenerReservas("Bearer "+tokenUsuario,idUsuario);
        call.enqueue(new Callback<RequestReservaWithDataArray>(){
            @Override
            public void onResponse(Call<RequestReservaWithDataArray> call, Response<RequestReservaWithDataArray> response) {
                if (response.isSuccessful()) {
                    RequestReservaWithDataArray request = response.body();
                    JsonArray datos = request.getData();
                    System.out.println("Datos: "+datos);

                    ObjectMapper objectMapper = new ObjectMapper();
                    //List<Libro> libros = null;

                    try {
                        reservas= objectMapper.readValue(String.valueOf(datos),new TypeReference<List<Reserva>>() { });
                        libros.clear();
                        if(reservas!=null){
                            for(Reserva r:reservas) {
                                System.out.println("RESERVA ISBN: "+r.getIsbn_libro());
                                connectAndGetApiDataLibrosReservados(r);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<RequestReservaWithDataArray> call, Throwable t) {
                Toast.makeText(((MainActivity) requireActivity()), "Ha ocurrido un error de servidor, intentelo más tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void connectAndGetApiDataLibrosReservados(Reserva r){
        System.out.println("ISBN: "+r.getIsbn_libro());
        Call<RequestReservaWithDataArray> call = LibrosApiService.getApiService().obtenerPorISBN(r.getIsbn_libro());
        call.enqueue(new Callback<RequestReservaWithDataArray>(){
            @Override
            public void onResponse(Call<RequestReservaWithDataArray> call, Response<RequestReservaWithDataArray> response) {
                if (response.isSuccessful()) {
                    RequestReservaWithDataArray request = response.body();
                    System.out.println("RESPUESTA: " + response.body());
                    JsonObject datos = request.getLibro();
                    System.out.println("DATOS LIBRO:" + datos);
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        LibroUnico l = objectMapper.readValue(String.valueOf(datos), new TypeReference<LibroUnico>() {});
                        libros.add(l);
                        adapter=new ReservationsAdapter(libros,R.layout.list_item_libro_reserva,getActivity());
                       adapter.setOnItemClickListener(new OnItemClickListener2() {
                        @Override
                        public void onItemClick(LibroUnico item) {
                            if (item != null) {
                                navigateToDetail(item);
                            } else {
                                Toast.makeText(requireActivity(), "No se ha seleccionado ningún libro", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                        recyclerView.setAdapter(adapter);
                    } catch (IOException e) {
                        System.out.println(e);
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure
                    (Call <RequestReservaWithDataArray> call, Throwable t){
                Toast.makeText(((MainActivity) requireActivity()), "Ha ocurrido un error de servidor, intentelo más tarde", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void navigateToDetail(LibroUnico itemUnico) {
        Libro item=new Libro(itemUnico.getLib_id(),itemUnico.getLib_stock(),itemUnico.getLib_isbn(),itemUnico.getLib_titulo(),itemUnico.getLib_imagen(),itemUnico.getLib_descripcion(),itemUnico.getLib_autores(),itemUnico.getLib_edicion(),itemUnico.getLib_novedades(),itemUnico.getLib_fecha_creado(),itemUnico.getLib_idioma(),itemUnico.getLib_puntuacion(),itemUnico.getLib_vigencia());
        Bundle bundle = new Bundle();
        bundle.putSerializable("libro", item);
        Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.action_menu_reservations_to_menu_book_detail, bundle);
    }
}