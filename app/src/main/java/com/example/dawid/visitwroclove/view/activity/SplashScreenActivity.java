package com.example.dawid.visitwroclove.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dawid.visitwroclove.DAO.implementation.AddressDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.EventDTO;
import com.example.dawid.visitwroclove.model.ObjectDTO;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.service.VisitWroAPI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashScreenActivity extends BaseActivity {
    @Inject ObjectDAOImpl repoObjects;
    @Inject EventDAOImpl repoEvents;
    @Inject RouteDAOImpl repoRoutes;
    @Inject AddressDAOImpl repoAddresses;
    private Context context = SplashScreenActivity.this;
    VisitWroAPI visitWroAPI;

    private Observer mObserver = new Observer() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Object value) {
            Intent intent = new Intent(context, MainPanelActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        getComponent().inject(this);
        visitWroAPI = VisitWroAPI.Factory.create(getApplicationContext());
        script();
    }

    @Override
    protected void onResume() {
        Observable.timer(3, TimeUnit.SECONDS).subscribe(mObserver);
        super.onResume();
    }

    private void script() {
        visitWroAPI.getObjects()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ObjectDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ObjectDTO> value) {
                        for (ObjectDTO objectDTO : value) {
                            repoObjects.add(objectDTO);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SplashScreen.onError","Objects: "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        visitWroAPI.getEvents()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<EventDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EventDTO> value) {
                        for (EventDTO eventDTO : value) {
                            repoEvents.add(eventDTO);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SplashScreen.onError","Objects: "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        visitWroAPI.getRoutes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<RouteDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<RouteDTO> value) {
                        for (RouteDTO routeDTO : value) {
                            repoRoutes.add(routeDTO);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SplashScreen.onError","Objects: "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
