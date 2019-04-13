package com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars;

public class Opciones {
    public String Nombre;
    public String Cantidad;


    public Opciones(String nombre, String cantidad) {
        Nombre = nombre;
        Cantidad = cantidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
}
