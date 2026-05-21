package com.example.inmobiliariaapp.ui.perfil;

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
import com.example.inmobiliariaapp.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vm;
    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);

        //El fragment espera con Observers
        configurarObservadores();

        // El click solo avisa al VM, no ejecuta lógica
        binding.btEditar.setOnClickListener(v -> vm.cambiarEstadoEdicion(true));

        binding.btAceptar.setOnClickListener(v -> {
            //Solo enviamos datos crudos
            vm.editarPerfil(
                    binding.etNombre.getText().toString().trim(),
                    binding.etApellido.getText().toString().trim(),
                    binding.etDni.getText().toString().trim(),
                    binding.etTelefonoDetalle.getText().toString().trim()
            );
        });

        binding.btCambiarPass.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_perfil_to_cambioPass);
        });

        vm.cargarPerfil();
        return binding.getRoot();
    }

    private void configurarObservadores() {
        // Observador de datos del propietario
        vm.getPropietarioM().observe(getViewLifecycleOwner(), propietario -> {
            if (propietario != null) {
                binding.etDni.setText(propietario.getDni());
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etEmailDetalle.setText(propietario.getEmail());
                binding.etTelefonoDetalle.setText(propietario.getTelefono());
            }
        });

        //El Fragment habilita la edición porque el VM se lo ordena
        vm.getEstadoEdicion().observe(getViewLifecycleOwner(), this::habilitarEdicion);

        vm.getError().observe(getViewLifecycleOwner(), s -> {
            if (s != null) Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show();
        });

        vm.getInfoMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
        });
    }

    private void habilitarEdicion(boolean estado) {
        binding.etNombre.setEnabled(estado);
        binding.etApellido.setEnabled(estado);
        binding.etDni.setEnabled(estado);
        binding.etTelefonoDetalle.setEnabled(estado);

        binding.btEditar.setVisibility(estado ? View.GONE : View.VISIBLE);
        binding.btAceptar.setVisibility(estado ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}