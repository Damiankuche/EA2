package com.example.main;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class RegistroHandler extends Thread{
    Handler handler;
    public RegistroHandler(){
        this.start();
    }


    @Override
    public void run(){
        try{
            Looper.prepare();
            handler = new Handler();
            Looper.loop();
        }catch(Throwable t){
            Log.e("Looper","Error: ", t);
        }
    }

    public void terminate(){
        handler.getLooper().quit();
    }

    public void post(Runnable runnable){
        handler.post(runnable);
    }
}
