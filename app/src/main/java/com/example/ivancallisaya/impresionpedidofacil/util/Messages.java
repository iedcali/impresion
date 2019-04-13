package com.example.ivancallisaya.impresionpedidofacil.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import com.andrognito.flashbar.Flashbar;
import com.andrognito.flashbar.anim.FlashAnim;
import com.example.ivancallisaya.impresionpedidofacil.R;


import org.jetbrains.annotations.NotNull;


public class Messages {

    private static final int TOASTNORMAL = 886;
    private static final int TOASTERROR = 417;
    private static final int TOASTADVERTENCIA = 30;

    public static void ToastNormal(Context context, String message){
        showCustomDialog(context, message, TOASTNORMAL);
    }

    public static void ToastError(Context context, String message) {
        showCustomDialog(context, message, TOASTERROR);
    }

    public static void ToastAdv(Context context, String message) {
        showCustomDialog(context, message, TOASTADVERTENCIA);
    }

    private static void showCustomDialog(Context c, String mensaje, int type){
        if(mensaje!=null){
            if(mensaje.toLowerCase().contains("timeout"))
                mensaje="Se excedió el tiempo límite, por favor intente de nuevo.";
            else if(mensaje.toLowerCase().contains("connect"))
                mensaje= "error de comunicación, por favor revise su conexión a internet e intente de neuevo.";
            final Dialog dialog = new Dialog(c);
          /*  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            if(type==TOASTNORMAL){
                dialog.setContentView(R.layout.alerta_normal);
            }else if(type==TOASTADVERTENCIA){
                dialog.setContentView(R.layout.alerta_advertencia);
            }else if(type==TOASTERROR){
                dialog.setContentView(R.layout.alerta_error);
            }
            TextView text = dialog.findViewById(R.id.id_mensaje);
            text.setText(mensaje);

            LinearLayout l=dialog.findViewById(R.id.id_linear);
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.setCanceledOnTouchOutside(true);

            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            try {
                dialog.show();
            }catch (Exception ex){

            }*/


            new Flashbar.Builder((Activity) c)
                    .gravity(Flashbar.Gravity.BOTTOM)
                    .message(mensaje)
                    .messageColorRes(R.color.blanco)
                    .backgroundColorRes(R.color.colorPrimary)
                    .showOverlay()
                    .dismissOnTapOutside()
                    .listenBarTaps(new Flashbar.OnTapListener() {
                        @Override
                        public void onTap(@NotNull Flashbar flashbar) {
                            flashbar.dismiss();
                        }
                    })
                    .enableSwipeToDismiss()
                    .enterAnimation(FlashAnim.with(c)
                            .animateBar()
                            .duration(400)
                            .slideFromLeft()
                            .overshoot())
                    .exitAnimation(FlashAnim.with(c)
                            .animateBar()
                            .duration(250)
                            .slideFromLeft()
                            .accelerate())
                    .build()
                    .show();
        }
    }
}