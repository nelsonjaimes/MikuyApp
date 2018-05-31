package com.restaurant.project.mikuyapp.scan.model;

public class DiscoveryModel {
    private long startAddress;
    private long endAddress;
    private String macAddress;
    private String ipAddress;
    private String hostName;

    public DiscoveryModel() {
    }

    public long getStartAddress() {
        return startAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setStartAddress(long startAddress) {
        this.startAddress = startAddress;
    }

    public long getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(long endAddress) {
        this.endAddress = endAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
