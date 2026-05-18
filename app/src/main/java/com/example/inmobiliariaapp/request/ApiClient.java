package com.example.inmobiliariaapp.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.inmobiliariaapp.modelo.Inmueble;
import com.example.inmobiliariaapp.modelo.Propietario;
import com.example.inmobiliariaapp.modelo.CambioClaveView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public class ApiClient {

    public static final String BASE_URL = "https://capacitacion.alwaysdata.net/";
    private static MiServicioInmobiliaria servicio;

    // Singleton simple para Retrofit
    public static MiServicioInmobiliaria getServicio() {
        if (servicio == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            servicio = retrofit.create(MiServicioInmobiliaria.class);
        }
        return servicio;
    }

    public interface MiServicioInmobiliaria {
        @PUT("api/propietarios/fix-id3")
        Call<ResponseBody> resetearPasswordFix();
        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> iniciarSesion(@Field("Usuario") String usuario, @Field("Clave") String clave);

        @GET("api/Propietarios")
        Call<Propietario> getPropietario(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarPerfil(@Header("Authorization") String token, @Body Propietario propietario);

        @FormUrlEncoded
        @POST("api/Propietarios/email")
        Call<String> resetearPassword(@Field("email") String email);

        // Si la API espera form-urlencoded para changePassword, mantenemos esto.
        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarPassword(@Header("Authorization") String token,
                                   @Field("currentPassword") String claveActual,
                                   @Field("newPassword") String claveNueva);

        @GET("api/Inmuebles")
        Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization") String token);

        @GET("api/Inmuebles/GetContratoVigente")
        Call<List<Inmueble>> obtenerInmueblesAlquilados(@Header("Authorization") String token);

        @PUT("api/Inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> cargarInmuebles(@Header("Authorization") String token,
                                       @Part MultipartBody.Part imagen,
                                       @Part("inmueble") RequestBody inmueble);
    }

    // SharedPreferences: nombre y clave consistentes
    private static final String PREFS_NAME = "inmobiliaria_prefs";
    private static final String KEY_TOKEN = "auth_token";

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public static String obtenerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_TOKEN, null);
    }

    public static void borrarToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }
}
