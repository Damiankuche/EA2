package com.example.main;

import android.content.Context;
import android.util.Log;


import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.Date;

public class AlmacenarDatos implements Runnable {

    private Context context;
    private String texto;
    private String nombre = "log.txt";

    public AlmacenarDatos(Context applicationContext) {
        context = applicationContext;
    }

    @Override
    public void run() {

        GlobalClass globalClass = (GlobalClass)context;
        try {
                OutputStreamWriter file = new OutputStreamWriter(globalClass.openFileOutput(nombre,globalClass.MODE_APPEND));
                while (globalClass.isRunning()) {
                    texto = (String) (globalClass.getLista().getDato());
                    if(texto != null)
                        file.write(DateFormat.getDateTimeInstance().format(new Date()) + " - " + texto+"\n");
                }
                file.flush();
                file.close();

        }
        catch (Exception e){
            Log.e("Almacenar datos",e.getMessage());
        }

    }

}
