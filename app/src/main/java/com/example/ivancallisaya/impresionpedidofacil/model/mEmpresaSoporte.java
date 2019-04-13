package com.example.ivancallisaya.impresionpedidofacil.model;

public class mEmpresaSoporte {
    private String Empresa;
    private String Tabla;
    private String Serial;
    private String Nombre;

    public mEmpresaSoporte(String empresa, String tabla, String serial, String nombre) {
        Empresa = empresa;
        Tabla = tabla;
        Serial = serial;
        Nombre = nombre;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }

    public String getTabla() {
        return Tabla;
    }

    public void setTabla(String tabla) {
        Tabla = tabla;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String serial) {
        Serial = serial;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
