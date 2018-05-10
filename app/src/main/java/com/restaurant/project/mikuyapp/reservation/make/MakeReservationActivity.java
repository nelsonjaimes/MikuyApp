package com.restaurant.project.mikuyapp.reservation.make;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.utils.Operations;

public class MakeReservationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reservation);
        TextView tvReservationNumber = findViewById(R.id.tvReservationNumber);
        TextView tvReservationDate = findViewById(R.id.tvReservationDate);
        Button btnMakePayment = findViewById(R.id.btnMakePayment);
        btnMakePayment.setOnClickListener(this);
        initToolbar();
        tvReservationNumber.setText(Operations.getHtml(String.format(getString(R.string.reservationNumber), "001")));
        tvReservationDate.setText(Operations.getHtml(String.format(getString(R.string.reservationDate), "9/05/2018 11:18am")));
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
