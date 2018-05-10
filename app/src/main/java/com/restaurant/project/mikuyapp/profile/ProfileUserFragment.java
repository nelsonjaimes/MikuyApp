package com.restaurant.project.mikuyapp.profile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.project.mikuyapp.R;

/**
 * @author La cada de TacuTacu
 */
public class ProfileUserFragment extends Fragment {


    public static ProfileUserFragment getInstance() {
        return new ProfileUserFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_user, container, false);
    }

}
