package com.krawa.fironettest.model;

import com.google.gson.annotations.SerializedName;

public class Route {

    private Leg[] legs;

    @SerializedName("overview_polyline")
    private Polyline polyline;

    public Leg[] getLegs() {
        return legs;
    }

    public Polyline getPolyline() {
        return polyline;
    }
}
