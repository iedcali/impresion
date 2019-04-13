package com.example.ivancallisaya.impresionpedidofacil.model;

public class mPedidoDetalle {

    private int Pedido ;
    private int Linea;
    private int Producto;
    private double Precio;
    private int Variedad;
    private int SerialVariedad;
    private String Aclaracion;
    private double Cantidad;

    private double Descuento;
    private double SubTotal;
    private double Total;

    public int getPedido() {
        return Pedido;
    }

    public void setPedido(int pedido) {
        Pedido = pedido;
    }

    public int getLinea() {
        return Linea;
    }

    public void setLinea(int linea) {
        Linea = linea;
    }

    public int getProducto() {
        return Producto;
    }

    public void setProducto(int producto) {
        Producto = producto;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public int getVariedad() {
        return Variedad;
    }

    public void setVariedad(int variedad) {
        Variedad = variedad;
    }

    public int getSerialVariedad() {
        return SerialVariedad;
    }

    public void setSerialVariedad(int serialVariedad) {
        SerialVariedad = serialVariedad;
    }

    public String getAclaracion() {
        return Aclaracion;
    }

    public void setAclaracion(String aclaracion) {
        Aclaracion = aclaracion;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }

    public double getDescuento() {
        return Descuento;
    }

    public void setDescuento(double descuento) {
        Descuento = descuento;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public mPedidoDetalle(int pedido, int linea, int producto, double precio, int variedad, int serialVariedad, String aclaracion, double cantidad, double descuento, double subTotal, double total) {

        Pedido = pedido;
        Linea = linea;
        Producto = producto;
        Precio = precio;
        Variedad = variedad;
        SerialVariedad = serialVariedad;
        Aclaracion = aclaracion;
        Cantidad = cantidad;
        Descuento = descuento;
        SubTotal = subTotal;
        Total = total;
    }
}
