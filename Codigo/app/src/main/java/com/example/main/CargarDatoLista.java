package com.example.main;

import android.content.Context;

public class CargarDatoLista implements Runnable {
    private String dato;
    Context context;
    ListaSincronizada lista;

    public CargarDatoLista(String dato,Context context) {
        this.dato = dato;
        this.context = context;
    }

    @Override
    public void run() {
        GlobalClass globalClass = (GlobalClass)context;

        lista = globalClass.getLista();

        lista.addDato(dato);

    }
}
