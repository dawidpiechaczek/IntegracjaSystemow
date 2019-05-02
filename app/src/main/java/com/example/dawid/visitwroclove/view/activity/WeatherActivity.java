package com.example.dawid.visitwroclove.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.PaymentData;
import com.example.dawid.visitwroclove.model.Response;
import com.example.dawid.visitwroclove.model.WeatherResponse;
import com.example.dawid.visitwroclove.service.VisitWroAPI;
import com.example.dawid.visitwroclove.service.WeatherAPI;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherActivity extends BaseActivity {
    @BindView(R.id.image_weather)
    ImageView imageView;
    @BindView(R.id.temperature)
    TextView temperature;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.weather_activity);
        ButterKnife.bind(this);
        showWeather(getIntent().getStringExtra("weather"));
    }

    private void showWeather(String weather) {
        Gson gson = new Gson();
        WeatherResponse weatherResponse = gson.fromJson(weather, WeatherResponse.class);
        temperature.setText(farenheitToCelcius(weatherResponse.getCurrently().getApparentTemperature()));
        String fileName = weatherResponse.getCurrently().getIcon().replace("-","");
        imageView.setImageDrawable(getImage(this,fileName));

    }

    private String farenheitToCelcius(double farenheit) {
        return String.format("%.2f", (farenheit - 32) * 5 / 9) + " °C";
    }

    private Drawable getImage(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }
}