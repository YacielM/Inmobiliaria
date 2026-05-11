package com.example.inmobiliariaapp.ui.login;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.inmobiliariaapp.MainActivity;
import com.example.inmobiliariaapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void iniciarSesion(String usuario, String clave) {
        if (usuario == null || usuario.isBlank() || clave == null || clave.isBlank()) {
            Toast.makeText(getApplication(), "Complete todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        // Llamamos al servicio tal como lo hace el repo de tu compañero
        ApiClient.MiServicioInmobiliaria servicio = ApiClient.getServicio();
        Call<String> call = servicio.iniciarSesion(usuario, clave);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body();
                    // Guardar token usando el helper que ya tenés en ApiClient
                    ApiClient.guardarToken(getApplication(), token);
                    Log.d(TAG, "Token: " + token);

                    // Iniciar MainActivity desde el ViewModel (necesita FLAG_ACTIVITY_NEW_TASK)
                    Intent i = new Intent(getApplication(), MainActivity.class);
                    i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(i);
                } else {
                    Log.d(TAG, "Login error: " + response.code() + " - " + response.message());
                    Toast.makeText(getApplication(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getApplication(), "Fallo del Callback: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void resetPassword(String email) {
        if (email == null || email.trim().isEmpty()) {
            Toast.makeText(getApplication(), "Ingrese un email válido", Toast.LENGTH_LONG).show();
            return;
        }
        // Implementación mínima (simulada). Reemplazar por llamada real si existe endpoint.
        Toast.makeText(getApplication(), "Se envió un email con instrucciones (simulado)", Toast.LENGTH_LONG).show();
    }
}
