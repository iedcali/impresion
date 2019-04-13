package com.example.ivancallisaya.impresionpedidofacil.core2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public abstract class MyDialogProgress {

    private ProgressDialog progressDialog;

    public MyDialogProgress(Context context, String message, boolean cancel){
        showProgressDialog(context, message, cancel);
    }

    private void showProgressDialog(Context context, String message, final boolean cancel){
        if(context!=null && !((Activity) context).isFinishing()){
            progressDialog = new ProgressDialog(context);
            if(message!=null) {
                progressDialog.setMessage(message);
            }
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    MyDialogProgress.this.onCancel();
                }
            });
            progressDialog.setCancelable(cancel);
            try {
                progressDialog.show();
            }catch (Exception ex){

            }
        }
    }

    public abstract void onCancel();

    /**
     * Se completo la tarea
     */
    public void dismis(){
        try {
            progressDialog.dismiss();
        }catch (Exception ex){

        }
    }

}
