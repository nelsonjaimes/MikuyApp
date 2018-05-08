package com.restaurant.project.restaurantapp.home;

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

import com.restaurant.project.restaurantapp.R;
import com.restaurant.project.restaurantapp.address.AddressLocationFragment;
import com.restaurant.project.restaurantapp.contacts.ContactsFragment;
import com.restaurant.project.restaurantapp.letter.LetterOfDishesFragment;
import com.restaurant.project.restaurantapp.menutoday.ui.MenuTodayFragment;
import com.restaurant.project.restaurantapp.profile.ProfileUserFragment;
import com.restaurant.project.restaurantapp.reservation.MyReservationsFragment;
import com.restaurant.project.restaurantapp.sidebar.adapter.SideBarAdapter;
import com.restaurant.project.restaurantapp.sidebar.ui.SideBarListener;
import com.restaurant.project.restaurantapp.utils.Constant;

public class HomeActivity extends AppCompatActivity implements SideBarListener {

    private SlidingPaneLayout splHome;
    private RecyclerView rvSideBar;
    private FragmentManager mFragmentManager;
    private SideBarAdapter sideBarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        splHome = findViewById(R.id.splHome);
        rvSideBar = findViewById(R.id.rvSideBar);
        mFragmentManager = getSupportFragmentManager();
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
    public void itemSelectSideBar(int pos) {
        if (splHome.isOpen()){
            replaceFragment(pos);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    splHome.closePane();
                }
            }, 200);
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
            case Constant.ITEM_ADDRESS:
                fragment = AddressLocationFragment.getInstance();
                title = getString(R.string.address);
                break;
            case Constant.ITEM_PROFILE_USER:
                fragment = ProfileUserFragment.getInstance();
                title = getString(R.string.userProfile);
                break;
            case Constant.ITEM_SIGN_OFF:
                break;
        }
        if (fragment != null && mFragmentManager != null) {
            sideBarAdapter.selectItem(position);
            mFragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
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
}
