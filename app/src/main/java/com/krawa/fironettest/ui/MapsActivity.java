package com.krawa.fironettest.ui;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.PolylineOptions;
import com.krawa.fironettest.R;
import com.krawa.fironettest.presentation.presenter.MapsPresenter;
import com.krawa.fironettest.presentation.view.MapsView;

public class MapsActivity extends MvpAppCompatActivity implements MapsView, OnMapReadyCallback {

    private GoogleMap mMap;
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    @InjectPresenter
    MapsPresenter presenter;
    private TextView tvMyDist;
    private TextView tvGoogleDist;
    private ProgressBar pbMy;
    private ProgressBar pbGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        tvMyDist = (TextView) findViewById(R.id.tvMyDist);
        tvGoogleDist = (TextView) findViewById(R.id.tvGoogleDist);

        pbMy = (ProgressBar) findViewById(R.id.pbMy);
        pbGoogle = (ProgressBar) findViewById(R.id.pbGoogle);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        presenter.onMapReady();
    }

    @Override
    public void addPolyline(PolylineOptions polylineOptions) {
        if(mMap != null) mMap.addPolyline(polylineOptions);
    }

    @Override
    public void moveCamera(CameraUpdate cameraUpdate) {
        if(mMap != null) mMap.moveCamera(cameraUpdate);
    }

    @Override
    public void showInfo(double dist1, double dist2) {
        pbMy.setVisibility(dist1 == 0 ? View.VISIBLE : View.GONE);
        pbGoogle.setVisibility(dist2 == 0 ? View.VISIBLE : View.GONE);
        tvMyDist.setVisibility(dist1 != 0 ? View.VISIBLE : View.GONE);
        tvGoogleDist.setVisibility(dist2 != 0 ? View.VISIBLE : View.GONE);
        tvMyDist.setText(getString(R.string.dist_km, dist1));
        tvGoogleDist.setText(getString(R.string.dist_km, dist2));
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void showMessage(int res) {
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show();
    }
}
