package com.example.inmobiliariaapp.ui.inquilinos;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmobiliariaapp.modelo.Contrato;
import com.example.inmobiliariaapp.modelo.Inmueble;
import com.example.inmobiliariaapp.modelo.Inquilino;
import com.example.inmobiliariaapp.request.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetalleViewModel extends AndroidViewModel {
    private final MutableLiveData<Inquilino> inquilinoM = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getInquilinoM() { return inquilinoM; }
    public LiveData<String> getError() { return error; }

    public void cargarInquilino(Inmueble inmueble) {
        if (inmueble == null) return;

        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.getServicio().obtenerContratoPorInmueble("Bearer " + token, inmueble.getIdInmueble())
                .enqueue(new Callback<Contrato>() {
                    @Override
                    public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            inquilinoM.postValue(response.body().getInquilino());
                        } else {
                            error.postValue("No se pudo obtener el inquilino.");
                        }
                    }
                    @Override
                    public void onFailure(Call<Contrato> call, Throwable t) {
                        error.postValue("Error de red.");
                    }
                });
    }
}