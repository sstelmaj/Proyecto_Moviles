package com.example.proyecto_moviles.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.proyecto_moviles.Modelo.LibroFavorito;
import com.example.proyecto_moviles.Modelo.Request;
import com.example.proyecto_moviles.rest.dao.FavoritosBody;
import com.example.proyecto_moviles.rest.librosApiAdapter;
import com.example.proyecto_moviles.utils.OnImageClickListener;
import com.squareup.picasso.Picasso;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.utils.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class favoritosAdapter extends RecyclerView.Adapter<favoritosAdapter.FavoritoViewHolder> {
    private List<LibroFavorito> libros;
    private int rowLayout;
    private Context context;

    private ImageView imgFavorito;


    private OnItemClickListener onItemClickListener;
    private OnImageClickListener onImageClickListener;

    public favoritosAdapter(List<LibroFavorito> libros, int rowLayout, Context context) {
        this.libros = libros;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class FavoritoViewHolder extends RecyclerView.ViewHolder {
        CardView librosLayout;
        TextView libroTitle;
        TextView data;
        ImageView libroImage,favImage;
        int imagenCorazonVacio;  // Guarda el recurso de la imagen del corazón vacío
        int imagenCorazonRelleno;  // Guarda el recurso de la imagen del corazón relleno
        boolean favorito;  // Guarda el estado actual del favorito
        public FavoritoViewHolder(View v, int imagenCorazonVacio, int imagenCorazonRelleno) {
            super(v);
            librosLayout = (CardView) v.findViewById(R.id.ultimos_vistos_list);
            libroTitle = (TextView) v.findViewById(R.id.titulo);
            data = (TextView) v.findViewById(R.id.autor);
            libroImage=(ImageView) v.findViewById(R.id.imagen);
            favImage=(ImageView) v.findViewById(R.id.favorito);
            this.imagenCorazonVacio = imagenCorazonVacio;
            this.imagenCorazonRelleno = imagenCorazonRelleno;
        }
    }
    @Override
    public favoritosAdapter.FavoritoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FavoritoViewHolder(view,R.drawable.favorite_fill0_wght400_grad0_opsz48 ,R.drawable.favoritosvg);
    }

    @Override
    public void onBindViewHolder(FavoritoViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String image_url = libros.get(position).getImagen();
        if(image_url.isEmpty()){
            image_url="/ruta/por/defecto/imagen.txt";
        }
        Picasso.get()  //with(context)
                .load(image_url)
                .placeholder(R.drawable.menu_book_fill0_wght400_grad0_opsz48)
                .error(R.drawable.menu_book_fill0_wght400_grad0_opsz48)
                .into(holder.libroImage);
        holder.libroTitle.setText(libros.get(position).getTitulo());
        holder.data.setText(libros.get(position).getAutores());
        this.imgFavorito=holder.favImage;

        //enlazamos el clicklistener al item
        holder.favImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.favorito = !holder.favorito;  // Cambia el estado del favorito al hacer clic

                // Actualiza la imagen del corazón según el estado del favorito
                if (holder.favorito) {
                    holder.favImage.setImageResource(holder.imagenCorazonRelleno);
                    cambiarEstadoFavorito(holder.favorito,position);
                } else {
                    holder.favImage.setImageResource(holder.imagenCorazonVacio);
                    cambiarEstadoFavorito(holder.favorito,position);
                }

                // Llama a la función agregarFavorito() según tus necesidades
               // agregarFavorito();
                /*if (onImageClickListener != null) {
                    onImageClickListener.onImageClick(libros.get(position));
                }*/
            }
        });



        /*View.OnClickListener listener = v -> onItemClickListener.onItemClick(libros.get(position));
        holder.libroTitle.setOnClickListener(listener);
        holder.data.setOnClickListener(listener);
        View.OnClickListener listener = v -> onItemClickListener.onItemClick(libros.get(position));
        holder.favImage.setOnClickListener(listener);*/
    }
    @Override
    public int getItemCount() {
        return libros.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    //Declaramos el get y set para el clicklistener
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void cambiarEstadoFavorito(boolean estado, int position) {
        if (estado) {
            Call<Request> call = librosApiAdapter.getApiService().eliminarFavorito("Bearer 64aa1393ba4ed", libros.get(position).getFav_id());
            call.enqueue(new Callback<Request>() {
                @Override
                public void onResponse(Call<Request> call, Response<Request> response) {
                    System.out.println("CORRECTO ELIMINADO A FAVORITO");
                    System.out.println("CORRECTO ELIMINADO A FAVORITO");
                    System.out.println("CORRECTO ELIMINADO A FAVORITO");
                    System.out.println("CORRECTO ELIMINADO A FAVORITO");
                }

                @Override
                public void onFailure(Call<Request> call, Throwable t) {
                    System.out.println("MAL ELIMINADO A FAVORITO");
                    System.out.println("MAL ELIMINADO A FAVORITO");
                    System.out.println("MAL ELIMINADO A FAVORITO");
                    System.out.println("MAL ELIMINADO A FAVORITO");
                }
            });
        } else {
            FavoritosBody favoritosBody=new FavoritosBody(19,libros.get(position).getId());
            Call<Request> call = librosApiAdapter.getApiService().agregarFavorito("Bearer 64aa1393ba4ed", favoritosBody);
            call.enqueue(new Callback<Request>() {
                @Override
                public void onResponse(Call<Request> call, Response<Request> response) {
                    System.out.println("CORRECTO AGREGADO A FAVORITO");
                    System.out.println("CORRECTO AGREGADO A FAVORITO");
                    System.out.println("CORRECTO AGREGADO A FAVORITO");
                    System.out.println("CORRECTO AGREGADO A FAVORITO");
                }
                
                @Override
                public void onFailure(Call<Request> call, Throwable t) {
                    System.out.println("MAL AGREGADO A FAVORITO");
                    System.out.println("MAL AGREGADO A FAVORITO");
                    System.out.println("MAL AGREGADO A FAVORITO");
                    System.out.println("MAL AGREGADO A FAVORITO");
                }
            });
        }
    }
}

