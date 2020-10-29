package com.example.main.services;

import com.example.main.login.LoginRequest;
import com.example.main.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest request);

}
