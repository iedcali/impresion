package com.example.ivancallisaya.impresionpedidofacil.core2;


import com.example.ivancallisaya.impresionpedidofacil.model.MPaquetePedidoFacil;
import com.example.ivancallisaya.impresionpedidofacil.model.mEmpresaSoporte;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoDetalle;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoDetalleComplementos;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoDetalleOpciones;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoFactura;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoRealizado;
import com.example.ivancallisaya.impresionpedidofacil.model.mProductoEmpresa;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    String pcPedidoFacil = "/ServicioSyscoopPedidoFacil";
    String pcPagoFacil ="/ServicioSyscoopPagoFacil2";

    //-------------------------------------------------------------------------------
    //                                   FACTURA
    //-------------------------------------------------------------------------------
    @POST(pcPagoFacil + "/Factura/ObtenerFacturaPDF2")
    @FormUrlEncoded
    Call<MPaquetePedidoFacil<mPedidoFactura>> getFactura(
            @Field("tnCliente") int tnCliente,
            @Field("tnTransaccionDePago") int tnTransaccionDePago,
            @Field("tnEmpresa") int tnEmpresa,
            @Field("tnFactura") int tnFactura);

    //-------------------------------------------------------------------------------
    //                                   PEDIDO
    //-------------------------------------------------------------------------------
    @POST(pcPedidoFacil + "/Pedido/getPedidos")
    @FormUrlEncoded
    Call<MPaquetePedidoFacil<ArrayList<mPedidoRealizado>>> getPedidos(
            @Field("tnCliente") int tnCliente);

    //-------------------------------------------------------------------------------
    //                                   EMPRESA SOPORTE
    //-------------------------------------------------------------------------------
    @POST(pcPedidoFacil + "/Producto/getEmpresaSoporte")
    @FormUrlEncoded
    Call<MPaquetePedidoFacil<ArrayList<mEmpresaSoporte>>> getEmpresaSoporte(
            @Field("tnCliente") int tnCliente,
            @Field("tnEmpresa") int tnEmpresa);




    //-------------------------------------------------------------------------------
    //                                   DETALLE PEDIDO
    //-------------------------------------------------------------------------------
    @POST(pcPedidoFacil + "/Pedido/getPedidoDetalle")
    @FormUrlEncoded
    Call<MPaquetePedidoFacil<ArrayList<mPedidoDetalle>>> getPedidoDetalle(
            @Field("tnCliente") int tnCliente,
            @Field("tnPedido") int tnPedido);
    //-------------------------------------------------------------------------------
    //                                   PRODUCTO EMPRESA
    //-------------------------------------------------------------------------------
    @POST(pcPedidoFacil + "/Empresa/getProductos")
    @FormUrlEncoded
    Call<MPaquetePedidoFacil<ArrayList<mProductoEmpresa>>> getProductos(
            @Field("tnCliente") int tnCliente,
            @Field("tnEmpresa") int tnEmpresa);

    //-------------------------------------------------------------------------------
    //                                   PEDIDO DETALLE OPCIONES
    //-------------------------------------------------------------------------------
    @POST(pcPedidoFacil + "/Pedido/getPedidoDetalleOpciones")
    @FormUrlEncoded
    Call<MPaquetePedidoFacil<ArrayList<mPedidoDetalleOpciones>>> getPedidoDetalleOpciones(
            @Field("tnCliente") int tnCliente,
            @Field("tnPedido") int tnPedido);

    //-------------------------------------------------------------------------------
    //                                   PEDIDO DETALLE COMPLEMENTOs
    //-------------------------------------------------------------------------------
    @POST(pcPedidoFacil + "/Pedido/getPedidoDetalleComplementos")
    @FormUrlEncoded
    Call<MPaquetePedidoFacil<ArrayList<mPedidoDetalleComplementos>>> getPedidoDetalleComplementos(
            @Field("tnCliente") int tnCliente,
            @Field("tnPedido") int tnPedido);


}
