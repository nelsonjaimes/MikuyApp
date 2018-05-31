package com.restaurant.project.mikuyapp.signin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.EntryMenuActivity;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.dialog.DialogProgress;
import com.restaurant.project.mikuyapp.home.ui.HomeActivity;
import com.restaurant.project.mikuyapp.scan.ui.ScannerActivity;
import com.restaurant.project.mikuyapp.signin.SignInPresenter;
import com.restaurant.project.mikuyapp.signin.SignInPresenterImp;
import com.restaurant.project.mikuyapp.utils.Constant;
import com.restaurant.project.mikuyapp.utils.Operations;

import io.fabric.sdk.android.Fabric;

public class SignInActivity extends AppCompatActivity implements SignInView {

    private DialogProgress dialogProgress;
    private SignInPresenter signInPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_sign_in);
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        Button btnSignIn = findViewById(R.id.btnSignIn);
        signInPresenter = new SignInPresenterImp(this);
        initToolbar();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInPresenter.initSignIn(etEmail.getText().toString().trim(),
                        etPassword.getText().toString().trim());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        signInPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        signInPresenter.dettachView();
        super.onStop();
    }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, EntryMenuActivity.class));
            finish();
        } else if (item.getItemId() == R.id.itemSettings) {
            startActivity(new Intent(SignInActivity.this, ScannerActivity.class));
        }
        return true;
    }

    @Override
    public void showProgress() {
        dialogProgress = DialogProgress.getInstance();
        dialogProgress.show(getSupportFragmentManager(), Constant.DIALOG_PROGRESS);
    }

    @Override
    public void hideProgress() {
        if (dialogProgress == null) {
            dialogProgress = (DialogProgress) getSupportFragmentManager().
                    findFragmentByTag(Constant.DIALOG_PROGRESS);
        }
        if (dialogProgress != null) {
            dialogProgress.dismiss();
        }
    }

    @Override
    public void onSucessSignIn() {
        startActivity(new Intent(SignInActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void showSnackBar(String message) {
        Operations.getSnackBar(findViewById(R.id.rlSignIn), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EntryMenuActivity.class));
        finish();
    }


}
