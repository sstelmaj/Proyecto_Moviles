package com.example.proyecto_moviles.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyecto_moviles.domain.LibroUnico;
import com.example.proyecto_moviles.utils.OnItemClickListener2;
import com.squareup.picasso.Picasso;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.utils.OnItemClickListener;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {
    private List<LibroUnico> libros;
    private int rowLayout;
    private Context context;


    private OnItemClickListener2 onItemClickListener;

    public ReservationsAdapter(List<LibroUnico> libros, int rowLayout, Context context) {
        this.libros = libros;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        CardView librosLayout;
        TextView libroTitle;
        TextView data;
        ImageView libroImage, flechaImage;

        public ReservationViewHolder(View v) {
            super(v);
            librosLayout = (CardView) v.findViewById(R.id.RecyclerViewReservas);
            libroTitle = (TextView) v.findViewById(R.id.titulo);
            data = (TextView) v.findViewById(R.id.descripcion);
            libroImage = (ImageView) v.findViewById(R.id.imagen);
            flechaImage = (ImageView) v.findViewById(R.id.flechaImg);
        }
    }

    @Override
    public ReservationsAdapter.ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String image_url = libros.get(position).getLib_imagen();
        if (image_url.isEmpty()) {
            image_url = "/ruta/por/defecto/imagen.txt";
        }
        Picasso.get()  //with(context)
                .load(image_url)
                .placeholder(R.drawable.menu_book_fill0_wght400_grad0_opsz48)
                .error(R.drawable.menu_book_fill0_wght400_grad0_opsz48)
                .into(holder.libroImage);
        holder.libroTitle.setText(libros.get(position).getLib_titulo());
        holder.data.setText(libros.get(position).getLib_descripcion());


        View.OnClickListener listener = v -> onItemClickListener.onItemClick(libros.get(position));
        holder.libroTitle.setOnClickListener(listener);
        holder.data.setOnClickListener(listener);
        holder.flechaImage.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public OnItemClickListener2 getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener2 onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}