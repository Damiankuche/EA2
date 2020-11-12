package com.example.main;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class LogActivity extends AppCompatActivity {
    //TODO guardar en el archivo los eventos en un hilo aparte y borrar el hardcode
    ImageButton btnAtras;
    TextView campoTexto;
    String texto;
    private String nombre = "log.txt";
    Handler handler = new Handler();
    GlobalClass globalClass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass)getApplicationContext();
        globalClass.setRunning(false);
        mostrarDatos();
        setContentView(R.layout.activity_log);

        btnAtras = findViewById(R.id.atrasButton);
        campoTexto = findViewById(R.id.logText);

        campoTexto.setMovementMethod(new ScrollingMovementMethod());

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mostrarDatos();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        globalClass.setRunning(true);
        Thread almacenar = new Thread(new AlmacenarDatos(getApplicationContext()));
        almacenar.start();
    }

    public void mostrarDatos(){
        handler.post(new Runnable(){
            @Override
            public void run() {

                globalClass.getLista().despertar();
                try{

                        InputStreamReader archivo = new InputStreamReader(
                                openFileInput(nombre));
                        BufferedReader br = new BufferedReader(archivo);
                        String linea = br.readLine();
                        texto = "";
                        while (linea != null) {
                            texto = texto + linea + "\n";
                            linea = br.readLine();
                        }
                        br.close();
                        archivo.close();

                }catch (IOException e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
                }

                campoTexto.setText(texto);

            }
        });

    }
}
