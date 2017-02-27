package com.krawa.fironettest.presentation.presenter;

import android.graphics.Color;
import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.krawa.fironettest.App;
import com.krawa.fironettest.R;
import com.krawa.fironettest.model.Leg;
import com.krawa.fironettest.model.Path;
import com.krawa.fironettest.model.Point;
import com.krawa.fironettest.model.RouteResponse;
import com.krawa.fironettest.network.MapsAPI;
import com.krawa.fironettest.network.RestAPI;
import com.krawa.fironettest.presentation.view.MapsView;
import com.krawa.fironettest.utils.PathFinder;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class MapsPresenter extends MvpPresenter<MapsView> {

    @Inject
    RestAPI restAPI;

    @Inject
    MapsAPI mapsAPI;

    private double myRouteDistance;

    public MapsPresenter(){
        App.getAppComponent().inject(this);
    }

    public void onMapReady() {
        restAPI.getPoints().enqueue(new Callback<Point[]>() {
            @Override
            public void onResponse(Call<Point[]> call, Response<Point[]> response) {
                if(response.isSuccessful()) {
                    findPath(response.body());
                } else {
                    getViewState().showMessage(R.string.error);
                }
            }

            @Override
            public void onFailure(Call<Point[]> call, Throwable t) {
                getViewState().showMessage(R.string.error);
            }
        });
    }

    private void findPath(final Point[] points) {
        new AsyncTask<Void, Void, Path>(){

            @Override
            protected Path doInBackground(Void... voids) {
                return new PathFinder().findPath(points);
            }

            @Override
            protected void onPostExecute(Path result) {
                PolylineOptions po = new PolylineOptions();
                LatLngBounds.Builder llbb = new LatLngBounds.Builder();
                for (Point point : result.getPoints()) {
                    LatLng latLng = new LatLng(point.getLat(), point.getLng());
                    po.add(latLng);
                    llbb.include(latLng);
                }
                po.color(Color.parseColor("#ffcc0000"));
                getViewState().addPolyline(po);

                myRouteDistance = result.getDistance();

                getViewState().showInfo(myRouteDistance/1000, 0);

                getViewState().moveCamera(CameraUpdateFactory.newLatLngBounds(llbb.build(), 50));

                getGoogleRoute(result.getPoints());
            }

        }.execute();
    }

    private void getGoogleRoute(Point[] points) {
        String origin = points[0].getLat() + "," + points[0].getLng();
        String dest = points[points.length - 1].getLat() + "," + points[points.length - 1].getLng();

        String waypoints = null;
        if(points.length > 2){
            waypoints = "optimize:true|";
            for (int i = 1; i < points.length - 1; i++) {
                waypoints += points[i].getLat() + "," + points[i].getLng();
                if(i < points.length - 2) waypoints += "|";
            }
        }
        mapsAPI.getRoute(origin, dest, waypoints, null).enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Call<RouteResponse> call, Response<RouteResponse> response) {
                if(response.isSuccessful() && response.body().isSuccess()){
                    List<LatLng> pointList = response.body().getRoutes()[0].getPolyline().getPointList();
                    PolylineOptions po = new PolylineOptions();
                    po.addAll(pointList);
                    po.color(Color.parseColor("#ff669900"));
                    getViewState().addPolyline(po);

                    double googleRouteDistance = 0;

                    for (Leg leg : response.body().getRoutes()[0].getLegs()) {
                        googleRouteDistance += leg.getDistance().getValue();
                    }

                    getViewState().showInfo(myRouteDistance/1000, googleRouteDistance/1000);
                } else {
                    getViewState().showMessage(R.string.error);
                }
            }

            @Override
            public void onFailure(Call<RouteResponse> call, Throwable t) {
                getViewState().showMessage(R.string.error);
            }
        });
    }


}
