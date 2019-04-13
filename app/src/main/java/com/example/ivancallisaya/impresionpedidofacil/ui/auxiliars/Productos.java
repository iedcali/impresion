package com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars;



import java.util.ArrayList;

public class Productos {
    private String Nombre;
    private String Cantidad;
    private double PrecioTotal;
    private double PrecioUnidad;
    private String Aclaracion;

    private ArrayList<Complemento> Complementos;
    private ArrayList<Opciones> Opciones;




    public Productos(String nombre, String cantidad, double precioTotal, double precioUnidad, String aclaracion, ArrayList<Complemento> complementos, ArrayList<Opciones> opciones) {
        Nombre = nombre;
        Cantidad = cantidad;
        PrecioTotal = precioTotal;
        PrecioUnidad = precioUnidad;
        Aclaracion=aclaracion;
        Complementos = complementos;
        Opciones = opciones;
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

    public ArrayList<Complemento> getComplementos() {
        return Complementos;
    }

    public void setComplementos(ArrayList<Complemento> complementos) {
        Complementos = complementos;
    }

    public ArrayList<Opciones> getOpciones() {
        return Opciones;
    }

    public void setOpciones(ArrayList<Opciones> opciones) {
        Opciones = opciones;
    }

    public String getAclaracion() {
        return Aclaracion;
    }

    public void setAclaracion(String aclaracion) {
        Aclaracion = aclaracion;
    }


}
