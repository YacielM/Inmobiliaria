package com.example.inmobiliariaapp.ui.inquilinos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.inmobiliariaapp.databinding.FragmentInquilinosBinding;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel vm;
    private FragmentInquilinosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInquilinosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InquilinosViewModel.class);

        // Observadores
        vm.getInmueblesAlquilados().observe(getViewLifecycleOwner(), inmuebles -> {
            InquilinoAdapter adapter = new InquilinoAdapter(inmuebles, getLayoutInflater());
            binding.rvInquilinos.setAdapter(adapter);
            binding.rvInquilinos.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        vm.getError().observe(getViewLifecycleOwner(), msj -> {
            if (msj != null) Toast.makeText(getContext(), msj, Toast.LENGTH_LONG).show();
        });

        // El fragmento solo da la orden
        vm.cargarInmueblesAlquilados();

        return binding.getRoot();
    }
}