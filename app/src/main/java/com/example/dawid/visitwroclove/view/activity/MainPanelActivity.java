package com.example.dawid.visitwroclove.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.enums.Categories;
import com.example.dawid.visitwroclove.model.AddressDTO;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.LoggedUserDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.PaymentData;
import com.example.dawid.visitwroclove.model.PointDTO;
import com.example.dawid.visitwroclove.model.RegistrationDTO;
import com.example.dawid.visitwroclove.model.Response;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.model.ShopData;
import com.example.dawid.visitwroclove.model.WeatherResponse;
import com.example.dawid.visitwroclove.service.VisitWroAPI;
import com.example.dawid.visitwroclove.service.WeatherAPI;
import com.example.dawid.visitwroclove.utils.Constants;
import com.example.dawid.visitwroclove.utils.FontManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.mobiltek.paymentsmobile.dotpay.Configuration;
import pl.mobiltek.paymentsmobile.dotpay.events.PaymentEndedEventArgs;
import pl.mobiltek.paymentsmobile.dotpay.events.PaymentManagerCallback;
import pl.mobiltek.paymentsmobile.dotpay.managers.PaymentManager;
import pl.mobiltek.paymentsmobile.dotpay.model.PaymentInformation;

import static com.example.dawid.visitwroclove.utils.Constants.BUS_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.EXTRA_WEB_VIEW;
import static com.example.dawid.visitwroclove.utils.Constants.WEATHER_WEB_VIEW;


public class MainPanelActivity extends BaseActivity {


    private static final String MY_PREFS_NAME = "token";
    private static final String PREMIUM = "premium";
    public static final String USER_ACCESS_TOKEN = "Shared.User.AccesToken";
    public static final String USER_ID = "Shared.User.UserID";
    public static final String USER_EMAIL = "Shared.User.UserEmail";
    public static final String USER_PASSWORD = "Shared.User.UserPassword";

    private String mLog = MainPanelActivity.class.getName();
    @BindView(R.id.tv_map)
    TextView mpa_tv_map;
    @BindView(R.id.tv_events)
    TextView mpa_tv_events;
    @BindView(R.id.tv_places)
    TextView mpa_tv_places;
    @BindView(R.id.tv_tracks)
    TextView mpa_tv_tracks;
    @BindView(R.id.tv_bus)
    TextView mpa_tv_buses;
    @BindView(R.id.tv_weather)
    TextView mpa_tv_weather;
    @Inject
    public ObjectDAOImpl mRepo;
    @Inject
    public EventDAOImpl mRepoEvent;
    @Inject
    public RouteDAOImpl mRepoRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        getComponent().inject(this);
        ButterKnife.bind(this);
        Log.d(mLog, "MainPanelActivity.onCreate()");
        setPermissions();
        setIcons();
        setupSDK();
        // scripts();
    }

    private void setIcons() {
        mpa_tv_places.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_map.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_events.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_tracks.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_weather.setTypeface(FontManager.getIcons(MainPanelActivity.this));
        mpa_tv_buses.setTypeface(FontManager.getIcons(MainPanelActivity.this));
    }

    private void setPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(mLog, "MainPanelActivity.onResume()");
    }

    @OnClick(R.id.ll_tracks)
    public void showRoutesActivity() {
        Intent intent = new Intent(getApplicationContext(), RoutesListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_places)
    public void showPlacesActivity() {
        Intent intent = new Intent(getApplicationContext(), PlacesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_events)
    public void showEventsActivity() {
        Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_weather)
    public void showWeatherActivity() {
        WeatherAPI weatherAPI = WeatherAPI.Factory.create(this);
        weatherAPI.getWeather()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeatherResponse value) {
                        Gson gson = new Gson();
                        Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
                        intent.putExtra("weather", gson.toJson(value));
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.toString();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.ll_map)
    public void showMapActivity() {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        intent.putExtra("own_route_mode", true); //run own route creator mode
        startActivity(intent);
    }

    @OnClick(R.id.ll_favs)
    public void showFavsActivity() {
        Intent intent = new Intent(getApplicationContext(), EventsActivity.class);
        intent.putExtra("favourite", true);
        startActivity(intent);
    }

    @OnClick(R.id.ll_bus)
    public void showScheduleActivity() {
        Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_my_trips)
    public void showMyTripsActivity() {
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        boolean isPremium = prefs.getBoolean(PREMIUM, false);
        if (isPremium) {
            startMyTripsActivity();
        } else {
            buildDialog();
        }

    }

    private void startMyTripsActivity() {
        Intent intent = new Intent(getApplicationContext(), RoutesListActivity.class);
        intent.putExtra(Constants.EXTRA_POSIOTION, Categories.FAVOURITE.getValue());
        startActivity(intent);
    }

    private void buildDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Brak konta Premium")
                .setMessage("Jeżeli chcesz skorzystać z funkcjonalności moje trasy musisz wykupić konto premium")

                .setPositiveButton("Kup", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startPayment();
                    }
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    private void startPayment() {
        PaymentManager.getInstance().initialize(this, getPaymentInformation());
    }

    @NonNull
    private PaymentInformation getPaymentInformation() {
        return new PaymentInformation(ShopData.getMerchantId(), ShopData.getProductPrice(), ShopData.getDescription(), ShopData.getCurrency());
    }


    private void setupSDK() {
        PaymentManager.getInstance().setPaymentManagerCallback(paymentManagerCallback);
        PaymentManager.getInstance().setApplicationVersion(Configuration.TEST_VERSION);
    }

    private PaymentManagerCallback paymentManagerCallback = new PaymentManagerCallback() {
        @Override
        public void onPaymentSuccess(PaymentEndedEventArgs paymentEndedEventArgs) {
            if (!paymentEndedEventArgs.mPaymentResult.getStatus().equals("processing") || !paymentEndedEventArgs.mPaymentResult.getStatus().equals("new")) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(PREMIUM, true);
                editor.apply();
                SharedPreferences prefs = getSharedPreferences("token", MODE_PRIVATE);
                String token = prefs.getString(USER_ACCESS_TOKEN, "");
                int id = prefs.getInt(USER_ID, 23);
                VisitWroAPI visitWroAPI = VisitWroAPI.Factory.createLogin(token);
                visitWroAPI.sendPaymentData(new PaymentData(id, paymentEndedEventArgs.getPaymentResult().getNumber()))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Response>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Response value) {
                                startMyTripsActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                startMyTripsActivity();
                            }

                            @Override
                            public void onComplete() {
                            }
                        });

            } else {
                Toast.makeText(MainPanelActivity.this, "Płatność się nie powiodła", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onPaymentFailure(PaymentEndedEventArgs paymentEndedEventArgs) {
            Toast.makeText(MainPanelActivity.this, "Płatność się nie powiodła", Toast.LENGTH_LONG).show();
        }
    };
}


