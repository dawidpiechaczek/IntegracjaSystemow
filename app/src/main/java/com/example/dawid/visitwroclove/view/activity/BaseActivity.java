package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dawid.visitwroclove.application.MyApplication;
import com.example.dawid.visitwroclove.dagger.NonConfigurationComponent;


public class BaseActivity extends AppCompatActivity {
    private NonConfigurationComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = retriveInjectorOrCreateNew();
    }

    private NonConfigurationComponent retriveInjectorOrCreateNew() {
        Object lastNonConfInstance = getLastNonConfigurationInstance();
        if(lastNonConfInstance == null){
            MyApplication application = (MyApplication) getApplicationContext();
            return application.getAppComponent().nonConfigurationComponent();
        }else{
            return (NonConfigurationComponent) lastNonConfInstance;
        }
    }

    public NonConfigurationComponent getComponent() {
        return component;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
