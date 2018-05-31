package com.restaurant.project.mikuyapp.scan.ui;

import com.restaurant.project.mikuyapp.scan.adapter.Host;

import java.util.List;

public interface ScannerCallback {
    void onProgressUpdate(Host host);

    void onPostExecute(List<Host> hosts);

    void onPreExecute();

    void onCancelled();

}
