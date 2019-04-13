package com.example.ivancallisaya.impresionpedidofacil.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

public class Utils {
    public static File writeFile(byte [] data, String fileName) {
        FileOutputStream outputStream;
        String root = Environment.getExternalStorageDirectory().toString()+"/PedidoFacil";
        File myDir = new File(root);
        myDir.mkdirs();
        File file = new File(myDir, fileName);
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
