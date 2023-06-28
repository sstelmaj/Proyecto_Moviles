package com.example.proyecto_moviles.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.domain.Comentario;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.utils.OnItemClickListener;

import java.util.List;

public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.ComentarioViewHolder>{
    private List<Comentario> comentarios;
    private int rowLayout;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public ComentariosAdapter(List<Comentario> comentarios, int rowLayout, Context context) {
        this.comentarios = comentarios;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    //A view holder inner class where we get reference to the views in the layout using their ID
    public static class ComentarioViewHolder extends RecyclerView.ViewHolder {
        CardView comentariosLayout;
        TextView comentario;
        TextView autor;
        ImageView autorImagen;
        public ComentarioViewHolder(View v) {
            super(v);
            comentariosLayout = (CardView) v.findViewById(R.id.detalle_listRecyclerView);
            comentario = (TextView) v.findViewById(R.id.comentario_contenido);
            autor = (TextView) v.findViewById(R.id.comentario_autor);
            //libroImage=(ImageView) v.findViewById(R.id.imagen);
        }
    }
    @Override
    public ComentariosAdapter.ComentarioViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(rowLayout, parent, false);
        return new ComentariosAdapter.ComentarioViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ComentariosAdapter.ComentarioViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.comentario.setText(comentarios.get(position).getComentario());
        //holder.data.setText(libros.get(position).getDescripcion());
        holder.autor.setText(String.valueOf(comentarios.get(position).getNombreUsuario()));
    }
    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    //Declaramos el get y set para el clicklistener
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
