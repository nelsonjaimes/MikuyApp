package com.restaurant.project.mikuyapp.scan.adapter;

public class Host {
    private String name;
    private String hardwareAddress;
    private String ipAddress;
    private boolean select;

    public Host() {
        this.select = false;
    }

    public Host(String name, String ipAddress, String hardwareAddress) {
        this.name = name;
        this.hardwareAddress = hardwareAddress;
        this.ipAddress = ipAddress;
        this.select = false;
    }

    public boolean isSelect() {
        return select;
    }

    public String getHardwareAddress() {
        return hardwareAddress;
    }

    public void setHardwareAddress(String hardwareAddress) {
        this.hardwareAddress = hardwareAddress;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
