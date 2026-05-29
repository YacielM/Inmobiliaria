package com.example.inmobiliariaapp.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.inmobiliariaapp.databinding.FragmentInquilinoDetalleBinding;
import com.example.inmobiliariaapp.modelo.Inmueble;

public class InquilinoDetalleFragment extends Fragment {

    private InquilinoDetalleViewModel vm;
    private FragmentInquilinoDetalleBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInquilinoDetalleBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InquilinoDetalleViewModel.class);

        // Observar los datos del inquilino
        vm.getInquilinoM().observe(getViewLifecycleOwner(), inquilino -> {
            if (inquilino != null) {
                binding.etCodigo.setText(String.valueOf(inquilino.getId()));
                binding.etNombre.setText(inquilino.getNombre());
                binding.etApellido.setText(inquilino.getApellido());
                binding.etDni.setText(inquilino.getDni());
                binding.etEmail.setText(inquilino.getEmail());
                binding.etTelefono.setText(inquilino.getTelefono());
            }
        });

        vm.getError().observe(getViewLifecycleOwner(), s -> Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show());

        // Recuperar el inmueble del Bundle y pedir los datos al VM
        if (getArguments() != null) {
            Inmueble inmueble = (Inmueble) getArguments().getSerializable("inmueble");
            vm.cargarInquilino(inmueble);
        }

        return binding.getRoot();
    }
}