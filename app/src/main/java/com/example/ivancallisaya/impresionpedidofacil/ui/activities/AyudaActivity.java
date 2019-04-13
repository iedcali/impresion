package com.example.ivancallisaya.impresionpedidofacil.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.ivancallisaya.impresionpedidofacil.R;

public class AyudaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_rongta);
        ImageButton imgbtnHelp=findViewById(R.id.btnImghelp);
        imgbtnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playstore();
            }
        });


    }

    public void playstore(){
        Intent playstore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mocoo.hang.rtprinter"));
        startActivity(playstore);
    }
}
