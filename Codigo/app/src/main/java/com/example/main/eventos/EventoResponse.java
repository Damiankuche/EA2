package com.example.main.eventos;

import com.google.gson.annotations.SerializedName;

public class EventoResponse {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("env")
    private String env;

    @SerializedName("event")
    private Event event;

    public Boolean getSuccess() {
        return success;
    }

    public String getEnv() {
        return env;
    }

    public Event getEvent() {
        return event;
    }

    private class Event{
        @SerializedName("type_events")
        private String type_events;

        @SerializedName("dni")
        private Number dni;

        @SerializedName("description")
        private String description;

        @SerializedName("id")
        private Number id;

        public String getType_events() {
            return type_events;
        }

        public Number getDni() {
            return dni;
        }

        public String getDescription() {
            return description;
        }

        public Number getId() {
            return id;
        }
    }


}
