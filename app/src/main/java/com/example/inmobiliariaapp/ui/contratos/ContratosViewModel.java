package com.example.inmobiliariaapp.ui.contratos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmobiliariaapp.modelo.Inmueble;
import com.example.inmobiliariaapp.request.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratosViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Inmueble>> inmueblesAlquilados = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public ContratosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesAlquilados() { return inmueblesAlquilados; }
    public LiveData<String> getError() { return error; }

    public void cargarInmueblesConContrato() {
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.getServicio().obtenerInmueblesAlquilados("Bearer " + token).enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    inmueblesAlquilados.postValue(response.body());
                } else {
                    error.postValue("No se encontraron contratos vigentes.");
                }
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                error.postValue("Error de red");
            }
        });
    }
}