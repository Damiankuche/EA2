package com.example.main;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

public class LogActivity extends AppCompatActivity {
    //TODO guardar en el archivo los eventos y borrar el hardcode
    ImageButton btnAtras;
    TextView campoTexto;
    String texto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        btnAtras = findViewById(R.id.atrasButton);
        campoTexto = findViewById(R.id.logText);
        texto = DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se dio de alta.\n";
        texto += DateFormat.getDateTimeInstance().format(new Date())+" - se registro el sensor.\n";

        campoTexto.setText(texto);

        campoTexto.setMovementMethod(new ScrollingMovementMethod());


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
