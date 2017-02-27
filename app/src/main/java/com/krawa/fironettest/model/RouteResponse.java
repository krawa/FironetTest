package com.krawa.fironettest.model;

public class RouteResponse {

    private Route[] routes;
    private String status;

    public Route[] getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuccess(){
        return status.equals("OK");
    }
}
