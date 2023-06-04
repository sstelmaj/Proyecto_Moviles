package com.example.proyecto_moviles.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.utils.OnItemClickListener;

import java.util.List;

public class librosAdapter extends RecyclerView.Adapter<librosAdapter.LibroViewHolder> {
    private List<Libro> libros;
    private int rowLayout;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public librosAdapter(List<Libro> libros, int rowLayout, Context context) {
        this.libros = libros;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class LibroViewHolder extends RecyclerView.ViewHolder {
        CardView librosLayout;
        TextView libroTitle;
        TextView data;
        ImageView libroImage;
        public LibroViewHolder(View v) {
            super(v);
            librosLayout = (CardView) v.findViewById(R.id.nombre);
            libroTitle = (TextView) v.findViewById(R.id.titulo);
            data = (TextView) v.findViewById(R.id.autor);
        }
    }
    @Override
    public librosAdapter.LibroViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new LibroViewHolder(view);
    }
    @Override
    public void onBindViewHolder(LibroViewHolder holder, @SuppressLint("RecyclerView") final int position) {
       // String image_url = libros.get(position).getImagen();
        //https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg
        /*Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);*/
        holder.libroTitle.setText(libros.get(position).getTitulo());
        //holder.data.setText(libros.get(position).getDescripcion());
        holder.data.setText(libros.get(position).getAutores());

        //enlazamos el clicklistener al item
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(libros.get(position));
            }
        };
        holder.libroTitle.setOnClickListener(listener);
        holder.data.setOnClickListener(listener);
    }
    @Override
    public int getItemCount() {
        return libros.size();
    }

    //Declaramos el get y set para el clicklistener
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
