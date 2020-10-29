package com.example.main.services;

import com.example.main.registration.RegistrationRequest;
import com.example.main.registration.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationService {

    @POST("api/register")
    Call<RegistrationResponse> register(@Body RegistrationRequest request);
}
