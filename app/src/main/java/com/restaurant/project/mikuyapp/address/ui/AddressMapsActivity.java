package com.restaurant.project.mikuyapp.address.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.ui.IconGenerator;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.address.AddressMapsPresenter;
import com.restaurant.project.mikuyapp.address.AddressMapsPresenterImp;
import com.restaurant.project.mikuyapp.domain.model.directions.DirectionsApiResponse;
import com.restaurant.project.mikuyapp.utils.AnimationUtil;
import com.restaurant.project.mikuyapp.utils.LogUtil;
import com.restaurant.project.mikuyapp.utils.Operations;

import java.util.List;

public class AddressMapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, AddressMapsView {

    final long INTERVAL_REQUEST = 60 * 1000;
    final long FAST_INTERVAL_REQUEST = INTERVAL_REQUEST / 2;
    private final String KEY_LOCATION = "location_update";
    private SupportMapFragment mapFragment;
    private ProgressBar pbLoad;
    private TextView tvTime;
    private ImageView ivBus;
    private TextView tvStartUbication;
    private TextView tvEndUbication;
    private AddressMapsPresenter addressMapsPresenter;
    private List<LatLng> latLngList;
    /*MyLocation*/
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private Snackbar snackNetworkError;
    private Snackbar snackSettingsLocation;
    private LatLng latLng;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_maps);
        pbLoad = findViewById(R.id.pbLoad);
        tvStartUbication = findViewById(R.id.tvStartUbication);
        tvEndUbication = findViewById(R.id.tvEndUbication);
        ivBus = findViewById(R.id.ivBus);
        tvTime = findViewById(R.id.tvTime);
        initToolbar();
        addressMapsPresenter = new AddressMapsPresenterImp();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        createLocationRequest();
        createCallbackRequest();
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_LOCATION)) {
            latLng = savedInstanceState.getParcelable(KEY_LOCATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressMapsPresenter.attachView(this);
        startLocationUpdate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        addressMapsPresenter.onDestroyView();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_LOCATION, latLng);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(R.string.address);
        }
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL_REQUEST);
        mLocationRequest.setFastestInterval(FAST_INTERVAL_REQUEST);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createCallbackRequest() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    addressMapsPresenter.initRequestDirectionApi(latLng);
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (!locationAvailability.isLocationAvailable()) {
                    LogUtil.d("onLocationAvailability");
                    showSnackSettingLocation();
                    hideProgressBar();
                } else {
                    showProgressBar();
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdate() {
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                mLocationCallback, Looper.myLooper());
    }

    private void stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (latLngList != null) {
            googleMap.clear();
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(getResources().getColor(R.color.colorPrimary));
            polylineOptions.addAll(latLngList);
            googleMap.addPolyline(polylineOptions);
            addMarker(latLngList.get(0), googleMap, getString(R.string.titleStart),
                    IconGenerator.STYLE_WHITE);
            addMarker(latLngList.get(latLngList.size() - 1), googleMap,
                    getString(R.string.titleEnd), IconGenerator.STYLE_GREEN);
            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(latLngList.get(0))
                    .tilt(45.0f)
                    .zoom(13.5f)
                    .build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onSuccessDirectionApi(DirectionsApiResponse response) {
        tvEndUbication.setText(Operations.getHtml(String.format(getString(R.string.endUbication),
                response.getEndUbication())));
        tvStartUbication.setText(Operations.getHtml(String.format(getString(R.string.startUbication),
                response.getStartUbication())));
        tvTime.setText(response.getTime());
        AnimationUtil.showAnimationView(tvTime, this);
        AnimationUtil.showAnimationView(ivBus, this);
        latLngList = PolyUtil.decode(response.getPoints());
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onErrorService() {
        showSnackNetworkError(R.string.errorApiDireccions);
    }

    @Override
    public void onFailure() {
        showSnackNetworkError(R.string.errorNetwoork);
    }

    @Override
    public void showProgressBar() {
        AnimationUtil.showAnimationView(pbLoad, this);
        if (snackNetworkError != null) snackNetworkError.dismiss();
        if (snackSettingsLocation != null) snackSettingsLocation.dismiss();
    }

    @Override
    public void hideProgressBar() {
        pbLoad.setVisibility(View.GONE);
    }

    private void addMarker(LatLng latLng, GoogleMap googleMap, String title, int style) {
        IconGenerator iconGenerator = new IconGenerator(AddressMapsActivity.this);
        iconGenerator.setStyle(style);
        Bitmap bitmap = iconGenerator.makeIcon(title);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(icon);
        googleMap.addMarker(markerOptions);
    }

    private void showSnackNetworkError(@StringRes int idString) {
        snackNetworkError = Snackbar.make(findViewById(R.id.rlAddress), idString,
                Snackbar.LENGTH_INDEFINITE).setAction(R.string.reload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressMapsPresenter.initRequestDirectionApi(latLng);
            }
        });
        snackNetworkError.setActionTextColor(Color.YELLOW);
        snackNetworkError.show();
    }

    private void showSnackSettingLocation() {
        snackSettingsLocation = Snackbar.make(findViewById(R.id.rlAddress), R.string.errorSettingsLocation,
                Snackbar.LENGTH_INDEFINITE).setAction(R.string.settings, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        snackSettingsLocation.setActionTextColor(Color.MAGENTA);
        snackSettingsLocation.show();
    }
}
