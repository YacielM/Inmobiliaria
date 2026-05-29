package com.example.inmobiliariaapp.ui.inquilinos;

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

public class InquilinosViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Inmueble>> inmueblesAlquilados = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getInmueblesAlquilados() { return inmueblesAlquilados; }
    public LiveData<String> getError() { return error; }

    public void cargarInmueblesAlquilados() {
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.getServicio().obtenerInmueblesAlquilados("Bearer " + token).enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    inmueblesAlquilados.postValue(response.body());
                } else {
                    error.postValue("No se encontraron inmuebles con contratos vigentes.");
                }
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                error.postValue("Error de conexión");
            }
        });
    }
}