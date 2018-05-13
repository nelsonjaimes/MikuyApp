package com.restaurant.project.mikuyapp.address.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import io.fabric.sdk.android.Fabric;

public class AddressMapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, AddressMapsView {

    private ProgressBar pbLoad;
    private TextView tvTime;
    private ImageView ivBus;
    private TextView tvStartUbication;
    private TextView tvEndUbication;
    private AddressMapsPresenter addressMapsPresenter;
    /*MyLocation*/
    private SupportMapFragment mapFragment;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private Snackbar snackNetworkError, snackSettingsLocation;
    private LatLng latLng;
    private List<LatLng> latLngList;
    final long INTERVAL_REQUEST = 2 * 60 * 1000;
    final long FAST_INTERVAL_REQUEST = INTERVAL_REQUEST / 2;
    private final String KEY_LIST_LOCATION = "key_list_location";
    private String pathLocation;
    private boolean restoreState = true;
    /*Permission*/
    private final int PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
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
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_LIST_LOCATION)) {
            pathLocation = savedInstanceState.getString(KEY_LIST_LOCATION);
            if (pathLocation != null) {
                restoreState = false;
                latLngList = PolyUtil.decode(pathLocation);
                mapFragment.getMapAsync(this);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressMapsPresenter.attachView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions()) startSettingsLocation();
            else startRequestPermissions();
        } else startSettingsLocation();
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
        outState.putString(KEY_LIST_LOCATION, pathLocation);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    private void startKnownLocation() {
        mFusedLocationProviderClient.getLastLocation().
                addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            if (restoreState) addressMapsPresenter.initRequestDirectionApi(latLng);
                        }
                    }
                });

        startLocationUpdate();
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

    private void startSettingsLocation() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startKnownLocation();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int status = ((ApiException) e).getStatusCode();
                if (status == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    hideProgressBar();
                    showSnackSettingLocation();
                }
            }
        });
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
                    if (restoreState) addressMapsPresenter.initRequestDirectionApi(latLng);
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if (!locationAvailability.isLocationAvailable()) {
                    LogUtil.d("onLocationAvailability");
                    hideProgressBar();
                    showSnackSettingLocation();
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
        pathLocation = response.getPoints();
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
        restoreState = true;
    }

    private void showSnackNetworkError(@StringRes int idString) {
        snackNetworkError = Snackbar.make(findViewById(R.id.rlAddress), idString,
                Snackbar.LENGTH_INDEFINITE).setAction(R.string.reload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackNetworkError.dismiss();
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
                snackSettingsLocation.dismiss();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        snackSettingsLocation.setActionTextColor(Color.YELLOW);
        snackSettingsLocation.show();
    }

    /* Permission Android .6.0 */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startRequestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            Snackbar.make(findViewById(R.id.rlAddress), R.string.permissionRationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(AddressMapsActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    PERMISSIONS_REQUEST_CODE);
                        }
                    }).setActionTextColor(Color.MAGENTA).show();
        } else {
            ActivityCompat.requestPermissions(AddressMapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startKnownLocation();
            } else {
                startRequestPermissions();
            }
        }
    }
}
