package com.krawa.fironettest.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.PolylineOptions;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MapsView extends MvpView {

    void addPolyline(PolylineOptions polylineOptions);
    void moveCamera(CameraUpdate cameraUpdate);
    void showInfo(double dist1, double dist2);
    void showMessage(int res);
}
