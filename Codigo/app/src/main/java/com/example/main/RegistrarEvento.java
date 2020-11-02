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
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarEvento  implements Runnable {
    public static final String TAG = RegistroActivity.class.getSimpleName();

    private String type_events;
    private String description;
    private Context context;

    public RegistrarEvento(String type_events, String description, Context context) {
        this.type_events = type_events;
        this.description = description;
        this.context = context;
    }

    public void setType_events(String type_events) {
        this.type_events = type_events;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_events() {
        return type_events;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void run() {

        EventoRequest request = new EventoRequest();
        request.setEnv("PROD");
        request.setType_events(type_events);
        request.setDescription(description);

        GlobalClass globalClass = (GlobalClass)context;

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Resources.getSystem().getString(R.string.retrofit_server)).build();

        EventoService eventoService = retrofit.create(EventoService.class);

        Call<EventoResponse<EventoResponse.Event>> call = eventoService.evento(globalClass.getToken(),request);
        call.enqueue(new Callback<EventoResponse<EventoResponse.Event>>(){
            @Override
            public void onResponse(Call<EventoResponse<EventoResponse.Event>> call, Response<EventoResponse<EventoResponse.Event>> response)
            {
                String error;

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Evento registrado.", Toast.LENGTH_LONG).show();
                } else {

                    Gson gson = new Gson();
                    EventoErrorResponse eventoErrorResponse = new EventoErrorResponse();

                    try {
                        eventoErrorResponse = gson.fromJson(
                                response.errorBody().string(),
                                EventoErrorResponse.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                Log.i(TAG, "Mensaje finalizado.");
            }

           @Override
            public void onFailure(Call<EventoResponse<EventoResponse.Event>> call, Throwable t) {
                CharSequence texto;
                texto = t.getMessage();
                Log.e("Error de eventos:", (String) texto);
            }
        });

    }


}
