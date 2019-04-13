package com.example.ivancallisaya.impresionpedidofacil.model;

public class mPedidoRealizado {
    private int Empresa;
    private String Nombre;
    private String UrlLogo;
    private int Pedido;
    private String Fecha;
    private String Hora;
    private double TotalPedido;
    private double PrecioEnvio;
    private String Producto;
    private double Cantidad;

    public mPedidoRealizado(int empresa, String nombre, String urlLogo, int pedido, String fecha, String hora, double totalPedido, double precioEnvio, String producto, double cantidad) {
        Empresa = empresa;
        Nombre = nombre;
        UrlLogo = urlLogo;
        Pedido = pedido;
        Fecha = fecha;
        Hora = hora;
        TotalPedido = totalPedido;
        PrecioEnvio = precioEnvio;
        Producto = producto;
        Cantidad = cantidad;
    }

    public int getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(int empresa) {
        Empresa = empresa;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUrlLogo() {
        return UrlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        UrlLogo = urlLogo;
    }

    public int getPedido() {
        return Pedido;
    }

    public void setPedido(int pedido) {
        Pedido = pedido;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public double getTotalPedido() {
        return TotalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        TotalPedido = totalPedido;
    }

    public double getPrecioEnvio() {
        return PrecioEnvio;
    }

    public void setPrecioEnvio(double precioEnvio) {
        PrecioEnvio = precioEnvio;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(double cantidad) {
        Cantidad = cantidad;
    }
}
