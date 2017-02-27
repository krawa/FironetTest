package com.krawa.fironettest.network;

import com.krawa.fironettest.model.Point;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestAPI {

    String BASE_URL = "http://www.mocky.io/v2/";

    @GET("58aaa8f9100000730e4b62b7")
    Call<Point[]> getPoints();

}
