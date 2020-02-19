package com.tech.cloudnausor.ohmytennis.apicall;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    public static final String BASE_URL = "http://172.107.175.10:3002/";
    public static final String BASE_URL = "http://192.168.1.32:3004/";

//    public static final String BASE_URL = "http://52.15.190.124:3004/";

//    public static final String BASE_URL = "http://3.14.73.153:4004/";



    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS);

    public static Retrofit getClient(){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        }
        return retrofit;
    }


}

