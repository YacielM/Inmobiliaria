package com.example.inmobiliariaapp.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.FragmentInmueblesBinding;

public class InmuebleFragment extends Fragment {

    private InmueblesViewModel vm;
    private FragmentInmueblesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(InmueblesViewModel.class);

        vm.getListaInmuebles().observe(getViewLifecycleOwner(), lista -> {
            InmuebleAdapter adapter = new InmuebleAdapter(lista, getLayoutInflater());
            binding.rvInmuebles.setAdapter(adapter);
            binding.rvInmuebles.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        // Botón flotante para ir a agregar (debes tenerlo en tu XML)
        binding.fabAgregar.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_inmuebles_to_detalle);
        });

        vm.cargarInmuebles();
        return binding.getRoot();
    }
}
// Dentro del onCreateView de InmueblesFragment.java

