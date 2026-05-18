package com.example.inmobiliariaapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.inmobiliariaapp.MainActivity;
import com.example.inmobiliariaapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // REGLA 1: Cada Activity con su ViewModel
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = new ViewModelProvider(this).get(LoginViewModel.class);

        // REGLA 2: El fragment/Activity espera con un Observer
        configurarObservadores();

        // REGLA 4: Enviamos la intención de verificar sesión al VM sin lógica aquí
        vm.verificarToken();

        // REGLA 4: Los clics solo envían datos, no validan nada
        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String pass = binding.etPassword.getText().toString();
            vm.iniciarSesion(email, pass);
        });

        binding.tvForgot.setOnClickListener(v -> {
            vm.resetPassword("");
        });
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