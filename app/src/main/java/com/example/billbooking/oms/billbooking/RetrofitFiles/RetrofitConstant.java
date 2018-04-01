package com.example.billbooking.oms.billbooking.RetrofitFiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Suriya on 10-11-2017.
 */

public class RetrofitConstant {

    public static final String MOBILE_NUMBER = "mobile_number";


    public static final String BASE_URL = "http://www.billbookonline.com/";

    public static final String APPICATION_NAME = "Munjid";

    public static final String REGISTER_OPERATION = "register";
    public static final int LOGIN_OPERATION = 1;
    public static final String VERIFY_PHONE = "numberExists";

    public static final String TAG = "varsha vikshith";


    private static Retrofit retrofit;

    public static Retrofit getApiClient() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }

    public static ApiService getApiServices() {
        return getApiClient().create(ApiService.class);
    }


}
