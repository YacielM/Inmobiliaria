package com.example.inmobiliariaapp.ui.contratos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmobiliariaapp.modelo.Contrato;
import com.example.inmobiliariaapp.modelo.Inmueble;
import com.example.inmobiliariaapp.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoDetalleViewModel extends AndroidViewModel {
    private final MutableLiveData<Contrato> contratoM = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContratoM() { return contratoM; }
    public LiveData<String> getError() { return error; }

    public void cargarContrato(Inmueble inmueble) {
        if (inmueble == null) return;
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.getServicio().obtenerContratoPorInmueble("Bearer " + token, inmueble.getIdInmueble())
                .enqueue(new Callback<Contrato>() {
                    @Override
                    public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                        if (response.isSuccessful()) contratoM.postValue(response.body());
                        else error.postValue("No se encontró el contrato.");
                    }
                    @Override
                    public void onFailure(Call<Contrato> call, Throwable t) {
                        error.postValue("Error de red.");
                    }
                });
    }
}