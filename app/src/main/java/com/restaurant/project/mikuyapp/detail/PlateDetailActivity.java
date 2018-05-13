package com.restaurant.project.mikuyapp.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.R;

import io.fabric.sdk.android.Fabric;

public class PlateDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_plate_detail);
    }
}
