package com.volnoor.gogo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eugene on 15.09.2017.
 */

public class RetrofitClient {
    private static ApiService apiService;

    private static final String ROOT_URL = "https://api.github.com";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofitInstance().create(ApiService.class);
        }
        return apiService;
    }
}
