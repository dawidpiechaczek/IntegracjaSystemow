package com.example.dawid.visitwroclove.enums;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.application.MyApplication;

public enum Categories {
    FOREST(R.string.forest),

    WATER(R.string.river),

    CYCLE(R.string.cycle),

    COOK(R.string.cook),

    WALKING(R.string.foot),

    FAVOURITE(R.string.favourite);

    private final int constValue;

    Categories(int constValue) {
        this.constValue = constValue;
    }

    public String getValue() {
        return MyApplication.getMyApplicationContext().getString(constValue);
    }

}

