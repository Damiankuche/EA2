package com.example.main.actualizar.token;

import com.google.gson.annotations.SerializedName;

public class ActualizarTokenErrorResponse {

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
