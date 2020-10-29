package com.example.main.registration;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {
    @SerializedName("env")
    private String env;
    @SerializedName("name")
    private String name;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("dni")
    private Long dni;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("commission")
    private Long commission;

    public String getEnv() {
        return env;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Long getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getCommission() {
        return commission;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCommission(Long commission) {
        this.commission = commission;
    }
}
