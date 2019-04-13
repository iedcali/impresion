package com.example.ivancallisaya.impresionpedidofacil.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ivancallisaya.impresionpedidofacil.R;


public class ImpresionPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impresion_principal);
        Button btnRongta =findViewById(R.id.btnRongta);
        Button btnZebra =findViewById(R.id.btnZebra);
        btnZebra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               zebra();
            }
        });
        btnRongta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rongta();
            }
        });



    }
    //Abre el Activity para la impresion en impresoras marca Zebra
    public void zebra (){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Intent ZebraIntent = new Intent(getApplicationContext(), ZebraActivity.class);
            startActivity(ZebraIntent);
        }else {
            Toast.makeText(this,"Conectse a internet por favor",Toast.LENGTH_LONG).show();
        }

    }

    //Abre el activity para la impresion en impresoras marca Rongta
    public void rongta (){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Intent RongtaIntent = new Intent(getApplicationContext(), RongtaActivity.class);
            startActivity(RongtaIntent);
        }else {
            Toast.makeText(this,"Conectse a internet por favor",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");
            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
