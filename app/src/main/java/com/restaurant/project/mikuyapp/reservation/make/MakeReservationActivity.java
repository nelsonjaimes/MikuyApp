package com.restaurant.project.mikuyapp.reservation.make;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.menutoday.ui.MenuTodayFragment;
import com.restaurant.project.mikuyapp.utils.Operations;

import io.fabric.sdk.android.Fabric;

public class MakeReservationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_make_reservation);
        TextView tvReservationNumber = findViewById(R.id.tvReservationNumber);
        TextView tvReservationDate = findViewById(R.id.tvReservationDate);
        TextView tvAmount = findViewById(R.id.tvAmount);
        Button btnMakePayment = findViewById(R.id.btnMakePayment);
        btnMakePayment.setOnClickListener(this);
        initToolbar();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String codeReservation = bundle.getString(MenuTodayFragment.EXTRA_CODE_RESERVE);
            String amount = bundle.getString(MenuTodayFragment.EXTRA_AMOUNT);
            String date = bundle.getString(MenuTodayFragment.EXTRA_DATE_RESERVE);

            tvReservationDate.setText(Operations.getHtml(String.format(getString(R.string.reservationDate), date)));
            tvReservationNumber.setText(codeReservation);
            tvAmount.setText("Total: s/." + amount);
        }

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
