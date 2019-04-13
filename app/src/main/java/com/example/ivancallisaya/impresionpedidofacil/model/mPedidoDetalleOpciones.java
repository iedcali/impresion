package com.example.ivancallisaya.impresionpedidofacil.model;

public class mPedidoDetalleOpciones {
    private int Pedido;
    private int Linea;
    private int Producto;
    private int TipoProductoPropiedad;
    private int Tabla;
    private int Serial;
    private int Cantidad;

    public mPedidoDetalleOpciones(int pedido, int linea, int producto, int tipoProductoPropiedad, int tabla, int serial, int cantidad) {
        Pedido = pedido;
        Linea = linea;
        Producto = producto;
        TipoProductoPropiedad = tipoProductoPropiedad;
        Tabla = tabla;
        Serial = serial;
        Cantidad = cantidad;
    }

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

    public int getTipoProductoPropiedad() {
        return TipoProductoPropiedad;
    }

    public void setTipoProductoPropiedad(int tipoProductoPropiedad) {
        TipoProductoPropiedad = tipoProductoPropiedad;
    }

    public int getTabla() {
        return Tabla;
    }

    public void setTabla(int tabla) {
        Tabla = tabla;
    }

    public int getSerial() {
        return Serial;
    }

    public void setSerial(int serial) {
        Serial = serial;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }


}
