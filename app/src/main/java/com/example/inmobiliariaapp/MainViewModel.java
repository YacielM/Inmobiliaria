package com.example.inmobiliariaapp;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmobiliariaapp.request.ApiClient;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> sesionValida = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getSesionValida() {
        return sesionValida;
    }

    // El VM chequea el modelo
    public void verificarSesion() {
        String token = ApiClient.obtenerToken(getApplication());
        if (token == null || token.trim().isEmpty()) {
            sesionValida.setValue(false);
        } else {
            sesionValida.setValue(true);
        }
    }

    // Acceso al modelo (borrar) desde el VM
    public void cerrarSesion() {
        ApiClient.borrarToken(getApplication());
        sesionValida.setValue(false);
    }
}