/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

package com.restaurant.project.mikuyapp.scan;

import android.os.AsyncTask;

import com.restaurant.project.mikuyapp.scan.adapter.Host;
import com.restaurant.project.mikuyapp.scan.model.DiscoveryModel;
import com.restaurant.project.mikuyapp.scan.ui.ScannerCallback;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import jcifs.netbios.NbtAddress;

public class DiscoveryTask extends AsyncTask<DiscoveryModel, Host, List<Host>> {

    private static final String NONAME = "unkowok";
    private final ScannerCallback scannerCallback;

    public DiscoveryTask(ScannerCallback scannerCallback) {
        this.scannerCallback = scannerCallback;
    }

    @Override
    protected void onPreExecute() {
        if (scannerCallback != null) {
            scannerCallback.onPreExecute();
        }
        super.onPreExecute();
    }

    @Override
    protected List<Host> doInBackground(DiscoveryModel... discoveryModels) {
        DiscoveryModel discoveryModel = discoveryModels[0];
        List<Host> hostList = new ArrayList<>();
        long endAddress = discoveryModel.getEndAddress();
        long startAddress = discoveryModel.getStartAddress();
        for (long i = startAddress; i < endAddress + 1; i++) {
            Host host = new Host();
            host.setIpAddress(NetInfo.getIpFromLongUnsigned(i));
            LogUtil.d("IpAddress:" + host.getIpAddress());
            try {
                if (isCancelled()) break;
                NbtAddress nbtAddress = NbtAddress.getByName(host.getIpAddress());
                host.setHardwareAddress(HardwareAddress.bytesToHex(nbtAddress.getMacAddress()));
                host.setName(nbtAddress.getHostName());
            } catch (UnknownHostException e) {
                if (isCancelled()) break;
                host.setName(NONAME);
            }
            if (host.getIpAddress().equals(discoveryModel.getIpAddress())) {
                host.setHardwareAddress(discoveryModel.getMacAddress());
                host.setName(discoveryModel.getHostName());
            }
            if (!host.getName().equals(NONAME)) {
                if (isCancelled()) break;
                hostList.add(host);
                publishProgress(host);
            }
        }
        return hostList;
    }

    @Override
    protected void onPostExecute(List<Host> hosts) {
        if (scannerCallback != null) {
            scannerCallback.onPostExecute(hosts);
        }
    }

    @Override
    protected void onProgressUpdate(Host... values) {
        if (scannerCallback != null) {
            scannerCallback.onProgressUpdate(values[0]);
        }
    }

    @Override
    protected void onCancelled() {
        LogUtil.d("onCancelll");
        if (scannerCallback != null) {
            scannerCallback.onCancelled();
        }
    }
}
