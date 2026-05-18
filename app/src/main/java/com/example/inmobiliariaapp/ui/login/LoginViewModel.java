package com.example.inmobiliariaapp.ui.login;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.inmobiliariaapp.request.ApiClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private static final String TAG = "LoginViewModel";
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getLoginSuccess() { return loginSuccess; }
    public LiveData<String> getErrorMessage() { return errorMessage; }
    public LiveData<Boolean> isLoading() { return loading; }

    // REGLA 4: El ViewModel decide si el token es válido o no
    public void verificarToken() {
        String token = ApiClient.obtenerToken(getApplication());
        if (token != null && !token.isEmpty()) {
            loginSuccess.setValue(true);
        }
    }

    public void iniciarSesion(String usuario, String clave) {
        // REGLA 3: Las validaciones se hacen en el ViewModel
        if (usuario == null || usuario.trim().isEmpty() || clave == null || clave.trim().isEmpty()) {
            errorMessage.setValue("Debe ingresar usuario y contraseña");
            return;
        }

        loading.setValue(true);
        ApiClient.MiServicioInmobiliaria servicio = ApiClient.getServicio();
        Call<String> call = servicio.iniciarSesion(usuario, clave);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                loading.postValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    loginSuccess.postValue(true);
                } else {
                    errorMessage.postValue("Usuario o contraseña incorrectos");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                loading.postValue(false);
                errorMessage.postValue("Error de conexión");
            }
        });
    }

    public void resetPassword(String email) {
        loading.setValue(true);
        ApiClient.MiServicioInmobiliaria servicio = ApiClient.getServicio();
        Call<ResponseBody> call = servicio.resetearPasswordFix();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loading.postValue(false);
                if (response.isSuccessful()) {
                    errorMessage.postValue("Contraseña reseteada con éxito (Clave ahora es DEEKQW)");
                } else {
                    errorMessage.postValue("Error al resetear");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.postValue(false);
                errorMessage.postValue("Fallo de red");
            }
        });
    }
}
