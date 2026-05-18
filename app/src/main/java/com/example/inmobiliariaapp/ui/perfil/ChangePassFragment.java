package com.example.inmobiliariaapp.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.inmobiliariaapp.databinding.FragmentCambioPassBinding;
import com.example.inmobiliariaapp.ui.login.LoginActivity;

public class ChangePassFragment extends Fragment {

    private PerfilViewModel vm;
    private FragmentCambioPassBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCambioPassBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);

        configurarObservadores();

        binding.btAceptarCambiarPass.setOnClickListener(v -> {
            // Regla 4: Solo enviamos datos, la lógica está en el VM
            String claveActual = binding.etClaveActual.getText().toString().trim();
            String claveNueva = binding.etClaveNueva.getText().toString().trim();
            vm.cambiarPass(claveActual, claveNueva);
        });

        return binding.getRoot();
    }

    private void configurarObservadores() {
        vm.getError().observe(getViewLifecycleOwner(), s -> {
            if (s != null) Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show();
        });

        vm.getInfoMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show();
        });

        vm.getLogoutEvent().observe(getViewLifecycleOwner(), doLogout -> {
            if (Boolean.TRUE.equals(doLogout)) {
                // Navegación limpia al Login
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}