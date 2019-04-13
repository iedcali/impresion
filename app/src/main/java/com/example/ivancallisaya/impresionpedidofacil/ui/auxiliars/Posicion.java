package com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars;

public class Posicion {


        private int posicion;
        private String mensaje;

        public Posicion(int posFila, String mensaje) {
            this.posicion = posFila;
            this.mensaje = mensaje;
        }

        public int getPosicion() {
            return posicion;
        }

        public void setPosicion(int posFila) {
            this.posicion = posFila;
        }

        public String  getMensaje() {
            return mensaje;
        }

        public void setMensaje(String posColumna) {
            this.mensaje = this.mensaje+posColumna;
        }


}
