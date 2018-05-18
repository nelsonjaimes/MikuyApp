package com.restaurant.project.mikuyapp.profile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.domain.model.mikuy.response.SignInResponseEntity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;

/**
 * @author La cada de TacuTacu
 */
public class ProfileUserFragment extends Fragment {

    private TextView tvProfileName;
    private TextView tvProfileEmail;

    public static ProfileUserFragment getInstance() {
        return new ProfileUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail);
        tvProfileName = view.findViewById(R.id.tvProfileName);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SignInResponseEntity signInResponseEntity = MikuyPreference.getUserSession();
        tvProfileEmail.setText(signInResponseEntity.getEmail());
        tvProfileName.setText(signInResponseEntity.getName() + " " + signInResponseEntity.getLastname());
    }
}
