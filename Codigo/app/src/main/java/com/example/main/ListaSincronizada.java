package com.example.main;

import android.util.Log;

import java.util.LinkedList;

public class ListaSincronizada {
    private LinkedList lista = new LinkedList();

    public void addDato(Object dato) {
        synchronized (lista) {
            try {
                lista.add(dato);
                lista.notify();
            } catch (Exception e) {
                Log.e("Error en la lista", e.getMessage());
            }
        }
    }

    public void despertar(){
        synchronized (lista){
            lista.notify();
        }
    }

    public Object getDato(){
        synchronized (lista) {
            if (lista.size() == 0) {
                try {
                    lista.wait();
                } catch (InterruptedException e) {
                    Log.e("Error en la lista", e.getMessage());
                }
            }
            if (lista.size() == 0)
                return null;
            Object dato = lista.get(0);
            lista.remove(0);
            return dato;
        }
    }


}
