package com.restaurant.project.mikuyapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.EntryMenuActivity;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.home.ui.HomeActivity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        LinearLayout llFooter = findViewById(R.id.llFooter);
        Animation alpaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        llFooter.setAnimation(alpaAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigation();
            }
        }, 2000);
    }

    private void navigation() {
        if (MikuyPreference.getUserSession() != null) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, EntryMenuActivity.class));
        }
        finish();
    }
}
