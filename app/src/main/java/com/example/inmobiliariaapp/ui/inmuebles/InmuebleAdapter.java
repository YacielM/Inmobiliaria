package com.example.inmobiliariaapp.ui.inmuebles;

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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {

    private List<Inmueble> listaInmuebles;
    private LayoutInflater inflater;

    public InmuebleAdapter(List<Inmueble> inmuebles, LayoutInflater inflater) {
        this.listaInmuebles = inmuebles;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_inmueble, parent, false);
        return new ViewHolderInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmuebleActual = listaInmuebles.get(position);
        holder.direccion.setText(inmuebleActual.getDireccion());

        NumberFormat nf = NumberFormat.getInstance(new Locale("es", "AR"));
        String valorFormateado = nf.format(inmuebleActual.getPrecio());
        holder.precio.setText("$ " + valorFormateado);

        Glide.with(holder.itemView.getContext())
                .load(ApiClient.BASE_URL + inmuebleActual.getImagen())
                .placeholder(R.drawable.icono_de_actualizacion)
                .error(R.drawable.recargar_flecha)
                .into(holder.foto);

        // Al hacer clic, pasamos el objeto a la siguiente pantalla
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("inmueble", inmuebleActual);
            Navigation.findNavController(v).navigate(R.id.inmuebleDetalleFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listaInmuebles != null ? listaInmuebles.size() : 0;
    }

    public static class ViewHolderInmueble extends RecyclerView.ViewHolder {
        TextView direccion, precio;
        ImageView foto;

        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvPrecio);
            foto = itemView.findViewById(R.id.ivImagen);
        }
    }
}