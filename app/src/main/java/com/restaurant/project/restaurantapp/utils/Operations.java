package com.restaurant.project.restaurantapp.utils;

import android.os.Build;
import android.text.Html;

final public class Operations {
    @SuppressWarnings("deprecation")
    public static CharSequence getHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml(source);
    }
}
