package com.restaurant.project.mikuyapp.scan.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant.project.mikuyapp.R;
import com.restaurant.project.mikuyapp.scan.DiscoveryTask;
import com.restaurant.project.mikuyapp.scan.NetInfo;
import com.restaurant.project.mikuyapp.scan.adapter.Host;
import com.restaurant.project.mikuyapp.scan.adapter.ScannerAdapter;
import com.restaurant.project.mikuyapp.scan.manually.ChangeIpMannuallyDialog;
import com.restaurant.project.mikuyapp.scan.model.DiscoveryModel;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.AnimationUtil;
import com.restaurant.project.mikuyapp.utils.Constant;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import java.util.List;

public class ScannerActivity extends AppCompatActivity implements ScannerAdapter.Callback,
        ScannerCallback, ChangeIpMannuallyDialog.Callback {

    private NetInfo netInfo;
    private String information;
    private Button btnCancel;
    private Button btnStart;
    private TextView tvWifiState;
    private TextView tvInformation;
    private MyBroadcast myBroadcast;
    private ProgressBar pbProgress;
    private RecyclerView rvHostScan;
    private TextView tvServerAddress;
    private LinearLayout llInformation;
    private ScannerAdapter scannerAdapter;
    private ConnectivityManager connectivityManager;
    private static final String TAG = "changeIpManuallyDialog";
    //Wifi
    private long networkIp = 0;
    private long networkEnd = 0;
    private long networkStart = 0;
    private boolean stateComplete = false;
    private DiscoveryTask discoveryTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        rvHostScan = findViewById(R.id.rvHostScan);
        tvWifiState = findViewById(R.id.tvWifiState);
        btnCancel = findViewById(R.id.btnCancel);
        btnStart = findViewById(R.id.btnStart);
        information = getString(R.string.wifi_desabled);
        llInformation = findViewById(R.id.llInformation);
        tvInformation = findViewById(R.id.tvInformation);
        pbProgress = findViewById(R.id.pbProgress);
        tvServerAddress = findViewById(R.id.tvServerAddress);
        netInfo = new NetInfo();
        myBroadcast = new MyBroadcast();
        initToolbar();
        initRecyclerView();
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        MikuyPreference.saveInterfaceName(NetInfo.DEFAULT_INTERFACE);
        setInformationAppBar(MikuyPreference.getIpAddressServer(), true);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkStart != 0 && networkEnd != 0 && stateComplete) {
                    DiscoveryModel discoveryModel = new DiscoveryModel();
                    discoveryModel.setMacAddress(netInfo.getMacAddress());
                    discoveryModel.setIpAddress(netInfo.getIp());
                    discoveryModel.setStartAddress(networkStart);
                    discoveryModel.setEndAddress(networkEnd);
                    discoveryModel.setHostName(Settings.Secure.getString(getContentResolver(),
                            Constant.DEVICE_NAME));
                    discoveryTask = new DiscoveryTask(ScannerActivity.this);
                    discoveryTask.execute(discoveryModel);
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTasks();
            }
        });
    }

    private void cancelTasks() {
        if (discoveryTask != null) {
            discoveryTask.cancel(true);
        }
    }

    @Override
    public void updateIpAddress() {
        setInformationAppBar(MikuyPreference.getIpAddressServer(), true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        registerReceiver(myBroadcast, filter);
    }

    @Override
    public void onClickHost(String ipAddress) {
        setInformationAppBar(ipAddress);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcast);
        cancelTasks();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setInformationAppBar(String ip, boolean state) {
        AnimationUtil.slideRightAnimationView(llInformation, this);
        if (state) {
            tvInformation.setText(R.string.scanTitleScan);
            tvServerAddress.setText(MikuyPreference.getIpAddressServer());
        } else {
            tvInformation.setText(R.string.newScanTitleScan);
            tvServerAddress.setText(ip);
            MikuyPreference.saveIpAddressServer(ip);
        }
    }

    private void setInformationAppBar(String ip) {
        setInformationAppBar(ip, false);
    }

    private void initRecyclerView() {
        scannerAdapter = new ScannerAdapter(this);
        scannerAdapter.setCallback(this);
        rvHostScan.setAdapter(scannerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.itemAddressManual) {
            ChangeIpMannuallyDialog.getInstance().show(getSupportFragmentManager(), TAG);
        }
        return true;
    }

    private void updateInformation() {
        tvWifiState.setText(information);
        if (stateComplete) {
            btnStart.setEnabled(true);
            tvWifiState.setBackgroundResource(android.R.color.transparent);
            networkIp = NetInfo.getUnsignedLongFromIp(netInfo.getIp());
            int shift = (32 - netInfo.getCidr());
            if (netInfo.getCidr() < 31) {
                networkStart = (networkIp >> shift << shift) + 1;
                networkEnd = (networkStart | ((1 << shift) - 1)) - 1;
            } else {
                networkStart = (networkIp >> shift << shift);
                networkEnd = (networkStart | ((1 << shift) - 1));
            }
        } else {
            btnStart.setEnabled(false);
            tvWifiState.setBackgroundResource(R.color.colorExpire);
        }
    }

    @Override
    public void onPreExecute() {
        scannerAdapter.clear();
        btnStart.setVisibility(View.GONE);
        btnCancel.setVisibility(View.VISIBLE);
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressUpdate(Host host) {
        scannerAdapter.addHost(host);
    }

    @Override
    public void onPostExecute(List<Host> hosts) {
        btnCancel.setVisibility(View.GONE);
        pbProgress.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);
        if (hosts.isEmpty()) {
            Toast.makeText(this, R.string.emptyDispositive, Toast.LENGTH_SHORT).show();
        } else {
            scannerAdapter.addList(hosts);
        }
        cancelTasks();
    }

    @Override
    public void onCancelled() {
        btnCancel.setVisibility(View.GONE);
        pbProgress.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);
        Toast.makeText(this, R.string.cancelScanner, Toast.LENGTH_SHORT).show();
    }

    private void resetVariables() {
        stateComplete = false;
        networkEnd = 0;
        networkIp = 0;
        networkStart = 0;
    }

    /* WifiBroadcast*/
    class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            resetVariables();
            String action = intent.getAction();
            if (action != null) {
                if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                    switch (wifiState) {
                        case WifiManager.WIFI_STATE_ENABLING:
                            LogUtil.d("enabling");
                            information = getString(R.string.wifi_enabling);
                            break;
                        case WifiManager.WIFI_STATE_ENABLED:
                            LogUtil.d("enabled");
                            information = getString(R.string.wifi_enabled);
                            break;
                        case WifiManager.WIFI_STATE_DISABLING:
                            LogUtil.d("disabling");
                            information = getString(R.string.wifi_desabling);
                            break;
                        case WifiManager.WIFI_STATE_DISABLED:
                            LogUtil.d("disabled");
                            information = getString(R.string.wifi_desabled);
                            break;
                        case WifiManager.WIFI_STATE_UNKNOWN:
                            LogUtil.d("unkown ");
                            information = getString(R.string.wifi_unknown);
                            break;
                    }
                } else if (action.equals(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION) && netInfo.getWifiInfo()) {
                    SupplicantState state = netInfo.getSupplicantState();
                    switch (state) {
                        case SCANNING:
                            LogUtil.d("entor en scanning");
                            information = getString(R.string.wifi_scanning);
                            break;
                        case ASSOCIATING:
                            LogUtil.d("entor en asocciate");
                            information = getString(R.string.wifi_associating, netInfo.getSsid());
                            break;
                        case COMPLETED:
                            LogUtil.d("entor en complete");
                            if (!netInfo.getSsid().equals(NetInfo.DEFAULT_SSID)) {
                                information = getString(R.string.wifi_completed, netInfo.getSsid());
                            }
                            break;
                    }
                }
            }
            final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                    int type = ni.getType();
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        if (netInfo.getWifiInfo()) {
                            netInfo.initIp();
                            if (!netInfo.getIp().equals(NetInfo.NOIP) &&
                                    !netInfo.getSsid().equals(NetInfo.DEFAULT_SSID)) {
                                information = getString(R.string.wifi_net_ip, netInfo.getSsid(),
                                        netInfo.getIp(), netInfo.getGatewayIp(), netInfo.getCidr());
                                stateComplete = true;
                            }
                        }
                    } else if (type == 3 || type == 9) {
                        information = getString(R.string.wifi_detect_internet);
                    }
                } else {
                    cancelTasks();
                }
            } else {
                cancelTasks();
            }
            updateInformation();
        }
    }
}
