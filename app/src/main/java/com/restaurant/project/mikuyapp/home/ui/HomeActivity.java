package com.restaurant.project.mikuyapp.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.restaurant.project.mikuyapp.EntryMenuActivity;
import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.address.ui.AddressMapsActivity;
import com.restaurant.project.mikuyapp.contacts.ContactsFragment;
import com.restaurant.project.mikuyapp.dialog.DialogProgress;
import com.restaurant.project.mikuyapp.dialog.SignOffDialog;
import com.restaurant.project.mikuyapp.home.HomePresenter;
import com.restaurant.project.mikuyapp.home.HomePresenterImp;
import com.restaurant.project.mikuyapp.home.sidebar.adapter.SideBarAdapter;
import com.restaurant.project.mikuyapp.home.sidebar.ui.SideBarListener;
import com.restaurant.project.mikuyapp.letter.LetterOfThePlatesFragment;
import com.restaurant.project.mikuyapp.menutoday.ui.MenuTodayFragment;
import com.restaurant.project.mikuyapp.profile.ProfileUserFragment;
import com.restaurant.project.mikuyapp.reservation.MyReservationsFragment;
import com.restaurant.project.mikuyapp.scan.ui.ScannerActivity;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.BaseFragment;
import com.restaurant.project.mikuyapp.utils.Constant;
import com.restaurant.project.mikuyapp.utils.Operations;

import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity implements SideBarListener,
        HomeView, SignOffDialog.Callback {

    private RecyclerView rvSideBar;
    private SlidingPaneLayout splHome;
    private HomePresenter homePresenter;
    private SideBarAdapter sideBarAdapter;
    private DialogProgress dialogProgress;
    private static final String TAG_DIALOG = "dialogOff";
    private static final String TAG_ITEM = "itemTag";
    private HandlerSlidingPanel handlerSlidingPanel;
    private BaseFragment itemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        splHome = findViewById(R.id.splHome);
        rvSideBar = findViewById(R.id.rvSideBar);
        LinearLayout llSignOff = findViewById(R.id.llSignOff);
        LinearLayout llAddressLocation = findViewById(R.id.llAddressLocation);
        homePresenter = new HomePresenterImp(this);
        handlerSlidingPanel = new HandlerSlidingPanel(splHome);
        initSlidingPanel();
        initSideBar();
        initToolbar();
        if (savedInstanceState == null) {
            replaceFragment(Constant.ITEM_MENU_TODAY);
        }

        llSignOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOffDialog.getInstance().show(getSupportFragmentManager(), TAG_DIALOG);
            }
        });

        llAddressLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (splHome.isOpen()) {
                    startActivity(new Intent(HomeActivity.this,
                            AddressMapsActivity.class));
                }
            }
        });
    }

    @Override
    public void signOff() {
        MikuyPreference.deleteAll(this);
        startActivity(new Intent(HomeActivity.this,
                EntryMenuActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sideBarAdapter.setSideBarListener(this);
        homePresenter.onAttach(this);
        if (!MikuyPreference.getStateDownloadPlatesList(this)) {
            homePresenter.downloadPlatesList();
        }
    }

    @Override
    protected void onStop() {
        sideBarAdapter.setSideBarListener(null);
        if (homePresenter != null) homePresenter.onDettach();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (splHome.isOpen()) {
            splHome.closePane();
        } else {
            SignOffDialog.getInstance().show(getSupportFragmentManager(), TAG_DIALOG);
        }
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
        dialogProgress.dismiss();
    }

    @Override
    public void onSuccessDownloadPlatesList() {
        MikuyPreference.saveStateDownloadPlatesList(this, true);
        itemFragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(TAG_ITEM);
        if (itemFragment != null) {
            itemFragment.update();
        }
    }

    @Override
    public void showSnackBar(@NonNull String message) {
        Operations.getSnackBar(this, findViewById(R.id.llHome),
                message, Snackbar.LENGTH_LONG).show();
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
        if (splHome.isOpen()) {
            replaceFragment(pos);
            handlerSlidingPanel.close();
        }
    }

    private void replaceFragment(int position) {
        String title = null;
        switch (position) {
            case Constant.ITEM_MENU_TODAY:
                itemFragment = MenuTodayFragment.getInstance();
                title = getString(R.string.menu);
                break;
            case Constant.ITEM_LETTER_DISHES:
                itemFragment = LetterOfThePlatesFragment.getInstance();
                title = getString(R.string.foodLetter);
                break;
            case Constant.ITEM_RESERVATIONS:
                itemFragment = MyReservationsFragment.getInstance();
                title = getString(R.string.myReservations);
                break;
            case Constant.ITEM_CONTACTS:
                itemFragment = ContactsFragment.getInstance();
                title = getString(R.string.contacts);
                break;
            case Constant.ITEM_PROFILE_USER:
                itemFragment = ProfileUserFragment.getInstance();
                title = getString(R.string.userProfile);
                break;
        }
        if (itemFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                    itemFragment, TAG_ITEM).commit();
            sideBarAdapter.selectItem(position);
            setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settingshome, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            toggleDrawerLayout();
        } else if (item.getItemId() == R.id.itemSettings) {
            startActivity(new Intent(HomeActivity.this, ScannerActivity.class));
        } else if (item.getItemId() == R.id.itemSyncup) {
            if (homePresenter == null) {
                homePresenter = new HomePresenterImp(this);
            }
            homePresenter.downloadPlatesList();
            Toast.makeText(this, R.string.syncupMessage, Toast.LENGTH_SHORT).show();
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

    private static class HandlerSlidingPanel extends Handler implements Runnable {
        private final SlidingPaneLayout slidingPaneLayout;

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
