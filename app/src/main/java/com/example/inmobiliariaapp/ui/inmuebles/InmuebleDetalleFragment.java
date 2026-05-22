package com.example.inmobiliariaapp.ui.inmuebles;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.FragmentInmuebleDetalleBinding;
import com.example.inmobiliariaapp.modelo.Inmueble;
import com.example.inmobiliariaapp.request.ApiClient;

public class InmuebleDetalleFragment extends Fragment {

    private InmueblesViewModel vm;
    private FragmentInmuebleDetalleBinding b;
    private ActivityResultLauncher<Intent> selector;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);

        selector = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            vm.recibirFoto(result);
        });

        // Si recibimos un inmueble por Bundle, cargamos los datos (Modo Ver)
        if (getArguments() != null) {
            Inmueble i = (Inmueble) getArguments().getSerializable("inmueble");
            if (i != null) {
                cargarDatosInmueble(i);
            }
        }

        vm.getmUri().observe(getViewLifecycleOwner(), uri -> b.ivFoto.setImageURI(uri));

        vm.getMsjError().observe(getViewLifecycleOwner(), s -> Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show());

        vm.getGuardadoExitoso().observe(getViewLifecycleOwner(), ok -> {
            if (ok) {
                Toast.makeText(getContext(), "Operación exitosa!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        b.btnSeleccionarFoto.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            selector.launch(i);
        });

        b.btnGuardar.setOnClickListener(v -> {
            vm.guardarInmueble(
                    b.etDireccion.getText().toString(),
                    b.etUso.getText().toString(),
                    b.etTipo.getText().toString(),
                    b.etAmbientes.getText().toString(),
                    b.etSuperficie.getText().toString(),
                    b.etPrecio.getText().toString()
            );
        });

        return b.getRoot();
    }

    private void cargarDatosInmueble(Inmueble i) {
        // Llenamos los campos con el objeto que llegó
        b.tvTituloCargar.setText("Detalle del Inmueble");
        b.etDireccion.setText(i.getDireccion());
        b.etUso.setText(i.getUso());
        b.etTipo.setText(i.getTipo());
        b.etAmbientes.setText(String.valueOf(i.getAmbientes()));
        b.etSuperficie.setText(String.valueOf(i.getSuperficie()));
        b.etPrecio.setText(String.valueOf(i.getPrecio()));

        // Seteamos el Switch
        b.swDisponible.setChecked(i.isDisponible());

        // Cargamos la foto con Glide
        Glide.with(this)
                .load(ApiClient.BASE_URL + i.getImagen())
                .placeholder(R.drawable.icono_de_actualizacion)
                .error(R.drawable.recargar_flecha)
                .into(b.ivFoto);

        // Bloqueamos los campos para que solo se vean.
        deshabilitarCampos();
    }

    private void deshabilitarCampos() {
        b.etDireccion.setEnabled(false);
        b.etUso.setEnabled(false);
        b.etTipo.setEnabled(false);
        b.etAmbientes.setEnabled(false);
        b.etSuperficie.setEnabled(false);
        b.etPrecio.setEnabled(false);
        b.swDisponible.setEnabled(true);
        b.btnSeleccionarFoto.setVisibility(View.GONE);
        b.btnGuardar.setVisibility(View.GONE);
    }
}