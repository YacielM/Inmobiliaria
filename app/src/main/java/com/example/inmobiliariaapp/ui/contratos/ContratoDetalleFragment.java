package com.example.inmobiliariaapp.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.FragmentContratoDetalleBinding;
import com.example.inmobiliariaapp.modelo.Inmueble;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel vm;
    private FragmentContratoDetalleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContratoDetalleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ContratoDetalleViewModel.class);

        // Observar datos del contrato
        vm.getContratoM().observe(getViewLifecycleOwner(), contrato -> {
            if (contrato != null) {
                binding.etCodigoContrato.setText(String.valueOf(contrato.getId()));
                binding.etFechaInicio.setText(contrato.getFechaInicio());
                binding.etFechaFin.setText(contrato.getFechaFinalizacion());
                binding.etMontoAlquiler.setText("$ " + contrato.getMontoAlquiler());
                binding.etInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
                binding.etInmuebleContrato.setText("Inmueble en " + contrato.getInmueble().getDireccion());

                // Botón Pagos: Pasamos el contrato al siguiente fragmento
                binding.btnPagos.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contrato", contrato);
                    Navigation.findNavController(v).navigate(R.id.pagosFragment, bundle);
                });
            }
        });

        vm.getError().observe(getViewLifecycleOwner(), s -> Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show());

        // Recuperar el inmueble del Bundle y pedir los datos al VM
        if (getArguments() != null) {
            Inmueble inmueble = (Inmueble) getArguments().getSerializable("inmueble");
            vm.cargarContrato(inmueble);
        }

        return binding.getRoot();
    }
}