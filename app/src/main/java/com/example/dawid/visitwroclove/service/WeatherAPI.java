package com.example.dawid.visitwroclove.service;


import android.content.Context;

import com.example.dawid.visitwroclove.model.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface WeatherAPI {

    String SERVICE_ENDPOINT = "https://api.darksky.net/forecast/";


    @GET("4f8e0bf02f133bdcf8677263a1f12635/51.06,17.01")
    Observable<WeatherResponse> getWeather();


    class Factory {
        public static WeatherAPI create(Context context) {
            return ServiceFactory.createRetrofitWeatherService(WeatherAPI.class, SERVICE_ENDPOINT, context);
        }
    }
}