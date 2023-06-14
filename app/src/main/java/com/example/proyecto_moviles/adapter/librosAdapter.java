package com.example.proyecto_moviles.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
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
            librosLayout = (CardView) v.findViewById(R.id.ultimos_vistos_list);
            libroTitle = (TextView) v.findViewById(R.id.titulo);
            data = (TextView) v.findViewById(R.id.autor);
            libroImage=(ImageView) v.findViewById(R.id.imagen);
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
        String image_url = libros.get(position).getImagen();
        //https://m.media-amazon.com/images/M/MV5BNGNhMDIzZTUtNTBlZi00MTRlLWFjM2ItYzViMjE3YzI5MjljXkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg
        if(image_url.isEmpty()){
            image_url="/ruta/por/defecto/imagen.txt";
        }
        Picasso.get()  //with(context)
                .load(image_url)
                .placeholder(R.drawable.menu_book_fill0_wght400_grad0_opsz48)
                .error(R.drawable.menu_book_fill0_wght400_grad0_opsz48)
                .into(holder.libroImage);
        holder.libroTitle.setText(libros.get(position).getTitulo());
        //holder.data.setText(libros.get(position).getDescripcion());
        holder.data.setText(libros.get(position).getAutores());

        //enlazamos el clicklistener al item
        View.OnClickListener listener = v -> onItemClickListener.onItemClick(libros.get(position));
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
