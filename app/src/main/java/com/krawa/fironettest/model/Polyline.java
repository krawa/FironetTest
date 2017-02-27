package com.krawa.fironettest.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import java.util.List;

public class Polyline {

    private String points;

    public String getPoints() {
        return points;
    }

    public List<LatLng> getPointList() {
        return PolyUtil.decode(points);
    }
}
