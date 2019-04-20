package com.example.dawid.visitwroclove.view.activity;

import com.akexorcist.googledirection.model.Direction;
import com.example.dawid.visitwroclove.model.BaseDTO;

public interface MapView {
    void addMarker(BaseDTO baseDTO, String tag);
    void setCameraPosition(int position);
    void positiveRouteCallback(Direction direction);
    void negativeRouteCallback();
    void clearPolylines();
    void setButtonVisibility(boolean visible);
    void setButtonNavigateVisibility(boolean visible);
    void setMarkerAdded(int markerId);
    void setMarkerRemoved(int markerId);
}
