package com.example.main;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SensorActivity extends AppCompatActivity  implements SensorEventListener{



    public static final String TAG = RegistroActivity.class.getSimpleName();
    Sensor acSensor;
    Sensor prSensor;
    CheckBox acelerometroCheck;
    ImageButton btnAtras;
    TextView aceletrometroXText;
    TextView aceletrometroYText;
    TextView aceletrometroZText;
    SensorManager sm;
    CheckBox proximidadCheck;
    TextView proximidadText;
    private long last_update = 0, last_movement = 0;
    private float prevX = 0, prevY = 0, prevZ = 0;
    private float curX = 0, curY = 0, curZ = 0;
    private Boolean realizoCambio;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor acSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor prSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        realizoCambio = false;
        GlobalClass globalClass = (GlobalClass)getApplicationContext();

        sm.registerListener(this, acSensor, SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(this, prSensor, SensorManager.SENSOR_DELAY_GAME);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAtras = findViewById(R.id.backButton);
        aceletrometroXText = findViewById(R.id.acelerometroXText);
        aceletrometroYText = findViewById(R.id.acelerometroYText);
        aceletrometroZText = findViewById(R.id.acelerometroZText);
        proximidadText = findViewById(R.id.proximidadText);

        acelerometroCheck = findViewById(R.id.acelerometroCheck);
        proximidadCheck = findViewById(R.id.proximidadCheck);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        proximidadCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    proximidadText.setText("Proximidad: Esperando cambios en el sensor...");

                    Retrofit retrofitEvento = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(getString(R.string.retrofit_server)).build();


                    Thread evento = new Thread(new RegistrarEvento("Sensor proximidad", "Se activó el sensor de proximidad.", getApplicationContext(), retrofitEvento));
                    evento.start();
                }
                else {
                    proximidadText.setText("");

                    Retrofit retrofitEvento = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(getString(R.string.retrofit_server)).build();


                    Thread evento = new Thread(new RegistrarEvento("Sensor proximidad", "Se desactivó el sensor de proximidad.", getApplicationContext(), retrofitEvento));
                    evento.start();
                }

            }

        });


        acelerometroCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //se guarda en el servidor
                if (isChecked) {

                    Retrofit retrofitEvento = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(getString(R.string.retrofit_server)).build();


                    Thread evento = new Thread(new RegistrarEvento("Sensor acelerometro", "Se activó el acelerómetro.", getApplicationContext(), retrofitEvento));
                    evento.start();
                }
                else {

                    Retrofit retrofitEvento = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(getString(R.string.retrofit_server)).build();


                    Thread evento = new Thread(new RegistrarEvento("Sensor acelerometro", "Se desactivó el acelerometro.", getApplicationContext(), retrofitEvento));
                    evento.start();
                }

            }

        });

    }


        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                Retrofit retrofitEvento = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(getString(R.string.retrofit_server)).build();
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        if (acelerometroCheck.isChecked()) {



                            curX = event.values[0];
                            curY = event.values[1];
                            curZ = event.values[2];

                            if (prevX == 0 && prevY == 0 && prevZ == 0) {
                                prevX = curX;
                                prevY = curY;
                                prevZ = curZ;

                                aceletrometroXText.setText("Acelerómetro X: " + curX);
                                aceletrometroYText.setText("Acelerómetro Y: " + curY);
                                aceletrometroZText.setText("Acelerómetro Z: " + curZ);
                            }


                            if(Math.abs(prevX-curX)>0.20){
                                aceletrometroXText.setText("Acelerómetro X: " + curX);
                                prevX = curX;
                                realizoCambio = true;
                            }

                            if(Math.abs(prevY-curY)>0.20){
                                aceletrometroYText.setText("Acelerómetro Y: " + curY);
                                prevY = curY;
                                realizoCambio = true;
                            }

                            if(Math.abs(prevZ-curZ)>0.20){
                                aceletrometroZText.setText("Acelerómetro Z: " + curZ);
                                prevZ = curZ;
                                realizoCambio = true;
                            }

                            if(realizoCambio){
                                Thread evAcel = new Thread(new RegistrarEvento("Sensor acelerometro", "X: "+curX+" Y: "+curY+" Z: "+curZ, getApplicationContext(), retrofitEvento));
                                evAcel.start();
                                realizoCambio = false;
                            }

                        } else {

                            aceletrometroXText.setText("");
                            aceletrometroYText.setText("");
                            aceletrometroZText.setText("");

                        }
                        break;
                    case Sensor.TYPE_PROXIMITY:

                        if (proximidadCheck.isChecked()) {
                            proximidadText.setText("Proximidad: " + event.values[0] + " cm");
                            Thread evProx = new Thread(new RegistrarEvento("Sensor proximidad", "Proximidad: " + event.values[0] + " cm", getApplicationContext(), retrofitEvento));
                            evProx.start();
                        }

                        break;

                    default:
                        break;
                }
                if (!proximidadCheck.isChecked())
                    proximidadText.setText("");
            }
        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener( this, acSensor, SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener( this, prSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sm.unregisterListener(this,acSensor);
        sm.unregisterListener(this,prSensor);
    }


}
