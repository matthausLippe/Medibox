package br.com.fiap.medibox.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.0.5:8080/";

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd@HH:mm").create();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
