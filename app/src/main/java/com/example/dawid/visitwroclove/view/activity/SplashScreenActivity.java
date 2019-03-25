package com.example.dawid.visitwroclove.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dawid.visitwroclove.DAO.implementation.AddressDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.RouteDAOImpl;
import com.example.dawid.visitwroclove.R;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SplashScreenActivity extends BaseActivity {
    @Inject ObjectDAOImpl repoObjects;
    @Inject EventDAOImpl repoEvents;
    @Inject RouteDAOImpl repoRoutes;
    @Inject AddressDAOImpl repoAddresses;
    private Context context = SplashScreenActivity.this;

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
        script();
    }

    @Override
    protected void onResume() {
        Observable.timer(3, TimeUnit.SECONDS).subscribe(mObserver);
        super.onResume();
    }

    private void script() {
       //download data from API
    }
}
