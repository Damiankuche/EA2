package com.example.main.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("toekn")
    private String token;

    @SerializedName("token_refresh")
    private String token_refresh;

    public Boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public String getToken_refresh() {
        return token_refresh;
    }
}
