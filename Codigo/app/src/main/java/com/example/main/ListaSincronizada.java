package com.example.main;

import android.util.Log;

import java.util.LinkedList;

public class ListaSincronizada {
    private LinkedList lista = new LinkedList();

    public synchronized void addDato(Object dato){
        lista.add(dato);
        lista.notify();
    }

    public synchronized Object getDato(){
        if (lista.size()==0){
            try {
                wait();
            } catch (InterruptedException e) {
                Log.e("Error en la lista", e.getMessage());
            }
        }
        Object dato = lista.get(0);
        lista.remove(0);
        return dato;
    }
}
