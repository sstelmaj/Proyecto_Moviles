package com.example.proyecto_moviles;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = itemList;
        this.context = context;
    }

    @Override
    public int getItemCount() { return mData.size(); }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int posicion){
        holder.bindData(mData.get(posicion));
    }

    public void setItems(List<ListElement> items) { mData = items; }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView titulo, autor, categoria;

        ViewHolder(View itemView){
            super(itemView);
            iconImage=itemView.findViewById(R.id.imagen);
            titulo = itemView.findViewById(R.id.titulo);
            autor = itemView.findViewById(R.id.autor);
            //categoria = itemView.findViewById(R.id.categoria);
        }

        void bindData(final ListElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColor()), PorterDuff.Mode.SRC_IN);
            titulo.setText(item.getTitulo());
            autor.setText(item.getAutor());
            //categoria.setText(item.getCategoria());
        }

    }




}
