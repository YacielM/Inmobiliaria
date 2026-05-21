package com.example.inmobiliariaapp.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InicioViewModel extends ViewModel {

    private final MutableLiveData<String> mensaje = new MutableLiveData<>();

    public InicioViewModel() {
        mensaje.setValue("Inicio");
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public void cargarDatos() {

        mensaje.setValue("Bienvenido — aquí verás tus inmuebles");
    }
}
