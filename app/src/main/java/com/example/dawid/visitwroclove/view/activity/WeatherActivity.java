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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    @BindView(R.id.image_weather1)
    ImageView imageView1;
    @BindView(R.id.temperature1)
    TextView temperature1;
    @BindView(R.id.image_weather2)
    ImageView imageView2;
    @BindView(R.id.temperature2)
    TextView temperature2;
    @BindView(R.id.image_weather3)
    ImageView imageView3;
    @BindView(R.id.temperature3)
    TextView temperature3;
    @BindView(R.id.image_weather4)
    ImageView imageView4;
    @BindView(R.id.temperature4)
    TextView temperature4;
    @BindView(R.id.image_weather5)
    ImageView imageView5;
    @BindView(R.id.temperature5)
    TextView temperature5;
    @BindView(R.id.day1)
    TextView day1;
    @BindView(R.id.day2)
    TextView day2;
    @BindView(R.id.day3)
    TextView day3;
    @BindView(R.id.day4)
    TextView day4;
    @BindView(R.id.day5)
    TextView day5;

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
        String fileName = weatherResponse.getCurrently().getIcon().replace("-", "");
        imageView.setImageDrawable(getImage(this, fileName));

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        String fileName1 = weatherResponse.getDaily().getData().get(0).getIcon().replace("-", "");
        imageView1.setImageDrawable(getImage(this, fileName1));
        temperature1.setText(farenheitToCelcius(weatherResponse.getDaily().getData().get(0).getApparentTemperatureMax()));
        day1.setText(getDay(day + 1));

        String fileName2 = weatherResponse.getDaily().getData().get(1).getIcon().replace("-", "");
        imageView2.setImageDrawable(getImage(this, fileName2));
        temperature2.setText(farenheitToCelcius(weatherResponse.getDaily().getData().get(1).getApparentTemperatureMax()));
        day2.setText(getDay(day + 2));

        String fileName3 = weatherResponse.getDaily().getData().get(2).getIcon().replace("-", "");
        imageView3.setImageDrawable(getImage(this, fileName3));
        temperature3.setText(farenheitToCelcius(weatherResponse.getDaily().getData().get(2).getApparentTemperatureMax()));
        day3.setText(getDay(day + 3));

        String fileName4 = weatherResponse.getDaily().getData().get(3).getIcon().replace("-", "");
        imageView4.setImageDrawable(getImage(this, fileName4));
        temperature4.setText(farenheitToCelcius(weatherResponse.getDaily().getData().get(3).getApparentTemperatureMax()));
        day4.setText(getDay(day + 4));

        String fileName5 = weatherResponse.getDaily().getData().get(4).getIcon().replace("-", "");
        imageView5.setImageDrawable(getImage(this, fileName5));
        temperature5.setText(farenheitToCelcius(weatherResponse.getDaily().getData().get(4).getApparentTemperatureMax()));
        day5.setText(getDay(day + 5));

    }

    private String farenheitToCelcius(double farenheit) {
        return String.format("%.2f", (farenheit - 32) * 5 / 9) + " °C";
    }

    private Drawable getImage(Context c, String ImageName) {
        return c.getResources().getDrawable(c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()));
    }

    private String getDay(int day) {
        if (day>7) day = day - 7;
        switch (day) {
            case Calendar.SUNDAY:
                return "Niedziela";
            case Calendar.MONDAY:
                return "Poniedziałek";
            case Calendar.TUESDAY:
                return "Wtorek";
            case Calendar.WEDNESDAY:
                return "Środa";
            case Calendar.THURSDAY:
                return "Czwartek";
            case Calendar.FRIDAY:
                return "Piątek";
            case Calendar.SATURDAY:
                return "Sobota";
            default:
                return "Poniedziałek";
        }
    }
}