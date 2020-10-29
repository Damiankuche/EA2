package com.example.main.login;

import com.google.gson.annotations.SerializedName;

public class LoginErrorResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("msg")
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }
}
