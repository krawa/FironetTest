package com.krawa.fironettest.network;

import com.krawa.fironettest.model.RouteResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsAPI {

    String BASE_URL = "https://maps.googleapis.com/maps/api/";

    @GET("directions/json")
    Call<RouteResponse> getRoute(@Query("origin") String origin,
                                 @Query("destination") String dest,
                                 @Query("waypoints") String waypoints,
                                 @Query("key") String key);

}
