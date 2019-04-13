package com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars;

public class Complemento {
    public String Cantidad;
    public String Nombre;
    public double PrecioTotal;
    public double PrecioUnidad;

    public Complemento( String nombre,String cantidad, double precioUnidad, double precioTotal) {
        Cantidad = cantidad;
        Nombre = nombre;
        PrecioTotal = precioTotal;
        PrecioUnidad = precioUnidad;
    }



    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public double getPrecioTotal() {
        return PrecioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        PrecioTotal = precioTotal;
    }

    public double getPrecioUnidad() {
        return PrecioUnidad;
    }

    public void setPrecioUnidad(double precioUnidad) {
        PrecioUnidad = precioUnidad;
    }
}
