package com.example.proyecto_moviles.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.domain.Libro;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.utils.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LastSeenAdapter extends RecyclerView.Adapter<LastSeenAdapter.LastSeenViewHolder> {
    private List<Libro> lastSeenBooks;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public LastSeenAdapter(List<Libro> libros, Context context) {
        this.lastSeenBooks = libros;
        this.context = context;
    }

    @NonNull
    @Override
    public LastSeenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.vertical_list_item, null);
        return new LastSeenAdapter.LastSeenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LastSeenViewHolder holder, int position) {
        holder.bookTitle.setText(lastSeenBooks.get(position).getTitulo());
        holder.bookAuthor.setText(lastSeenBooks.get(position).getAutores());
        Picasso.get()
                .load(lastSeenBooks.get(position).getImagen())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.bookImage);

        holder.itemView.setOnClickListener(
                v -> onItemClickListener.onItemClick(lastSeenBooks.get(position))
        );
    }

    @Override
    public int getItemCount() {
        return lastSeenBooks.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class LastSeenViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView bookAuthor;
        ImageView bookImage;
        ImageView btnDetails;
        public LastSeenViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.titulo);
            bookAuthor = itemView.findViewById(R.id.autor);
            bookImage = itemView.findViewById(R.id.imagen);
            btnDetails = itemView.findViewById(R.id.flecha);
        }
    }
}
