package com.example.ivancallisaya.impresionpedidofacil.core2;

import android.content.Context;
import android.util.Log;

import com.example.ivancallisaya.impresionpedidofacil.model.MPaquetePedidoFacil;
import com.example.ivancallisaya.impresionpedidofacil.util.Messages;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CallbackPSP<T> implements Callback<MPaquetePedidoFacil> {

    private Context context;
    private MyDialogProgress myDialogProgress;
    private boolean dialog;
    protected static String messageError = "";
    protected static String messagesistema="";

    public enum Errores{
        ErrorHTTP, ErrorHTTPNull, ErrorNull, ErrorStatus, ErrorError, ErrorValueNull, ErrorUnknown, ErrorKnown
    }

    public CallbackPSP(Context context, boolean dialog){
        this.context = context;
        this.dialog = dialog;
        messageError = "";

        if(dialog) {
            myDialogProgress = new MyDialogProgress(context, "Espere por favor...", false) {
                @Override
                public void onCancel() {
                    CallbackPSP.this.cancel();
                }
            };
        }
    }

    @Override
    public void onResponse(Call<MPaquetePedidoFacil> call, Response<MPaquetePedidoFacil> response) {
        MPaquetePedidoFacil<T> mPSP = response.body();
        if (response.isSuccessful() && mPSP!= null && mPSP.getStatus()==1 && mPSP.getError()==0 && mPSP.getValues()!=null) {
            T t = mPSP.getValues();
            messagesistema=mPSP.getMessageSistema();
            correct(t);
        }
        else{
            if(!response.isSuccessful()){
                String message = "HTTP Code: " + response.code() + "\nStatus message: ";
                if(response.message() == null){
                    message = message + "Unknown";
                    messageError = message;
                    error(Errores.ErrorHTTPNull);
                }
                else{
                    message = message + response.message();
                    messageError = message;
                    error(Errores.ErrorHTTP);
                }
            }else if(mPSP==null){
                messageError = "";
                error(Errores.ErrorNull);
            }else if(mPSP.getStatus()!=1){
                messageError = mPSP.getMessage();
                error(Errores.ErrorStatus);
            }else if(mPSP.getError()!=0){
                messageError = mPSP.getMessage();
                if(mPSP.getMessage().toLowerCase().contains("timeout"))
                    messageError="Se rebaso el tiempo límite de la comunicación, por favor intente de nuevo dentro de un tiempo prudente";
                error(Errores.ErrorError);
            }else if(mPSP.getValues()==null){
                error(Errores.ErrorValueNull);
                return;
            }
            /*if(!(context instanceof  IntentServicePagar) && !(context instanceof EstadoTransaccionActivity)
            && !(context instanceof MenuActivity) &&context!=null && messageError!=null && !messageError.equals("")){
                Messages.ToastError(context, messageError);
            }*/
            //error(Errores.ErrorUnknown);
           // messageError = "Error desconocido";
        }
    }

    @Override
    public void onFailure(Call<MPaquetePedidoFacil> call, Throwable t) {
        if(call.isCanceled()){
            Messages.ToastAdv(context, messageError);
            cancel();
            return;
        }

        if(t!=null) {
            //Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            String tag = "Error";
            try {
                if(context!=null){
                    if(t.toString().toLowerCase().contains("connect")){
                        Messages.ToastError(context,"Error de comunicación, revise su conexión a internet e intente de nuevo");
                    }else{
                        Messages.ToastError(context, t.toString());
                    }
                }
            }catch (Exception ignored){

            }
            Log.d(tag, "Error Paquete");
            Log.d(tag, "Message: --" + t.getMessage() + "--");
            Log.d(tag, "toString: --" + t.toString() + "--");
            Log.d(tag, "getLocalizedMessage: --" + t.getLocalizedMessage() + "--");

            messageError = "Error desconocido";
            error(Errores.ErrorKnown);
            return;
        }
        //Error desconocido
        messageError = "Error desconocido";
        Messages.ToastError(context, messageError);
        error(Errores.ErrorUnknown);
    }

    private void correct(T value){
        if(dialog) {
            myDialogProgress.dismis();
        }
        onResponseCorrect(value);
    }

    private void cancel(){
        onResponseCancel();
    }

    private void error(Errores errores){
        if(dialog) {
            myDialogProgress.dismis();
        }
        onResponseError(errores);
    }

    public abstract void onResponseCorrect(T value);

    public void onResponseCancel(){
    }

    public void onResponseError(Errores errores){
    }

}
