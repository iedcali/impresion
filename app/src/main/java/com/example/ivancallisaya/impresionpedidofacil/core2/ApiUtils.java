package com.example.ivancallisaya.impresionpedidofacil.core2;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    private ApiUtils() {}

    //http://190.186.90.42/ServicioSyscoopPagoFacil2
    //public static final String BASE_URL = "http://190.186.90.42/";

    public static final String BASE_URL = "http://190.186.187.77/";
    public static final String BASE_URL_PAGO = "http://190.186.90.42/";

    public static APIService getAPIService() {
        return getClient(BASE_URL).create(APIService.class);
    }
    public static APIService getAPIServicepago() {
        return getClient(BASE_URL_PAGO).create(APIService.class);
    }


    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
        }
        return retrofit;
    }
}
