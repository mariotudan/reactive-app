package com.example.reactiveapp.util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mario on 25.6.2017..
 */

public class RetrofitUtils {
    public static final String BASE_URL = "http://139.59.156.150:9000/api/";
    //public static final String BASE_URL = "http://1.2.3.5:9000/api/";

    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = retrofit.create(clazz);

        return service;
    }
}
