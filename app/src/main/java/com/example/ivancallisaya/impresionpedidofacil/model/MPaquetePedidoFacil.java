package com.example.ivancallisaya.impresionpedidofacil.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MPaquetePedidoFacil<T> {

    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("messageMostrar")
    @Expose
    private Integer messageMostrar;

    @SerializedName("messageSistema")
    @Expose
    private String messageSistema;

    @SerializedName("values")
    @Expose
    private T values;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageMostrar() {
        return messageMostrar;
    }

    public void setMessageMostrar(Integer messageMostrar) {
        this.messageMostrar = messageMostrar;
    }

    public String getMessageSistema() {
        return messageSistema;
    }

    public void setMessageSistema(String messageSistema) {
        this.messageSistema = messageSistema;
    }

    public T getValues() {
        return values;
    }

    public void setValues(T values) {
        this.values = values;
    }
}