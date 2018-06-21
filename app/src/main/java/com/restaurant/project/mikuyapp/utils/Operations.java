package com.restaurant.project.mikuyapp.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

final public class Operations {
    @SuppressWarnings("deprecation")
    public static CharSequence getHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        }
        return Html.fromHtml(source);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        }
        return false;
    }

    public static Snackbar getSnackBar(Context context, View view, String message, int duration) {
        Typeface fontRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/ralewayregular.ttf");
        Typeface fontBold = Typeface.createFromAsset(context.getAssets(),
                "fonts/ralewaybold.ttf");
        Snackbar snackbar = Snackbar.make(view, message, duration);
        TextView tvAction = snackbar.getView().
                findViewById(android.support.design.R.id.snackbar_action);
        tvAction.setTypeface(fontBold);
        TextView tvMessage = snackbar.getView().
                findViewById(android.support.design.R.id.snackbar_text);
        tvMessage.setTypeface(fontRegular);
        return snackbar;
    }
}
