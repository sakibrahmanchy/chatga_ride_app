package com.demoriderctg.arif.demorider.RestAPI;

/**
 * Created by Sakib Rahman on 11/18/2017.
 */

import com.demoriderctg.arif.demorider.AppConfig.AppConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = AppConstant.BASE_URL;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}