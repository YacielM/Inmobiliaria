package com.example.inmobiliariaapp.ui.inmuebles;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmobiliariaapp.modelo.Inmueble;
import com.example.inmobiliariaapp.request.ApiClient;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();
    private final MutableLiveData<Uri> mUri = new MutableLiveData<>();
    private final MutableLiveData<String> msjError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> guardadoExitoso = new MutableLiveData<>();

    public InmueblesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getListaInmuebles() { return listaInmuebles; }
    public LiveData<Uri> getmUri() { return mUri; }
    public LiveData<String> getMsjError() { return msjError; }
    public LiveData<Boolean> getGuardadoExitoso() { return guardadoExitoso; }

    // LÓGICA DE LISTADO
    public void cargarInmuebles() {
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.getServicio().obtenerInmuebles("Bearer " + token).enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) listaInmuebles.postValue(response.body());
                else msjError.postValue("Error al obtener inmuebles");
            }
            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                msjError.postValue("Error de conexión");
            }
        });
    }

    // LÓGICA DE ALTA (AGREGAR)
    public void recibirFoto(ActivityResult resultado) {
        if (resultado.getResultCode() == -1 && resultado.getData() != null) {
            mUri.setValue(resultado.getData().getData());
        }
    }

    public void guardarInmueble(String dir, String uso, String tipo, String amb, String sup, String val) {
        if (dir.isEmpty() || uso.isEmpty() || tipo.isEmpty() || amb.isEmpty() || sup.isEmpty() || val.isEmpty()) {
            msjError.setValue("Complete todos los campos");
            return;
        }

        byte[] imagenByte = transformarImagen();
        if (imagenByte.length == 0) {
            msjError.setValue("Seleccione una foto");
            return;
        }

        try {
            Inmueble i = new Inmueble();
            i.setDireccion(dir);
            i.setUso(uso);
            i.setTipo(tipo);
            i.setAmbientes(Integer.parseInt(amb));
            i.setSuperficie(Integer.parseInt(sup));
            i.setPrecio(Double.parseDouble(val));

            String json = new Gson().toJson(i);
            RequestBody iBody = RequestBody.create(MediaType.parse("application/json"), json);
            RequestBody fBody = RequestBody.create(MediaType.parse("image/jpeg"), imagenByte);
            MultipartBody.Part part = MultipartBody.Part.createFormData("imagen", "foto.jpg", fBody);

            String token = ApiClient.obtenerToken(getApplication());
            ApiClient.getServicio().cargarInmuebles("Bearer " + token, part, iBody).enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) guardadoExitoso.postValue(true);
                    else msjError.postValue("Error al guardar");
                }
                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    msjError.postValue("Fallo de red");
                }
            });
        } catch (Exception e) {
            msjError.setValue("Datos numéricos inválidos");
        }
    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue();
            if (uri == null) return new byte[]{};
            InputStream is = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            return bos.toByteArray();
        } catch (Exception e) { return new byte[]{}; }
    }
}