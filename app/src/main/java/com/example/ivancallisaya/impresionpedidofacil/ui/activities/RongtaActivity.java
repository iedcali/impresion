package com.example.ivancallisaya.impresionpedidofacil.ui.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.example.ivancallisaya.impresionpedidofacil.model.mPedidoFactura;
import com.example.ivancallisaya.impresionpedidofacil.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class RongtaActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;
    private byte[]pdfByte;
    File file;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    APIService mAPIService;
    private static final String PEDIDOFACIL="PedidoFacil";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impresion_rongta);
        checkPermission();
        ObtenerFacturar();

        Button btnPrint= findViewById(R.id.btnPrint);
        Button btnHelp= findViewById(R.id.btnHelp);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    printRongta();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help();


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "OK Permissions granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Permissions are not granted ! " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //Intenta abrir el reporte en pdf para que este sea abierto con el Intent
        void printRongta() throws IOException {

        BluetoothAdapter state=mBluetoothAdapter.getDefaultAdapter();
        accederBT();
        if (state.isEnabled()) {
            try {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                file = new File(Environment.getExternalStoragePublicDirectory(PEDIDOFACIL), file.getName());
                Uri uri;
                if (Build.VERSION.SDK_INT < 24) {
                    uri = Uri.fromFile(file);
                } else {
                    uri = Uri.parse(file.getPath()); // My work-around for new SDKs, causes ActivityNotFoundException in API 10.
                }
                Intent viewFile = new Intent(Intent.ACTION_VIEW);
                viewFile.setDataAndType(uri, "application/pdf");
                startActivity(viewFile);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{
            Toast.makeText(this,"Dispostivo no conectado a Bluetooth",Toast.LENGTH_SHORT).show();
        }

    }

    // Solicita activar Bluetooth
    void accederBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
            }
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Abre el activity de ayuda para impresoras marca Rongta
    void help(){
        Intent ayudaIntent = new Intent(getApplicationContext(), AyudaActivity.class);
        startActivity(ayudaIntent);
    }


    public void ObtenerFacturar() {
        APIService interfas = ApiUtils.getAPIServicepago();
        Call<MPaquetePedidoFacil<mPedidoFactura>> post = interfas.getFactura(1020, 6527,6,2663062);
        Callback callback = new CallbackPSP<mPedidoFactura>(RongtaActivity.this, true) {
            @Override
            public void onResponseCorrect(mPedidoFactura value) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    Log.d("nombreeeeeee",value.nombreFactura);
                    String fileName = value.nombreFactura+"_serv" + ".pdf";
                    Log.i(getLocalClassName(),fileName);
                    try {
                        file = Utils.writeFile(value.facturaPDF, fileName);
                    }catch (Exception e){
                        Log.e(getLocalClassName(),"Error al generar la factura"+e.toString());
                    }
                }

            @Override
            public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
                super.onFailure(call, t);
            }


        };
        post.enqueue(callback);
    }

    private void checkPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_CODE_ASK_PERMISSIONS);

                Toast.makeText(this, "Requesting permissions", Toast.LENGTH_LONG).show();

            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){

            }

        }

        return;
    }


}
