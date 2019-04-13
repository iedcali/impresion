package com.example.ivancallisaya.impresionpedidofacil.ui.activities;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.ivancallisaya.impresionpedidofacil.R;


import com.example.ivancallisaya.impresionpedidofacil.core2.APIService;
import com.example.ivancallisaya.impresionpedidofacil.core2.ApiUtils;
import com.example.ivancallisaya.impresionpedidofacil.core2.CallbackPSP;
import com.example.ivancallisaya.impresionpedidofacil.model.MPaquetePedidoFacil;
import com.example.ivancallisaya.impresionpedidofacil.model.mEmpresaSoporte;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoDetalle;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoDetalleComplementos;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoDetalleOpciones;
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoRealizado;
import com.example.ivancallisaya.impresionpedidofacil.model.mProductoEmpresa;

import com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars.Complemento;
import com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars.DeviceList;
import com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars.LibTab;
import com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars.Opciones;
import com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars.Posicion;
import com.example.ivancallisaya.impresionpedidofacil.ui.auxiliars.Productos;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;

public class ZebraActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impresion_zebra);
        //Se ejecuta los consumos de API se hace un while por que algunas veces daba error
        int i=1;
        while (i<7) {
            switch (i){
                case 1:
                    DetallePedido();
                    break;
                case 2:
                    ProductoEmpresa();
                    break;
                case 3:
                    Pedido();
                    break;
                case 4:
                    EmpresaSoporte();
                    break;
                case 5:
                    detalleopciones();
                    break;
                case 6:
                    detallecomplementos();
                    break;
            }
            i++;
        }
        try {

            Button printButton = (Button) findViewById(R.id.btnOpen);
            Button connectButton = (Button) findViewById(R.id.btnPrintZebra);
            //Boton para imprimir
            printButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        gnposy = 0;
                        gnposy = 0;
                        iniy = 0;
                        iniyopc = 0;
                        iniycomp = 0;
                        String hola = cabezeraPedido(gnposy)
                                + getDatosPedido(gnposy)
                                + getDatosCliente(gnposy)
                                + printproductos()
                                + totalenvio() + facturar() + agradecimiento();

                        sendData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            //Boton para conectarse a bluetooth
            connectButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {

                        conectarbluetooth();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private static final String TAG = ZebraActivity.class.getName();
    private int gnposy = 0;
    private int iniy = 0;
    private int iniyopc = 0;
    private int iniycomp = 0;
    private int fuente = 7;
    private int fuenteprod = 5;

    private int tamanopagina= 0;

    //Datos unicos del pedido
    private String NumeroPedido = "";
    private String NombreEmpresa = "";
    private String Fecha = "";
    private String Hora = "";
    private Double PrecioEnvio = 0.00;
    private Double TotalPedido = 0.00;

    //Se instancia una ArrayList vacio de productos que pertenecen al pedido
    List<Productos> arrayproducto = new ArrayList<Productos>();

    Opciones opcion = new Opciones("", "");
    Complemento complemento = new Complemento("", "", 0.00, 0.00);

    //Se instancia un pedido vacio de Productos que tambien pueden tener un ArrayList de complementos y de Opciones dependiendo del pedido
    Productos pedido = new Productos("", "", 0.00, 0.00, "", new ArrayList<Complemento>(), new ArrayList<Opciones>());


    List<Integer> getpos = new ArrayList<Integer>();

    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private Thread workerThread;

    int tnCliente = 1260;   //Id del cliente que se envia como parametro para usar los servicios
    int tnPedido = 14;      //Id del pedido que se envia como parametro para usar los servicios
    int tnEmpresa = 2;      //Id de la empresa que se envia como parametro para usar los servicios

    ArrayList<mPedidoDetalle> product = new ArrayList<mPedidoDetalle>();
    ArrayList<mEmpresaSoporte> tablaserial = new ArrayList<mEmpresaSoporte>();

    String sendData() throws IOException {

        //Obtiene los datos del Dispositivo conectado en el activity DeviceList
        mmDevice= DeviceList.getMmDevice();
        mmOutputStream=DeviceList.mmOutputStream;
        mmInputStream=DeviceList.mmInputStream;

        if(mmDevice!=null) {

            String mensaje = "";
            gnposy = 0;
            gnposy = 0;
            iniy = 0;
            iniyopc = 0;
            iniycomp = 0;
            try {
                String cpclData = "! 0 200 200 " + String.valueOf(tamanopagina + 40) + " 1\r\n"
                        + "PAGE-HEIGHT " + String.valueOf(tamanopagina + 40) + "\r\n"
                        + "COUNTRY SPAIN\r\n"
                        + cabezeraPedido(gnposy)
                        + getDatosPedido(gnposy)
                        + getDatosCliente(gnposy)
                        + printproductos()
                        + totalenvio()
                        + facturar()
                        + agradecimiento()
                        + "PRINT\r\n";
                Log.d(TAG, cpclData);

                mmOutputStream.write(cpclData.getBytes());
            } catch (NullPointerException e) {
                e.printStackTrace();
                mensaje = "print 1";
            } catch (Exception e) {
                e.printStackTrace();
                mensaje = "Problemas al generar la impresion";
            }
            return mensaje;
        }else{
            Toast.makeText(this,"Conectese a una impresora por favor",Toast.LENGTH_LONG).show();
        }
        return "";
    }

    //Recibe la como parametro la posicion "y" del papel que inicia en 0 para imprimir la cabezera
    private String cabezeraPedido(int y) {
        String cabezera = "CENTER" + "\r\n";

        Posicion lcText = GetLinesByC(NombreEmpresa, 48, 0, y, fuente, 2);
        cabezera = cabezera + lcText.getMensaje();

        cabezera = cabezera + strToLinePrint(0, y + 35, "Barrio las Piedritas esq Vanguardia Final CONAVI", fuente, 0);
        cabezera = cabezera + strToLinePrint(280, y + 55, "Pedido N", fuente, 0);

        cabezera = cabezera + strToLinePrint(420, y + 55, NumeroPedido, fuente, 0);

        cabezera = cabezera + strToLinePrint(0, y + 85, "ENTREGA A DOMICILIO", fuente, 0);
        gnposy = gnposy + 85;
        return cabezera;
    }

    //Recibe como parametro la posicion "y" despues de haber impreso la cabezera
    private String getDatosPedido(int y) {
        String inspeccion = "";
        inspeccion = "LEFT" + "\n\r";
        inspeccion = inspeccion + strToLinePrint(0, y + 25, "Fecha:", fuente, 0);
        inspeccion = inspeccion + strToLinePrint(100, y + 25, Fecha, fuente, 0);

        // inspeccion = inspeccion + strToLinePrint(300, , siniestroDto.getSucursal(), 5, 0);
        inspeccion = inspeccion + strToLinePrint(300, y + 25, "Hora", fuente, 0);
        inspeccion = inspeccion + strToLinePrint(400, y + 25, Hora, fuente, 0);
        //inspeccion = inspeccion + strToLinePrint(300, y + 30, DateUtil.toStringDateFormat(siniestroDto.getFecha(), DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_MM_SS), 5, 0);
        inspeccion = inspeccion + strToLinePrint(0, y + 45, "Cliente : Jhasmani Junior Nina Conde", fuente, 0);

        Posicion lcText = GetLinesByL("Direccion : Avenida Luis Salazar de la Vega, Puerto Quijarro, Bolivia", 48, 0, y + 65, 5);
        ;
        inspeccion = inspeccion + lcText.getMensaje();

        return inspeccion;
    }

    //Recibe como parametro la posicion "y" despues de haber impreso los datos del pedido
    private String getDatosCliente(int y) {
        String Datoshora = "";
        Datoshora = "LEFT" + "\r\n";
        Datoshora = Datoshora + strToLinePrint(0, y, "Telefono : 75751109", fuente, 0);
        Datoshora = Datoshora + strToLinePrint(0, y + 20, "Repartidor : Angel Alvarez", fuente, 0);
        Datoshora = Datoshora + strToLinePrint(0, y + 40, "==============================================", fuente, 0);
        Datoshora = Datoshora + strToLinePrint(0, y + 55, "Cant.    Descripcion                                P/U        Total", 5, 0);
        Datoshora = Datoshora + strToLinePrint(0, y + 70, "==============================================", fuente, 0);
        gnposy = gnposy + 90;
        gnposy = gnposy;
        return Datoshora;
    }

    //Metodo para dar el formato de comando CPCL primero "x" que tiene como referencia el ancho del papel
    //"y" que tiene como referencia la altura, el texto que se envia, el tipo de fuente y el tamaño
    private String strToLinePrint(int x, int y, String texto, int font, int size) {
        String sText = "";
        sText += "TEXT " + font + " " + size + " " + x + " " + y + " " + texto + "\r\n";
        return sText;
    }


    //No recibe parametros pero si ocupa la variable global arrayproducto donde estan todos los productos
    //con sus complementos y opciones correspondientes para que se pueden cargar como un mensaje
    private String printproductos() {
        String print = "";

        for (int i = 0; i < arrayproducto.size(); i++) {
            if (arrayproducto.get(i).getOpciones().size() == 1) {
                arrayproducto.get(i).setNombre(arrayproducto.get(i).getNombre() + " " + arrayproducto.get(i).getOpciones().get(0).getNombre());
                arrayproducto.get(i).getOpciones().clear();
            }
            print += GetProductos(arrayproducto.get(i).getNombre()).getMensaje();
            print += GetCantidadProductos(arrayproducto.get(i).getCantidad()).getMensaje();
            String preciounidad = String.format("%.2f", arrayproducto.get(i).getPrecioUnidad());
            preciounidad = preciounidad.replaceAll(",", ".");
            print += GetProductosPU(preciounidad).getMensaje();
            String preciototal = String.format("%.2f", arrayproducto.get(i).getPrecioTotal());
            preciototal = preciototal.replaceAll(",", ".");
            print += GetProductosTotal(preciototal).getMensaje();

            if (arrayproducto.get(i).getOpciones().size() > 0) {

                print += GetOpciones(arrayproducto.get(i).getOpciones()).getMensaje();
                print += GetCantidadOpciones(arrayproducto.get(i).getOpciones()).getMensaje();
            }
            print += GetAclaracion(arrayproducto.get(i).getAclaracion()).getMensaje();
            if (arrayproducto.get(i).getComplementos().size() > 0) {
                print += GetComplemento(arrayproducto.get(i).getComplementos()).getMensaje();
                print += GetCantidadComplementos(arrayproducto.get(i).getComplementos()).getMensaje();
                print += GetComplementosPU(arrayproducto.get(i).getComplementos()).getMensaje();
                print += GetComplementosTotal(arrayproducto.get(i).getComplementos()).getMensaje();
            }
        }
        return print;
    }


    // Se usan las variables globales Precio Envio, PrecioEnvio, TotalPedido
    private String totalenvio() {
        String total = "";
        int[] pos = {iniy, iniycomp, iniyopc, gnposy};
        iniy = mayor(pos);
        String precioenvio = String.format("%.2f", PrecioEnvio);
        precioenvio = precioenvio.replaceAll(",", ".");
        total = total + GetLinesBy("RECARGO POR ENVIO", 60, fuente, iniy + 10, fuenteprod).getMensaje();
        total = total + GetLinesBy(precioenvio, 8, 570, iniy+10, fuenteprod).getMensaje();
        total = total + strToLinePrint(0, iniy + 20, "============================================", fuente, 0);
        String preciopedido = String.format("%.2f", TotalPedido);
        preciopedido = preciopedido.replaceAll(",", ".");
        total = total + GetLinesBy("Total Pedido :", 20, 350, iniy + 40, fuenteprod).getMensaje();
        total = total + GetLinesBy("Total General :", 20, 350, iniy + 60, fuenteprod).getMensaje();
        total = total + GetLinesBy(preciopedido, 8, 570, iniy + 40, fuenteprod).getMensaje();

        Double preciot = TotalPedido + PrecioEnvio;
        String preciototal = String.format("%.2f", preciot);
        preciototal = preciopedido.replaceAll(",", ".");

        total = total + GetLinesBy(preciototal, 8, 570, iniy + 60, fuenteprod).getMensaje();
        iniy += 60;
        return total;

    }
    //Aun no se pudiero recuperar estos datos asi que estan estaticos
    private String facturar() {

        String facturar = "";
        facturar = "LEFT" + "\n\r";
        int[] pos = {iniy, iniycomp, iniyopc, gnposy};
        iniy = mayor(pos);
        facturar = facturar + strToLinePrint(0, iniy, "Facturar A :", fuente, 0);

        facturar = facturar + strToLinePrint(150, iniy, "Jhasmani Junior Nina Conde", fuente, 0);
        facturar = facturar + strToLinePrint(0, iniy + 20, "Nit :", fuente, 0);
        facturar = facturar + strToLinePrint(80, iniy + 20, "8823782", fuente, 0);

        iniy = iniy + 40;

        return facturar;

    }
    //Aqui se consigue el tamaño de la pagina y se guarda en la variable tamanopagina
    private String agradecimiento() {

        String gracias = "CENTER" + "\r\n";
        gracias = gracias + strToLinePrint(0, iniy, "GRACIAS POR SU PREFERENCIA!!!", 0, 3);
        tamanopagina = iniy + 40;
        return gracias;
    }

    //Recibe como parametro la cantidad de un determinado producto del pedido, retorna un string con el formato
    //correspondiente para las impresoras Zebra en comando CPCL
    private Posicion GetCantidadProductos(String cant) {
     try {

            Posicion lcText = new Posicion(gnposy, "");
            lcText.setMensaje(GetLinesBy(cant,
                    8, 10, getpos.get(0), fuenteprod).getMensaje());
            asignar(lcText.getPosicion());
            return lcText;
        } catch (Exception ex) {
            String sMethod = "Error al Imprimir Pagina de Prueba";
            //e.Guardar(ex, 0, sMethod);
            return null;
        }
    }

    //Recibe como parametro la aclaracion de un producto si no tiene no escribe nada
    private Posicion GetAclaracion(String acla) {
        try {
            int[] pos = {iniy, iniycomp, iniyopc, gnposy};
            int tnposiciony = mayor(pos);
            gnposy = tnposiciony;
            Posicion lcText = new Posicion(gnposy, "");

            lcText.setMensaje(GetLinesByL(acla,
                    40, 10, gnposy, fuente).getMensaje());
            asignar(lcText.getPosicion());
            return lcText;
        } catch (Exception ex) {
            String sMethod = "Error al Imprimir Pagina de Prueba";
            return null;
        }
    }

    //Recibe como parametro un ArrayList de opciones que hay en un determinado producto y va sacando su cantidad lo retorna en formato CPCL
    //toma como referencia la posicion el la listaglobal getpos que tiene la posicion como referencia que
    //le dejo GetOpciones para escribirlo en la misma linea
    private Posicion GetCantidadOpciones(ArrayList<Opciones> cantidadOpciones) {
        try {
            Posicion lcText = new Posicion(gnposy, "");
            if (cantidadOpciones.size() > 0) // Primero Vemos si tiene opciones el producto
            {
                int i = 0;
                while (i < cantidadOpciones.size()) {
                    lcText.setMensaje(GetLinesBy(cantidadOpciones.get(i).getCantidad(),
                            8, 100, getpos.get(i), fuente).getMensaje());
                    i++;
                }
            }
            asignaropc(lcText.getPosicion());
            return lcText;
        } catch (Exception ex) {
            String sMethod = "Error al Imprimir Pagina de Prueba";
            return null;
        }
    }

    //Recibe como parametro un ArrayList de complementos que hay en un determinado producto y va sacando su cantidad lo retorna en formato CPCL
    //toma como referencia la posicion el la listaglobal getpos que tiene la posicion como referencia que
    //le dejo GetComplementos para escribirlo en la misma linea
    private Posicion GetCantidadComplementos(ArrayList<Complemento> arrayopc) {
        try {
            Posicion lcText = new Posicion(gnposy, "");

            if (arrayopc.size() > 0) // Primero Vemos si tiene complementos el producto
            {
                int i = 0;
                while (i < arrayopc.size()) {

                    lcText.setMensaje(GetLinesBy(arrayopc.get(i).getCantidad(),
                            8, 10, getpos.get(i), fuente).getMensaje());
                    i++;
                }
            }
            asignarcomp(lcText.getPosicion());
            return lcText;
        } catch (Exception ex) {
            String sMethod = "Error al Imprimir Pagina de Prueba";
            return null;
        }
    }
    //Recibe como parametro un ArrayList de opciones en un determinado producto y va sacando su nombre lo retorna en formato CPCL
    //al iniciar saca el mayor valor para iniciar con el correspondiente entre los metodos que escriben
    //complementos, la posicion "y" global, la posicion y que dejan opciones y tambien iniy que es auxiliar de la
    //la posicion global de "y" y añade la posicion a una lista de enteros para dejarlo como referencia
    // a los demas valores de opciones para se escriban en la misma linea
    private Posicion GetOpciones(ArrayList<Opciones> arrayopc) {
        int[] pos = {iniy, iniycomp, iniyopc, gnposy};
        int tnposiciony = mayor(pos);
        gnposy = tnposiciony;

        try {
            getpos.clear();
            Posicion lcText = new Posicion(gnposy, "");
            if (arrayopc.size() > 0) // Primero Vemos si opciones el producto
            {
                lcText.setMensaje(GetLinesBy("Opciones Ingredientes",
                        28, 100, gnposy, fuente).getMensaje());
                int i = 0;
                while (i < arrayopc.size()) {
                    getpos.add(gnposy);
                    lcText.setMensaje(GetLinesBy(arrayopc.get(i).Nombre,
                            28, 100, gnposy, fuente).getMensaje());
                    i++;
                }
            }
            asignaropc(lcText.getPosicion());
            return lcText;
        } catch (Exception ex) {
            String sMethod = "Error al Imprimir Pagina de Prueba";
            return null;
        }
    }

    //Recibe un String del preciounitario de un prodcuto y toma como referencia a la posicion
    // "y" que le dejo GetProductos para escribirlo en la misma linea

    private Posicion GetProductosPU(String punidad) {

        try {
            Posicion lcText = new Posicion(gnposy, "");
            lcText.setMensaje(GetLinesBy(punidad,
                    8, 490, getpos.get(0), fuenteprod).getMensaje());
            asignar(lcText.getPosicion());
            return lcText;
        } catch (Exception ex) {
            String sMethod = "Error al Imprimir Pagina de Prueba";
            //e.Guardar(ex, 0, sMethod);
            return null;
        }
    }

    //Recibe un String del preciototal de un prodcuto y toma como referencia a la posicion
    // "y" que le dejo GetProductos para escribirlo en la misma linea

    private Posicion GetProductosTotal(String total) {
        try {

            Posicion lcText = new Posicion(gnposy, "");
            lcText.setMensaje(GetLinesBy(total,
                    8, 570, getpos.get(0), fuenteprod).getMensaje());

            return lcText;

        } catch (Exception ex) {
            String sMethod = "Error al Imprimir Pagina de Prueba";
            return null;
        }

    }


    //Recibe un String del nombre del producto de un prodcuto y añade su posicion a la lista global
    //getpos para dar como referencia esa posicion a los demas productos

    private Posicion GetProductos(String nombreproducto) {

        getpos.clear();
        int[] pos = {iniy, iniycomp, iniyopc, gnposy};
        int tnposiciony = mayor(pos) + 10;
        Posicion lcText = new Posicion(tnposiciony, "");

        getpos.add(tnposiciony);
        lcText.setMensaje(GetLinesByL(nombreproducto,
                28, 50, tnposiciony, fuenteprod).getMensaje());
        asignar(lcText.getPosicion());
        return lcText;
    }

    //Funcion que recibe como parametros tcLinea que es el mensaje que se le envia,tnCountChar que es la
    // cantida maxima de caracteres que se puede escribir del mensaje, tnPosX la referencia a la posicion "x"
    // tnPosY la referencia a la posicion "y" y tnFont que es la fuente de la letra esta funcion escribe de
    //derecha a izquierda
    private Posicion GetLinesBy(String tcLinea, int tnCountChar, int tnPosX, int tnPosY, int tnFont) {
        String sResult = "";
        sResult = "RIGHT ";
        sResult += String.valueOf(tnPosX) + "\r\n";
        tcLinea = LibTab.ReplacesCharsTo(tcLinea); //
        int nLineCount = LibTab.LineCount(tcLinea, tnCountChar);
        String[] sLineas = LibTab.LinesParrafo(tcLinea, tnCountChar);

        for (int i = 0; i < nLineCount; i++) {
            if (sLineas[i] != null) {
                sResult += strToLinePrint(tnPosX, tnPosY, sLineas[i], tnFont, 0);
                tnPosY += 20;
            }
        }
        gnposy = tnPosY;
        Posicion resultado = new Posicion(tnPosY, sResult);
        return resultado;
    }
    //Funcion que recibe como parametros tcLinea que es el mensaje que se le envia,tnCountChar que es la
    // cantida maxima de caracteres que se puede escribir del mensaje, tnPosX la referencia a la posicion "x"
    // tnPosY la referencia a la posicion "y" y tnFont que es la fuente de la letra esta funcion escribe
    // desde el centro

    private Posicion GetLinesByC(String tcLinea, int tnCountChar, int tnPosX, int tnPosY, int tnFont, int size) {
        String sResult = "";
        sResult = "CENTER" + "\r\n";
        tcLinea = LibTab.ReplacesCharsTo(tcLinea); //
        int nLineCount = LibTab.LineCount(tcLinea, tnCountChar);
        String[] sLineas = LibTab.LinesParrafo(tcLinea, tnCountChar);

        for (int i = 0; i < nLineCount; i++) {
            if (sLineas[i] != null) {
                sResult += strToLinePrint(tnPosX, tnPosY, sLineas[i], tnFont, size);
                tnPosY += 20;
            }
        }
        gnposy = tnPosY;
        Posicion resultado = new Posicion(tnPosY, sResult);
        return resultado;
    }


    //Funcion que recibe como parametros tcLinea que es el mensaje que se le envia,tnCountChar que es la
    // cantida maxima de caracteres que se puede escribir del mensaje, tnPosX la referencia a la posicion "x"
    // tnPosY la referencia a la posicion "y" y tnFont que es la fuente de la letra esta funcion escribe de
    // izquierda a derecha
    private Posicion GetLinesByL(String tcLinea, int tnCountChar, int tnPosX, int tnPosY, int tnFont) {

        String sResult = "";
        sResult = "LEFT" + "\r\n";
        tcLinea = LibTab.ReplacesCharsTo(tcLinea); //
        int nLineCount = LibTab.LineCount(tcLinea, tnCountChar);
        String[] sLineas = LibTab.LinesParrafo(tcLinea, tnCountChar);

        for (int i = 0; i < nLineCount; i++) {
            if (sLineas[i] != null) {
                sResult += strToLinePrint(tnPosX, tnPosY, sLineas[i], tnFont, 0);
                tnPosY += 20;
            }
        }
        gnposy = tnPosY;
        Posicion resultado = new Posicion(tnPosY, sResult);
        return resultado;
    }

    //Recibe como parametro un ArrayList de Complementos en un determinado producto y va sacando su nombre lo retorna en formato CPCL
    //al iniciar saca el mayor valor para iniciar con el correspondiente entre los metodos que escriben
    //complementos, la posicion "y" global, la posicion y que dejan opciones y tambien iniy que es auxiliar de la
    //la posicion global de "y" y añade la posicion a una lista de enteros para dejarlo como referencia
    // a los demas valores de complementos para que se escriban en la misma linea

    private Posicion GetComplemento(ArrayList<Complemento> arrayopc) {
        {
            try {
                getpos.clear();
                int[] pos = {iniy, iniycomp, iniyopc, gnposy};
                int tnposiciony = mayor(pos);
                gnposy = tnposiciony;

                Posicion lcText = new Posicion(gnposy, "");
                if (arrayopc.size() > 0) // Primero Vemos si complementos el producto
                {
                    lcText.setMensaje(GetLinesBy("Opciones Complementos",
                            28, 10, gnposy, fuente).getMensaje());
                    int i = 0;
                    while (i < arrayopc.size()) {
                        getpos.add(gnposy);
                        lcText.setMensaje(GetLinesBy(arrayopc.get(i).getNombre(),
                                28, 50, gnposy, fuente).getMensaje());
                        i++;
                    }
                }
                asignar(lcText.getPosicion());
                return lcText;
            } catch (Exception ex) {
                String sMethod = "Error al Imprimir Pagina de Prueba";
                return null;
            }
        }
    }

    //Recibe como parametro un ArrayList de complementos que hay en un determinado producto y va sacando su Precio total lo retorna en formato CPCL
    //toma como referencia la posicion el la listaglobal getpos que tiene la posicion como referencia que
    //le dejo GetComplementos para escribirlo en la misma linea se les da el formato para tener dos decimales

    private Posicion GetComplementosTotal(ArrayList<Complemento> arrayopc) {
        {
            try {
                Posicion lcText = new Posicion(gnposy, "");
                if (arrayopc.size() > 0) // Primero Vemos si Tiene complementos el producto
                {
                    int i = 0;
                    String preciototalcomplemento = String.format("%.2f", arrayopc.get(i).getPrecioTotal());
                    preciototalcomplemento = preciototalcomplemento.replaceAll(",", ".");
                    while (i < arrayopc.size()) {
                        lcText.setMensaje(GetLinesBy(preciototalcomplemento,
                                8, 575, getpos.get(i), fuente).getMensaje());
                        i++;
                    }
                }
                asignarcomp(lcText.getPosicion());

                return lcText;
            } catch (Exception ex) {
                String sMethod = "Error al Imprimir Pagina de Prueba";
                //e.Guardar(ex, 0, sMethod);
                return null;
            }
        }
    }

    //Recibe como parametro un ArrayList de complementos que hay en un determinado producto y va sacando su Precio unitario lo retorna en formato CPCL
    //toma como referencia la posicion el la listaglobal getpos que tiene la posicion como referencia que
    //le dejo GetComplementos para escribirlo en la misma linea se les da el formato para tener dos decimales
    private Posicion GetComplementosPU(ArrayList<Complemento> arrayopc) {
        {
            try {
                Posicion lcText = new Posicion(gnposy, "");
                if (arrayopc.size() > 0) // Primero Vemos si complementos el producto
                {
                    int i = 0;
                    while (i < arrayopc.size()) {
                        String preciounidadcomplemento = String.format("%.2f", arrayopc.get(i).getPrecioUnidad());
                        preciounidadcomplemento = preciounidadcomplemento.replaceAll(",", ".");
                        lcText.setMensaje(GetLinesBy(preciounidadcomplemento,
                                8, 495, getpos.get(i), fuente).getMensaje());
                        i++;
                    }
                }
                asignarcomp(lcText.getPosicion());

                return lcText;
            } catch (Exception ex) {
                String sMethod = "Error al Imprimir Pagina de Prueba";
                //e.Guardar(ex, 0, sMethod);
                return null;
            }
        }
    }

    // asigna la posicion mayor despues de escribir los datos del producto
    private void asignar(int y) {
        if (y > iniy) {
            iniy = y;
        }
    }
    // asigna la posicion mayor despues de escribir las opciones de un producto
    private void asignaropc(int y) {
        if (y > iniyopc) {
            iniyopc = y;
        }
    }
    // asigna la posicion mayor despues de escribir los complementos de un producto
    private void asignarcomp(int y) {
        if (y > iniycomp) {
            iniycomp = y;
        }
    }

    //Funcion que tiene como parametro un vector que tiene los inicios de la posicion "y" que dejan
    //las funciones que escriben los productos, oomplementos y opciones, los comopara y devuelve el mayor
    //entre ellos
    private int mayor(int[] vector) {
        int numeromayor = vector[0];
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > numeromayor) {
                numeromayor = vector[i];
            }
        }
        return numeromayor;
    }

    //Se obtiene los datos del pedido y se los guarda en las variables globales
    public void Pedido() {

        APIService interfas = ApiUtils.getAPIService();
        Call<MPaquetePedidoFacil<ArrayList<mPedidoRealizado>>> post = interfas.getPedidos(tnCliente);

        Callback callback = new CallbackPSP<ArrayList<mPedidoRealizado>>(ZebraActivity.this, true) {
            @Override
            public void onResponseCorrect(ArrayList<mPedidoRealizado> value) {

                for (int i = 0; i < value.size(); i++) {
                    if (value.get(i).getPedido() == tnPedido) {

                        NumeroPedido = String.valueOf(value.get(i).getPedido());
                        NombreEmpresa = value.get(i).getNombre();
                        Fecha = value.get(i).getFecha();
                        Hora = value.get(i).getHora();
                        PrecioEnvio = value.get(i).getPrecioEnvio();
                        TotalPedido = value.get(i).getTotalPedido();
                    }
                }
            }

            @Override
            public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
                super.onFailure(call, t);
            }
        };
        post.enqueue(callback);
    }

    //Se obtiene el detalle del pedido y se lo guarda en un ArrayList de mPedidoDetalle
    //Para despues compararlo con los resultados del servicio de getProductoEmpresa
       public void DetallePedido() {
        APIService interfas = ApiUtils.getAPIService();
        Call<MPaquetePedidoFacil<ArrayList<mPedidoDetalle>>> postdetalle = interfas.getPedidoDetalle(tnCliente, tnPedido);

        Callback callback = new CallbackPSP<ArrayList<mPedidoDetalle>>(ZebraActivity.this, true) {
            @Override
            public void onResponseCorrect(ArrayList<mPedidoDetalle> value) {
                product = value;
            }

            @Override
            public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
                super.onFailure(call, t);
            }


        };

        postdetalle.enqueue(callback);

    }

    //Aqui se compara el ArrayList de mPedidoDetalle para Obtener el nombre de los productos, su cantidad
    //Precio unitario y total , y se lo añade a un array de productos del pedido
    public void ProductoEmpresa() {

        APIService interfas = ApiUtils.getAPIService();
        Call<MPaquetePedidoFacil<ArrayList<mProductoEmpresa>>> postdetalle = interfas.getProductos(tnCliente, tnEmpresa);

        Callback callback = new CallbackPSP<ArrayList<mProductoEmpresa>>(ZebraActivity.this, true) {
            @Override
            public void onResponseCorrect(ArrayList<mProductoEmpresa> value) {
                int i = 0;
                int j = 0;
                while (i < value.size()) {

                    if (Integer.valueOf(value.get(i).getProducto()) == (product.get(j).getProducto())) {
                        pedido.setNombre(value.get(i).getNombre());

                        int cantidad = (int) product.get(j).getCantidad();

                        pedido.setCantidad(String.valueOf(cantidad));

                        pedido.setPrecioUnidad(product.get(j).getPrecio());

                        pedido.setPrecioTotal(product.get(j).getTotal());

                        pedido.setAclaracion(String.valueOf(product.get(j).getAclaracion()));

                        arrayproducto.add(j, new Productos(pedido.getNombre(), pedido.getCantidad(),
                                pedido.getPrecioTotal(), pedido.getPrecioUnidad(), pedido.getAclaracion(), new ArrayList<Complemento>(), new ArrayList<Opciones>()));
                        j++;
                        i = 0;
                        if (j == product.size()) {
                            i = value.size();
                        }
                    }
                    i++;
                }
            }

            @Override
            public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
                super.onFailure(call, t);
            }
        };

        postdetalle.enqueue(callback);

    }

    //Se guarda la respuesta del servicio getEmpresaSoporte para despues compararlos con los Detalles opciones y detalles complementos
    public void EmpresaSoporte() {
        APIService interfas = ApiUtils.getAPIService();
        Call<MPaquetePedidoFacil<ArrayList<mEmpresaSoporte>>> post = interfas.getEmpresaSoporte(tnCliente, tnEmpresa);
        Callback callback = new CallbackPSP<ArrayList<mEmpresaSoporte>>(ZebraActivity.this, true) {
            @Override
            public void onResponseCorrect(ArrayList<mEmpresaSoporte> value) {
                tablaserial = value;
            }

            @Override
            public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
                super.onFailure(call, t);
            }
        };
        post.enqueue(callback);
    }

    //Se compara con los valores de tabla serial para sacar los nombres de las opciones si el Tipo producto es 1 se concatena
    //con el nombre de producto, si es igual a 2 se va guardando un ArrayList de opciones del producto correspondiente

    public void detalleopciones() {
        APIService interfas = ApiUtils.getAPIService();
        Call<MPaquetePedidoFacil<ArrayList<mPedidoDetalleOpciones>>> post = interfas.getPedidoDetalleOpciones(tnCliente, tnPedido);
        Callback callback = new CallbackPSP<ArrayList<mPedidoDetalleOpciones>>(ZebraActivity.this, true) {
            @Override
            public void onResponseCorrect(ArrayList<mPedidoDetalleOpciones> value) {
                int i = 0;
                int j = 0;
                ArrayList<Opciones> arrayopciontrue = new ArrayList<>();
                ArrayList<Opciones> arrayopcionelse = new ArrayList<>();
                ArrayList<Opciones> arrayopcion = new ArrayList<>();
                while (i < tablaserial.size() && value.size() > 0) {
                    if (String.valueOf(value.get(j).getTabla()).equals(tablaserial.get(i).getTabla())
                            && String.valueOf(value.get(j).getSerial()).equals(tablaserial.get(i).getSerial())) {
                        if (j == 0) {
                            ArrayList<Opciones> arrayopcion1 = new ArrayList<>();
                            opcion.setNombre(tablaserial.get(i).getNombre());
                            opcion.setCantidad(String.valueOf(value.get(j).getCantidad()));
                            if (value.get(j).getTipoProductoPropiedad() != 1) {
                                arrayopcion.add(new Opciones(opcion.getNombre(), opcion.getCantidad()));
                                arrayproducto.get(value.get(j).getLinea()-1).setOpciones(new ArrayList<Opciones>(arrayopcion));
                            }
                        }
                        if (value.get(j).getTipoProductoPropiedad()==1) {
                            String productomod = "";
                            productomod = arrayproducto.get(value.get(j).getLinea() - 1).getNombre() + " " + tablaserial.get(i).getNombre();
                            arrayproducto.get(value.get(j).getLinea() - 1).setNombre(productomod);
                        } else if (value.get(j).getTipoProductoPropiedad() == 2) {
                            if (value.get(j).getLinea() != value.get(j - 1).getLinea()) {
                                ArrayList<Opciones> arrayopciontrue1 = new ArrayList<>();
                                opcion.setNombre(tablaserial.get(i).getNombre());
                                opcion.setCantidad(String.valueOf(value.get(j).getCantidad()));
                                arrayopciontrue.add(new Opciones(opcion.getNombre(), opcion.getCantidad()));
                                arrayopciontrue1 = arrayopciontrue;
                                arrayproducto.get(value.get(j).getLinea() - 1).setOpciones(new ArrayList<Opciones>(arrayopciontrue1));
                                arrayopcionelse.clear();
                            } else {
                                ArrayList<Opciones> arrayopcionelse1 = new ArrayList<>();
                                arrayopcionelse = arrayopcion;
                                opcion.setNombre(tablaserial.get(i).getNombre());
                                opcion.setCantidad(String.valueOf(value.get(j).getCantidad()));
                                arrayopcionelse.add(new Opciones(opcion.getNombre(), opcion.getCantidad()));
                                arrayopcionelse1 = arrayopcionelse;
                                arrayproducto.get(value.get(j).getLinea() - 1).setOpciones(new ArrayList<Opciones>(arrayopcionelse1));
                                arrayopciontrue.clear();
                            }
                        }
                        j++;
                        i = 0;
                        if (j == value.size()) {
                            i = tablaserial.size();
                        }
                    }
                    i++;
                }
            }
            @Override
            public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
                super.onFailure(call, t);
            }
        };
        post.enqueue(callback);
    }

    //Se compara con los valores de tabla serial para sacar el nombre y la cantidad de los complementos que
    //existen en determinado pedido

    public void detallecomplementos() {
        APIService interfas = ApiUtils.getAPIService();
        Call<MPaquetePedidoFacil<ArrayList<mPedidoDetalleComplementos>>> post = interfas.getPedidoDetalleComplementos(tnCliente, tnPedido);
        Callback callback = new CallbackPSP<ArrayList<mPedidoDetalleComplementos>>(ZebraActivity.this, true) {
            @Override
            public void onResponseCorrect(ArrayList<mPedidoDetalleComplementos> value) {
                int i = 0;
                int j = 0;
                ArrayList<Complemento> arraycomplementotrue = new ArrayList<>();
                ArrayList<Complemento> arraycomplementoelse = new ArrayList<>();
                ArrayList<Complemento> arraycomplemento = new ArrayList<>();
                while (i < tablaserial.size() && value.size() > 0) {
                    if (String.valueOf(value.get(j).getTabla()).equals(tablaserial.get(i).getTabla())
                            && String.valueOf(value.get(j).getSerial()).equals(tablaserial.get(i).getSerial())) {
                        if (j == 0) {
                            ArrayList<Complemento> arrayopcion1 = new ArrayList<>();
                            complemento.setNombre(tablaserial.get(i).getNombre());
                            complemento.setCantidad(String.valueOf(value.get(j).getCantidad()));
                            complemento.setPrecioTotal(value.get(j).getTotal());
                            complemento.setPrecioUnidad(value.get(j).getPrecio());
                            arraycomplemento.add(new Complemento(complemento.getNombre(), complemento.getCantidad(), complemento.getPrecioUnidad(), complemento.getPrecioTotal()));
                            arrayproducto.get(value.get(j).getLinea() - 1).setComplementos(new ArrayList<Complemento>(arraycomplemento));

                        } else if (value.get(j).getLinea() != value.get(j - 1).getLinea()) {
                            ArrayList<Complemento> arrayopciontrue1 = new ArrayList<>();
                            complemento.setNombre(tablaserial.get(i).getNombre());
                            complemento.setCantidad(String.valueOf(value.get(j).getCantidad()));
                            complemento.setPrecioTotal(value.get(j).getTotal());
                            complemento.setPrecioUnidad(value.get(j).getPrecio());
                            arraycomplementotrue.add(new Complemento(complemento.getNombre(), complemento.getCantidad(), complemento.getPrecioUnidad(), complemento.getPrecioTotal()));
                            arrayopciontrue1 = arraycomplementotrue;
                            arrayproducto.get(value.get(j).getLinea() - 1).setComplementos(new ArrayList<Complemento>(arrayopciontrue1));
                            arraycomplementoelse.clear();
                        } else {
                            ArrayList<Complemento> arrayopcionelse1 = new ArrayList<>();
                            arraycomplementoelse = arraycomplemento;
                            complemento.setNombre(tablaserial.get(i).getNombre());
                            complemento.setCantidad(String.valueOf(value.get(j).getCantidad()));
                            complemento.setPrecioTotal(value.get(j).getTotal());
                            complemento.setPrecioUnidad(value.get(j).getPrecio());
                            arraycomplementoelse.add(new Complemento(complemento.getNombre(), complemento.getCantidad(), complemento.getPrecioUnidad(), complemento.getPrecioTotal()));
                            arrayopcionelse1 = arraycomplementoelse;
                            arrayproducto.get(value.get(j).getLinea() - 1).setComplementos(new ArrayList<Complemento>(arrayopcionelse1));
                            arraycomplementotrue.clear();
                        }
                        j++;
                        i = 0;
                        if (j == value.size()) {
                            i = tablaserial.size();
                        }
                    }
                    i++;
                }
            }
            @Override
            public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
                super.onFailure(call, t);
            }
        };
        post.enqueue(callback);
    }


    //Llama al Activitu que lista los dispositivos Bluetooth disponibles y se conecta con el
    public void conectarbluetooth ()throws IOException {
        if (mmSocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        }
    }


}

