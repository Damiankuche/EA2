package com.example.main.eventos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventoResponse <T>{
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("env")
    private String env;

    @SerializedName("event")
    @Expose
    private T event;

    public Boolean getSuccess() {
        return success;
    }

    public String getEnv() {
        return env;
    }

    public class Event{
        @SerializedName("type_events")
        @Expose
        private String type_events;

        @SerializedName("dni")
        @Expose
        private Number dni;

        @SerializedName("description")
        @Expose
        private String description;

        @SerializedName("id")
        @Expose
        private Number id;
    }


}
