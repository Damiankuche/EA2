package com.example.main.eventos;

import com.google.gson.annotations.SerializedName;

public class EventoErrorResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("env")
    private String env;

    @SerializedName("msg")
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public String getEnv() {
        return env;
    }

    public String getMsg() {
        return msg;
    }
}
