package com.restaurant.project.mikuyapp.signup.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.EntryMenuActivity;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.dialog.DialogProgress;
import com.restaurant.project.mikuyapp.home.ui.HomeActivity;
import com.restaurant.project.mikuyapp.signup.SignUpPresenter;
import com.restaurant.project.mikuyapp.signup.SignUpPresenterImp;
import com.restaurant.project.mikuyapp.utils.Constant;

import io.fabric.sdk.android.Fabric;

public class SignUpActivity extends AppCompatActivity implements SignUpView, View.OnClickListener {

    private RadioGroup rgGender;
    private SignUpPresenter signUpPresenter;
    private EditText etEmail;
    private EditText etName;
    private EditText etLastName;
    private EditText etPassword;
    private String female;
    private char gender;
    private FragmentManager fragmentManager;
    private DialogProgress dialogProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_sign_up);
        rgGender = findViewById(R.id.rgGender);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        female = getString(R.string.female);
        fragmentManager = getSupportFragmentManager();
        signUpPresenter = new SignUpPresenterImp(this);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        initToolbar();
    }

    @Override
    protected void onResume() {
        signUpPresenter.attachView(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        signUpPresenter.dettachView();
        super.onStop();
    }
    public void onClickRadio(View v) {
        int id = rgGender.getCheckedRadioButtonId();
        RadioButton rbMale = rgGender.findViewById(R.id.rbMale);
        RadioButton rbFemale = rgGender.findViewById(R.id.rbFemale);
        rbMale.setTextColor(getResources().getColor(R.color.colorDisable));
        rbFemale.setTextColor(getResources().getColor(R.color.colorDisable));
        RadioButton radioButton = findViewById(id);
        radioButton.setTextColor(getResources().getColor(R.color.colorDark));
        if (radioButton.getText().toString().equalsIgnoreCase(female)) {
            gender = 'F';
        } else {
            gender = 'M';
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, EntryMenuActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (signUpPresenter != null) {
            signUpPresenter.initRegister(etName.getText().toString().trim(),
                    etLastName.getText().toString().trim(),
                    etEmail.getText().toString().trim(),
                    gender, etPassword.getText().toString().trim());
        }
    }

    @Override
    public void onSucessSignUp() {
        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        dialogProgress = DialogProgress.getInstance();
        dialogProgress.show(fragmentManager, Constant.DIALOG_PROGRESS);
    }

    @Override
    public void hideProgress() {
        if (dialogProgress == null) {
            dialogProgress = (DialogProgress) fragmentManager.
                    findFragmentByTag(Constant.DIALOG_PROGRESS);
        }
        if (dialogProgress != null) {
            dialogProgress.dismiss();
        }
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(findViewById(R.id.rlSignUp), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, EntryMenuActivity.class));
        finish();
    }
}
