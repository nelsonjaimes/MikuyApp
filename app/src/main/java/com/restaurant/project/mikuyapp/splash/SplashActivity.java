package com.restaurant.project.mikuyapp.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.EntryMenuActivity;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.home.ui.HomeActivity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.Constant;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private TextView tvFooter;
    private boolean state = true;
    private ObjectAnimator ivTranslate, ivAlpha, tvTranslate, tvAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        ivLogo = findViewById(R.id.ivLogo);
        tvFooter = findViewById(R.id.tvFooter);
        initAnimation();
    }

    private void navigation() {
        if (MikuyPreference.getUserSession() != null) {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, EntryMenuActivity.class));
        }
        finish();
    }

    private void initAnimation() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int heightLogo
                = getResources().getDimensionPixelSize(R.dimen.heightImgLogo);
        int heightTxtFooter = getResources().getDimensionPixelSize(R.dimen.heightTxtFooter);
        int translate = (point.y - (heightLogo + heightTxtFooter)) / 2;
        translate -= 20;
        int duration = 1500;
        ivTranslate = getObjectTranslateY(ivLogo, translate);
        ivAlpha = getAlphaAnimation(ivLogo);
        tvTranslate = getObjectTranslateY(tvFooter, -translate);
        tvAlpha = getAlphaAnimation(tvFooter);
        tvAlpha.setDuration(duration);
        ivAlpha.setDuration(duration);
        tvTranslate.setDuration(duration);
        ivTranslate.setDuration(duration);
        ivTranslate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (state) {
                    navigation();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ivLogo.setVisibility(View.VISIBLE);
                tvFooter.setVisibility(View.VISIBLE);
            }
        });
        ivTranslate.start();
        tvAlpha.start();
        ivAlpha.start();
        tvTranslate.start();
    }

    @Override
    protected void onStop() {
        cancelAnimation();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!state) {
            navigation();
        }
    }

    private void cancelAnimation() {
        state = false;
        ivTranslate.cancel();
        tvTranslate.cancel();
        tvAlpha.cancel();
        ivAlpha.cancel();
    }


    private ObjectAnimator getObjectTranslateY(View view, int translate) {
        if (view == null) return null;
        return ObjectAnimator.ofFloat(view, Constant.ANIMATION_TRANSLATE_Y, translate);
    }

    private ObjectAnimator getAlphaAnimation(View view) {
        if (view == null) return null;
        return ObjectAnimator.ofFloat(view, Constant.ANIMATION_ALPHA, 0f, 1f);
    }
}
