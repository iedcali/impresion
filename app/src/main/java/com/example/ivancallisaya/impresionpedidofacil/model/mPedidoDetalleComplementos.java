package com.example.ivancallisaya.impresionpedidofacil.model;

public class mPedidoDetalleComplementos {

        private int Pedido;
        private int Linea;
        private int TipoProductoPropiedad;
        private int Tabla;
        private int Serial;
        private int Cantidad;
        private int Precio;

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

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    private int Total;

    public mPedidoDetalleComplementos(int pedido, int linea, int tipoProductoPropiedad, int tabla, int serial, int cantidad, int precio, int total) {
        Pedido = pedido;
        Linea = linea;
        TipoProductoPropiedad = tipoProductoPropiedad;
        Tabla = tabla;
        Serial = serial;
        Cantidad = cantidad;
        Precio = precio;
        Total = total;
    }
}
