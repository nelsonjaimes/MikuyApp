package com.restaurant.project.mikuyapp.signup.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.signup.SignUpPresenter;
import com.restaurant.project.mikuyapp.signup.SignUpPresenterImp;

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
        signUpPresenter = new SignUpPresenterImp();
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        initToolbar();
    }

    @Override
    protected void onResume() {
        signUpPresenter.attachView(this);
        super.onResume();
    }

    public void onClickRadio(View v) {
        int id = rgGender.getCheckedRadioButtonId();
        RadioButton rbMale = rgGender.findViewById(R.id.rbMale);
        RadioButton rbFemale = rgGender.findViewById(R.id.rbFemale);
        rbMale.setTextColor(getResources().getColor(R.color.colorDisable));
        rbFemale.setTextColor(getResources().getColor(R.color.colorDisable));
        RadioButton radioButton = findViewById(id);
        radioButton.setTextColor(getResources().getColor(R.color.colorDark));
        if (radioButton.getText().toString().equalsIgnoreCase(female)) gender = 'F';
        else gender = 'M';
        Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
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
            onBackPressed();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (signUpPresenter != null) {
            signUpPresenter.initRegister(etName.getText().toString(),
                    etLastName.getText().toString(),
                    etEmail.getText().toString(),
                    gender, etPassword.getText().toString());
        }
    }

    @Override
    public void onSucessSignUp() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
