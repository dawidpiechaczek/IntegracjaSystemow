package com.example.dawid.visitwroclove.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dawid.visitwroclove.utils.BasicAuthInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dawid.visitwroclove.view.activity.LoginActivity.USER_ACCESS_TOKEN;

public class ServiceFactory {

    public static <T> T createRetrofitService(final Class<T> clazz, final String endPoint, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("token",Context.MODE_PRIVATE);
        String accessToken = sharedPref.getString(USER_ACCESS_TOKEN, "");
        BasicAuthInterceptor basicAuthInterceptor = new BasicAuthInterceptor(accessToken);

     //   HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
     //   interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
     //   OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(basicAuthInterceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();


        final Retrofit restAdapter = new Retrofit.Builder()
                .client(okClient)
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        T service = restAdapter.create(clazz);

        return service;
    }
}