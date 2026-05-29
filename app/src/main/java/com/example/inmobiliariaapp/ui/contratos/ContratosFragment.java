package com.example.inmobiliariaapp.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.inmobiliariaapp.databinding.FragmentContratosBinding;

public class ContratosFragment extends Fragment {

    private ContratosViewModel vm;
    private FragmentContratosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);

        // Configurar Observadores
        configurarObservadores();

        // El Fragment solo da la orden
        vm.cargarInmueblesConContrato();

        return binding.getRoot();
    }

    private void configurarObservadores() {
        vm.getInmueblesAlquilados().observe(getViewLifecycleOwner(), inmuebles -> {
            ContratoAdapter adapter = new ContratoAdapter(inmuebles, getLayoutInflater());
            binding.rvContratos.setAdapter(adapter);
            binding.rvContratos.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        vm.getError().observe(getViewLifecycleOwner(), msj -> {
            if (msj != null) Toast.makeText(getContext(), msj, Toast.LENGTH_LONG).show();
        });
    }
}