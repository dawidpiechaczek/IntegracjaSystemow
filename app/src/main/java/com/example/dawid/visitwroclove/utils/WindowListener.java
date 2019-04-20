package com.example.dawid.visitwroclove.utils;

import android.content.Context;
import android.content.Intent;

import com.example.dawid.visitwroclove.view.activity.DetailsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

public class WindowListener implements GoogleMap.OnInfoWindowClickListener {
    private Context context;
    private Map<Marker, Integer> hashMap;

    public WindowListener(Context context, Map<Marker, Integer> hashMap) {
        this.context = context;
        this.hashMap = hashMap;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.EXTRA_POSIOTION, hashMap.get(marker));
        if (marker.getTag().equals("events")) {
            intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_EVENT);
        } else {
            intent.putExtra(Constants.EXTRA_ACTIVITY, Constants.ACTIVITY_VALUE_OBJECT);
        }
        context.startActivity(intent);
    }
}
