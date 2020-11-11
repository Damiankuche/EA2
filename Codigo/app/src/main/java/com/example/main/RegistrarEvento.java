package com.example.main;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import com.example.main.eventos.EventoErrorResponse;
import com.example.main.eventos.EventoRequest;
import com.example.main.eventos.EventoResponse;
import com.example.main.services.EventoService;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrarEvento  implements Runnable {
    public static final String TAG = RegistroActivity.class.getSimpleName();

    private String type_events;
    private String description;
    private Context context;
    private Retrofit retrofit;


    public RegistrarEvento(String type_events, String description, Context context, Retrofit retrofitEvento) {
        this.type_events = type_events;
        this.description = description;
        this.context = context;
        this.retrofit = retrofitEvento;
    }

    @Override
    public void run() {
        //TODO cambiar el tipo de mensaje que envian los sensores
        final EventoRequest request = new EventoRequest();
        request.setEnv("PROD");
        request.setType_events(type_events);
        request.setDescription(description);

        final GlobalClass globalClass = (GlobalClass)context;

        EventoService eventoService = retrofit.create(EventoService.class);

        //se envía un request de tipo evento al servidor
        Call<EventoResponse<EventoResponse.Event>> call = eventoService.evento("Bearer "+globalClass.getToken(),request);
        call.enqueue(new Callback<EventoResponse<EventoResponse.Event>>(){
            @Override
            public void onResponse(Call<EventoResponse<EventoResponse.Event>> call, Response<EventoResponse<EventoResponse.Event>> response)
            {

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Evento "+request.getType_events() +" registrado.", Toast.LENGTH_SHORT).show();
                    Thread agregar = new Thread(new CargarDatoLista(description,globalClass));
                    agregar.start();
                } else {

                    Gson gson = new Gson();
                    EventoErrorResponse eventoErrorResponse;

                    try {
                        eventoErrorResponse = gson.fromJson(
                                response.errorBody().string(),
                                EventoErrorResponse.class);

                        Toast.makeText(context, eventoErrorResponse.getMsg(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG,eventoErrorResponse.getMsg());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

           @Override
            public void onFailure(Call<EventoResponse<EventoResponse.Event>> call, Throwable t) {
                Log.e("Error de eventos:",t.getMessage());
            }
        });


    }


}
