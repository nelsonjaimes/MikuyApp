package com.restaurant.project.mikuyapp.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.restaurant.project.mikuyapp.R;

public final class AnimationUtil {
    public static void showAnimationView(View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_up);
        view.clearAnimation();
        view.setVisibility(View.VISIBLE);
        view.setAnimation(animation);
    }

    public static void slideRightAnimationView(View view, Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_rigth);
        view.clearAnimation();
        view.setVisibility(View.VISIBLE);
        view.setAnimation(animation);
    }
}
