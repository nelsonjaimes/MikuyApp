package com.restaurant.project.mikuyapp.scan.manually;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.restaurant.project.mikuyapp.MikuyApplication;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;

public class ChangeIpMannuallyDialog extends DialogFragment {

    private EditText edtIp;
    private Button btnSave;
    private ImageView ivExit;

    public static ChangeIpMannuallyDialog getInstance() {
        return new ChangeIpMannuallyDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setCancelable(false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return inflater.inflate(R.layout.dialog_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = view.findViewById(R.id.btnSave);
        edtIp = view.findViewById(R.id.edtIp);
        ivExit = view.findViewById(R.id.ivExit);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edtIp.setText(MikuyPreference.getIpAddressServer());
        edtIp.requestFocus();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newIp = edtIp.getText().toString();
                if (newIp.length() > 6) {
                    MikuyPreference.saveIpAddressServer(newIp);
                    Toast.makeText(MikuyApplication.contextApp,
                            getString(R.string.saveSuccessIpAddress, newIp),
                            Toast.LENGTH_LONG).show();
                    dismiss();
                }
            }
        });

        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        window.setLayout(getResources().getDimensionPixelSize(R.dimen.widthChangeIpAddress),
                getResources().getDimensionPixelSize(R.dimen.heigthChageIpAddress));
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return dialog;
    }

    @Override
    public int getTheme() {
        return R.style.AppDialogThemeScan;
    }


}
