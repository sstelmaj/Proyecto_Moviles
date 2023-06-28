package com.example.proyecto_moviles.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_moviles.Modelo.Libro;
import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.utils.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeRecommendedAdapter extends RecyclerView.Adapter<HomeRecommendedAdapter.HomeRecommendedViewHolder> {
    private List<Libro> recommendedBooks;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public HomeRecommendedAdapter(List<Libro> libros, Context context) {
        this.recommendedBooks = libros;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeRecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.horizontal_list_item, null);
        return new HomeRecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecommendedViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.bookTitle.setText(recommendedBooks.get(position).getTitulo());
        holder.bookAuthor.setText(recommendedBooks.get(position).getAutores());
        Picasso.get()
                .load(recommendedBooks.get(position).getImagen())
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.bookImage);

//        holder.btnDetails.setOnClickListener(
//                v -> onItemClickListener.onItemClick(recommendedBooks.get(position))
//        );

        holder.card.setOnClickListener(
                v -> onItemClickListener.onItemClick(recommendedBooks.get(position))
        );
    }

    @Override
    public int getItemCount() {
        return recommendedBooks.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class HomeRecommendedViewHolder extends RecyclerView.ViewHolder {
        TextView bookTitle;
        TextView bookAuthor;
        ImageView bookImage;
        Button btnDetails;
        CardView card;

        public HomeRecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.book_title);
            bookAuthor = itemView.findViewById(R.id.book_author);
            bookImage = itemView.findViewById(R.id.book_image);
//            btnDetails = itemView.findViewById(R.id.btn_ver_detalle);
            card = itemView.findViewById(R.id.card_view);
        }
    }
}
