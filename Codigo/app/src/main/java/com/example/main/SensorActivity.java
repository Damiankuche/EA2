package com.example.main;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor acSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor prSensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sm.registerListener( this, acSensor, SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener( this, prSensor, SensorManager.SENSOR_DELAY_GAME);
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

    }
        @Override
        public void onSensorChanged(SensorEvent event) {
            synchronized (this) {
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        if (acelerometroCheck.isChecked()) {

                            long current_time = event.timestamp;

                            curX = event.values[0];
                            curY = event.values[1];
                            curZ = event.values[2];

                            if (prevX == 0 && prevY == 0 && prevZ == 0) {
                                last_update = current_time;
                                last_movement = current_time;
                                prevX = curX;
                                prevY = curY;
                                prevZ = curZ;
                            }

                            long time_difference = current_time - last_update;
                            if (time_difference > 0) {
                                float movement = Math.abs((curX + curY + curZ) - (prevX - prevY - prevZ)) / time_difference;
                                prevX = curX;
                                prevY = curY;
                                prevZ = curZ;
                                last_update = current_time;
                            }


                            aceletrometroXText.setText("Acelerómetro X: " + curX);
                            aceletrometroYText.setText("Acelerómetro Y: " + curY);
                            aceletrometroZText.setText("Acelerómetro Z: " + curZ);

                        } else {

                            aceletrometroXText.setText("");
                            aceletrometroYText.setText("");
                            aceletrometroZText.setText("");

                        }
                        break;
                    case Sensor.TYPE_PROXIMITY:

                        if (proximidadCheck.isChecked()) {
                            proximidadText.setText("Proximidad: " + event.values[0] + " cm");
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
