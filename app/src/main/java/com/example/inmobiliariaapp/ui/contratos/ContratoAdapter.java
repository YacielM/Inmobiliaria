package com.example.inmobiliariaapp.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.modelo.Inmueble;
import com.example.inmobiliariaapp.request.ApiClient;
import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolder> {
    private List<Inmueble> lista;
    private LayoutInflater inflater;

    public ContratoAdapter(List<Inmueble> lista, LayoutInflater inflater) {
        this.lista = lista;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Inmueble i = lista.get(position);
        holder.tvDireccion.setText(i.getDireccion());
        Glide.with(holder.itemView.getContext())
                .load(ApiClient.BASE_URL + i.getImagen())
                .placeholder(R.drawable.icono_de_actualizacion)
                .into(holder.ivImagen);

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", i);
            Navigation.findNavController(v).navigate(R.id.contratoDetalleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDireccion;
        ImageView ivImagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            ivImagen = itemView.findViewById(R.id.ivImagen);
        }
    }
}