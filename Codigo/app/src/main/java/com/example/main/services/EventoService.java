package com.example.main.services;

import com.example.main.eventos.EventoRequest;
import com.example.main.eventos.EventoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EventoService {
    @POST("api/event")
    Call<EventoResponse> evento(@Header("Authorization")String authToken, @Body EventoRequest request);
}
