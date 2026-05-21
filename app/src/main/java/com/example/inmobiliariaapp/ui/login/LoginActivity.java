package com.example.inmobiliariaapp.ui.login;

import android.content.Intent;import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog; // para el cartel
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.inmobiliariaapp.MainActivity;
import com.example.inmobiliariaapp.databinding.ActivityLoginBinding;
import com.example.inmobiliariaapp.request.ApiClient;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // El ViewModel verifica el token al iniciar
        vm = new ViewModelProvider(this).get(LoginViewModel.class);
        vm.verificarToken();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarObservadores();

        // Click en Iniciar Sesión
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String pass = binding.etPassword.getText().toString();
            vm.iniciarSesion(email, pass);
        });

        //La Activity maneja el diálogo (UI) pero el VM la acción (Lógica)
        binding.tvForgot.setOnClickListener(v -> {
            mostrarDialogoConfirmacion();
        });
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Restablecer Contraseña")
                .setMessage("¿Está seguro de que desea resetear la contraseña del usuario ID:3?")
                .setPositiveButton("Sí, resetear", (dialog, which) -> {
                    // Si confirma, recién ahí llamamos al VM
                    vm.resetPassword("");
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void configurarObservadores() {
        vm.getLoginSuccess().observe(this, success -> {
            if (Boolean.TRUE.equals(success)) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        vm.getErrorMessage().observe(this, msg -> {
            if (msg != null && !msg.isEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            }
        });

        vm.isLoading().observe(this, isLoading -> {
            binding.progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.btnLogin.setEnabled(!isLoading);
        });
    }
}