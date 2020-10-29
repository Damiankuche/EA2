package com.example.main.registration;

import com.google.gson.annotations.SerializedName;

public class RegistrationErrorResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("env")
    private String env;

    @SerializedName("msg")
    private String msg;

    public String getEnv() {
        return env;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }


}
