package com.example.ivancallisaya.impresionpedidofacil.model;

public class mProductoEmpresa {

      private String Producto;
    private String Nombre;
    private String Estado;
    private String Descripcion;
    private String TipoProducto;
    private String Empresa;
    private String TipoProductoEmpresa;
    private String Posicion;

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getTipoProducto() {
        return TipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        TipoProducto = tipoProducto;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }

    public String getTipoProductoEmpresa() {
        return TipoProductoEmpresa;
    }

    public void setTipoProductoEmpresa(String tipoProductoEmpresa) {
        TipoProductoEmpresa = tipoProductoEmpresa;
    }

    public String getUrlImagen() {
        return UrlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        UrlImagen = urlImagen;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }

    public double getPrecioPorMayor() {
        return PrecioPorMayor;
    }

    public void setPrecioPorMayor(double precioPorMayor) {
        PrecioPorMayor = precioPorMayor;
    }

    public double getPrecioOferta() {
        return PrecioOferta;
    }

    public void setPrecioOferta(double precioOferta) {
        PrecioOferta = precioOferta;
    }

    public String getCodigoProductoEmpresa() {
        return CodigoProductoEmpresa;
    }

    public void setCodigoProductoEmpresa(String codigoProductoEmpresa) {
        CodigoProductoEmpresa = codigoProductoEmpresa;
    }

    public String getCodigoProductoOrigen() {
        return CodigoProductoOrigen;
    }

    public void setCodigoProductoOrigen(String codigoProductoOrigen) {
        CodigoProductoOrigen = codigoProductoOrigen;
    }

    public String getPosicion() {
        return Posicion;
    }

    public void setPosicion(String posicion) {
        Posicion = posicion;
    }

    private String UrlImagen;
    private double Precio;
    private double PrecioPorMayor;
    private double PrecioOferta;
    private String CodigoProductoEmpresa;
    private String CodigoProductoOrigen;

    public mProductoEmpresa(String producto, String nombre, String estado, String descripcion, String tipoProducto, String empresa, String tipoProductoEmpresa, String urlImagen, int precio, int precioPorMayor, int precioOferta, String codigoProductoEmpresa, String codigoProductoOrigen, String posicion) {
        Producto = producto;
        Nombre = nombre;
        Estado = estado;
        Descripcion = descripcion;
        TipoProducto = tipoProducto;
        Empresa = empresa;
        TipoProductoEmpresa = tipoProductoEmpresa;
        UrlImagen = urlImagen;
        Precio = precio;
        PrecioPorMayor = precioPorMayor;
        PrecioOferta = precioOferta;
        CodigoProductoEmpresa = codigoProductoEmpresa;
        CodigoProductoOrigen = codigoProductoOrigen;
        Posicion = posicion;
    }


}
