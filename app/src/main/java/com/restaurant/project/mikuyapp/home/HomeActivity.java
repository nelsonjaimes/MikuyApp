package com.restaurant.project.mikuyapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.address.ui.AddressMapsActivity;
import com.restaurant.project.mikuyapp.contacts.ContactsFragment;
import com.restaurant.project.mikuyapp.home.sidebar.adapter.SideBarAdapter;
import com.restaurant.project.mikuyapp.home.sidebar.ui.SideBarListener;
import com.restaurant.project.mikuyapp.letter.LetterOfDishesFragment;
import com.restaurant.project.mikuyapp.menutoday.ui.MenuTodayFragment;
import com.restaurant.project.mikuyapp.profile.ProfileUserFragment;
import com.restaurant.project.mikuyapp.reservation.MyReservationsFragment;
import com.restaurant.project.mikuyapp.utils.Constant;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity implements SideBarListener, View.OnClickListener {

    private SlidingPaneLayout splHome;
    private RecyclerView rvSideBar;
    private FragmentManager mFragmentManager;
    private SideBarAdapter sideBarAdapter;
    private HandlerSlidingPanel handlerSlidingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        splHome = findViewById(R.id.splHome);
        rvSideBar = findViewById(R.id.rvSideBar);
        mFragmentManager = getSupportFragmentManager();
        handlerSlidingPanel = new HandlerSlidingPanel(splHome);
        Button btnAddress = findViewById(R.id.btnAddress);
        btnAddress.setOnClickListener(this);
        initSlidingPanel();
        initSideBar();
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void initSideBar() {
        sideBarAdapter = new SideBarAdapter(this);
        sideBarAdapter.setSideBarListener(this);
        rvSideBar.setAdapter(sideBarAdapter);
    }

    private void initSlidingPanel() {
        splHome.setParallaxDistance(100);
        splHome.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        splHome.setCoveredFadeColor(getResources().getColor(android.R.color.black));
    }

    @Override
    protected void onResume() {
        super.onResume();
        sideBarAdapter.setSideBarListener(this);
    }

    @Override
    public void onClick(View v) {
        if (splHome.isOpen()) {
            startActivity(new Intent(this, AddressMapsActivity.class));
            throw new RuntimeException("Prueba de exception...");
        }
    }

    @Override
    public void itemSelectSideBar(int pos) {
        if (splHome.isOpen()) {
            replaceFragment(pos);
            handlerSlidingPanel.close();
        }
    }

    private void replaceFragment(int position) {
        Fragment fragment = null;
        String title = null;
        switch (position) {
            case Constant.ITEM_MENU_TODAY:
                fragment = MenuTodayFragment.getInstance();
                title = getString(R.string.menu);
                break;
            case Constant.ITEM_LETTER_DISHES:
                fragment = LetterOfDishesFragment.getInstance();
                title = getString(R.string.foodLetter);
                break;
            case Constant.ITEM_RESERVATIONS:
                fragment = MyReservationsFragment.getInstance();
                title = getString(R.string.myReservations);
                break;
            case Constant.ITEM_CONTACTS:
                fragment = ContactsFragment.getInstance();
                title = getString(R.string.contacts);
                break;
            case Constant.ITEM_PROFILE_USER:
                fragment = ProfileUserFragment.getInstance();
                title = getString(R.string.userProfile);
                break;
        }
        if (fragment != null && mFragmentManager != null) {
            mFragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            sideBarAdapter.selectItem(position);
            setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            toggleDrawerLayout();
        }
        return true;
    }

    private void toggleDrawerLayout() {
        if (splHome.isOpen()) {
            splHome.closePane();
        } else {
            splHome.openPane();
        }
    }

    @Override
    public void onBackPressed() {
        if (splHome.isOpen()) {
            splHome.closePane();
        } else {
            super.onBackPressed();
        }
    }

    private static class HandlerSlidingPanel extends Handler implements Runnable {
        private SlidingPaneLayout slidingPaneLayout;

        HandlerSlidingPanel(SlidingPaneLayout slidingPaneLayout) {
            super();
            this.slidingPaneLayout = slidingPaneLayout;
        }

        void close() {
            postDelayed(this, 200);
        }

        @Override
        public void run() {
            slidingPaneLayout.closePane();
        }
    }
}
