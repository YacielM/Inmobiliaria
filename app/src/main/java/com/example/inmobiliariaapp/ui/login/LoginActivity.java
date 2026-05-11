package com.example.inmobiliariaapp.ui.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliariaapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding b;
    private LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        // Usamos el AndroidViewModelFactory para crear el ViewModel que necesita Application
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(LoginViewModel.class);

        b.btnLogin.setOnClickListener(v -> {
            vm.iniciarSesion(
                    b.etEmail.getText() != null ? b.etEmail.getText().toString().trim() : "",
                    b.etPassword.getText() != null ? b.etPassword.getText().toString() : ""
            );
        });

        b.tvForgot.setOnClickListener(v -> {
            String email = b.etEmail.getText() != null ? b.etEmail.getText().toString().trim() : "";
            vm.resetPassword(email);
        });


    }
}
