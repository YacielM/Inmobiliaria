package com.example.inmobiliariaapp.ui.perfil;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmobiliariaapp.modelo.Propietario;
import com.example.inmobiliariaapp.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private final MutableLiveData<Propietario> propietarioM = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> infoMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> estadoEdicion = new MutableLiveData<>(false);

    // LiveData para avisar al Fragment que debe cerrar sesión (Regla 2)
    private final MutableLiveData<Boolean> logoutEvent = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    // Getters para LiveData
    public LiveData<Propietario> getPropietarioM() { return propietarioM; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> isLoading() { return loading; }
    public LiveData<String> getInfoMessage() { return infoMessage; }
    public LiveData<Boolean> getEstadoEdicion() { return estadoEdicion; }
    public LiveData<Boolean> getLogoutEvent() { return logoutEvent; }

    public void cambiarEstadoEdicion(boolean estado) {
        estadoEdicion.setValue(estado);
    }

    public void cargarPerfil() {
        String token = ApiClient.obtenerToken(getApplication());
        if (token == null) {
            error.setValue("No hay token.");
            return;
        }
        loading.setValue(true);
        ApiClient.getServicio().getPropietario("Bearer " + token).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                loading.postValue(false);
                if (response.isSuccessful()) propietarioM.postValue(response.body());
                else error.postValue("Error al cargar perfil");
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                loading.postValue(false);
                error.postValue("Error de red");
            }
        });
    }

    public void editarPerfil(String nombre, String apellido, String dni, String telefono) {
        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty()) {
            error.setValue("Campos obligatorios vacíos");
            return;
        }
        Propietario actual = propietarioM.getValue();
        if (actual == null) return;

        actual.setNombre(nombre);
        actual.setApellido(apellido);
        actual.setDni(dni);
        actual.setTelefono(telefono);

        String token = ApiClient.obtenerToken(getApplication());
        loading.setValue(true);
        ApiClient.getServicio().actualizarPerfil("Bearer " + token, actual).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                loading.postValue(false);
                if (response.isSuccessful()) {
                    propietarioM.postValue(response.body());
                    infoMessage.postValue("Perfil actualizado");
                    estadoEdicion.postValue(false);
                } else error.postValue("Error al actualizar");
            }
            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                loading.postValue(false);
                error.postValue("Error de red");
            }
        });
    }

    // --- NUEVO MÉTODO PARA CAMBIO DE PASS (Regla 3 y 4) ---
    public void cambiarPass(String actual, String nueva) {
        if (actual.isEmpty() || nueva.isEmpty()) {
            error.setValue("Debe completar ambos campos");
            return;
        }
        if (nueva.length() < 4) {
            error.setValue("La nueva clave es muy corta");
            return;
        }

        String token = ApiClient.obtenerToken(getApplication());
        loading.setValue(true);
        ApiClient.getServicio().cambiarPassword("Bearer " + token, actual, nueva).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                loading.postValue(false);
                if (response.isSuccessful()) {
                    // Acción del modelo: borrar token
                    ApiClient.borrarToken(getApplication());
                    infoMessage.postValue("Clave cambiada. Inicie sesión.");
                    // Avisamos a la vista que debe navegar al Login (Regla 2)
                    logoutEvent.postValue(true);
                } else {
                    error.postValue("La clave actual es incorrecta");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                loading.postValue(false);
                error.postValue("Error de conexión");
            }
        });
    }
}