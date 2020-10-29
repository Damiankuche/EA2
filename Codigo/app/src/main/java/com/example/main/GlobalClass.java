package com.example.main;

import android.app.Application;

public class GlobalClass extends Application {
    private String token;
    private String refresh_token;

    public String getToken() {
        return token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
