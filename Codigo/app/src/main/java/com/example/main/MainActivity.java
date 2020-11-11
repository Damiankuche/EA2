package com.example.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.main.actualizar.token.ActualizarTokenErrorResponse;
import com.example.main.actualizar.token.ActualizarTokenResponse;
import com.example.main.services.ActualizarTokenService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageButton btnLogout;
    Button btnSensor;
    Button btnLog;
    GlobalClass globalClass;
    Handler handler = new Handler();
    //TODO implementar Firebase
    private final int TIEMPO = 1740000; // 29 minutos

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalClass = (GlobalClass)getApplicationContext();
        btnLogout = findViewById(R.id.logoutButton);
        btnSensor = findViewById(R.id.sensorButton);
        btnLog = findViewById(R.id.logButton);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorActivity.class);
                startActivity(intent);
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });

            actualizarToken();

    }

    @Override
    protected void onResume() {
        super.onResume();
        globalClass.setRunning(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        globalClass.setRunning(false);

    }



    public void actualizarToken(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(getString(R.string.retrofit_server)).build();
                ActualizarTokenService actualizarTokenService = retrofit.create(ActualizarTokenService.class);

                Call<ActualizarTokenResponse> call = actualizarTokenService.actualizar("Bearer "+globalClass.getRefresh_token());
                call.enqueue(new Callback<ActualizarTokenResponse>() {


                    @Override
                    public void onResponse(Call<ActualizarTokenResponse> call, Response<ActualizarTokenResponse> response) {

                        if(response.isSuccessful()){

                            globalClass.setToken(response.body().getToken());
                            globalClass.setRefresh_token(response.body().getToken_refresh());

                            Toast.makeText(globalClass,"Token actualizado",Toast.LENGTH_LONG);
                            Log.i("Actualizar token","El token fue actualizado de forma correcta.");
                            Thread agregar = new Thread(new CargarDatoLista("Se actualizó el token.",globalClass));
                            agregar.start();

                        } else {

                            Gson gson = new Gson();
                            ActualizarTokenErrorResponse actualizarTokenErrorResponse;

                            try {
                                actualizarTokenErrorResponse = gson.fromJson(
                                        response.errorBody().string(),
                                        ActualizarTokenErrorResponse.class);

                                Toast.makeText(getApplicationContext(), actualizarTokenErrorResponse.getMsg(), Toast.LENGTH_SHORT).show();
                                Log.e("Actualizar token",actualizarTokenErrorResponse.getMsg());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ActualizarTokenResponse> call, Throwable t) {

                        Log.e("Actualizacion de token", t.getMessage());
                    }

                    });

                handler.postDelayed(this,TIEMPO);

            }
        },TIEMPO);

    }

}
