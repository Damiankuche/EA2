package com.example.main;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.main.registration.RegistrationErrorResponse;
import com.example.main.registration.RegistrationRequest;
import com.example.main.registration.RegistrationResponse;
import com.example.main.services.RegistrationService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {

    public static final String TAG = RegistroActivity.class.getSimpleName();

    final int Error400 = 400;
    final int largoMaxPass = 8;

    Button btnRegistrar;
    Button btnCancelar;
    EditText nombreText;
    EditText apellidoText;
    EditText dniText;
    EditText mailText;
    EditText constraseñaText;
    EditText comisionText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnRegistrar = findViewById(R.id.createButton);
        btnCancelar = findViewById(R.id.cancelarButton);

        nombreText = findViewById(R.id.NombreText);
        apellidoText = findViewById(R.id.ApellidoText);
        dniText = findViewById(R.id.DniText);
        mailText = findViewById(R.id.EmailText);
        constraseñaText = findViewById(R.id.ContraseñaText);
        comisionText = findViewById(R.id.ComisionText);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public void registrar(){
        RegistrationRequest request = new RegistrationRequest();
        request.setEnv("PROD");
        request.setName(nombreText.getText().toString());
        request.setLastname(apellidoText.getText().toString());
        request.setDni(Long.parseLong(dniText.getText().toString()));
        request.setEmail(mailText.getText().toString());
        request.setPassword(constraseñaText.getText().toString());
        request.setCommission(Long.parseLong(comisionText.getText().toString()));

        if (isOnline()){

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(getString(R.string.retrofit_server)).build();

            RegistrationService registrationService = retrofit.create(RegistrationService.class);

            Call<RegistrationResponse> call = registrationService.register(request);
            call.enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    CharSequence texto;

                    if (response.isSuccessful()) {
                        // se guarda el token y token_refresh en una clase global
                        GlobalClass globalClass = (GlobalClass)getApplicationContext();
                        globalClass.setToken(response.body().getToken());
                        globalClass.setRefresh_token(response.body().getToken_refresh());
                        texto = "Cuenta creada";
                        Toast.makeText(RegistroActivity.this, texto, Toast.LENGTH_LONG).show();
                        Log.i(TAG, globalClass.getToken());
                        //se lanza el main activity y finaliza el activity de registro
                        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Gson gson = new Gson();
                        RegistrationErrorResponse registrationErrorResponse = new RegistrationErrorResponse();

                        try {
                            registrationErrorResponse = gson.fromJson(
                                    response.errorBody().string(),
                                    RegistrationErrorResponse.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (response.raw().code() == Error400) {

                            if (constraseñaText.length() < largoMaxPass) {
                                texto = "Ingrese una contraseña con 8 caracteres o más.";
                                Toast.makeText(RegistroActivity.this, texto, Toast.LENGTH_LONG).show();
                            } else if (!validarEmail(mailText.getText().toString())) {
                                texto = "Ingrese un formato correcto de mail.";
                                Toast.makeText(RegistroActivity.this, texto, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegistroActivity.this, registrationErrorResponse.getMsg(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    Log.i(TAG, "Mensaje finalizado.");
                }


                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    CharSequence texto;
                    texto = t.getMessage();
                    Log.e(TAG, (String) texto);
                    Toast.makeText(RegistroActivity.this, texto, Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
        else{
            Toast.makeText(RegistroActivity.this, "No tienes conexión a internet.", Toast.LENGTH_LONG).show();
        }

    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public boolean isOnline(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }
}
