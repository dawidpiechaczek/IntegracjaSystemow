package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;

import com.example.dawid.visitwroclove.R;


public class MainPanelActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        getComponent().inject(this);
    }
}


