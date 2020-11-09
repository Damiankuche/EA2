package com.example.main.services;

import com.example.main.actualizar.token.ActualizarTokenResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface ActualizarTokenService {

    @PUT("api/refresh")
    Call<ActualizarTokenResponse> actualizar(@Header("Authorization") String authToken_Refresh);
}
