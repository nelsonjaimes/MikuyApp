/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

//am start -a android.intent.action.MAIN -n com.android.settings/.wifi.WifiSettings
package com.restaurant.project.mikuyapp.scan;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.restaurant.project.mikuyapp.MikuyApplication;
import com.restaurant.project.mikuyapp.storage.MikuyPreference;
import com.restaurant.project.mikuyapp.utils.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetInfo {
    private static final int BUF = 8 * 1024;
    public static final String NOIP = "0.0.0.0";
    public static final String DEFAULT_INTERFACE = null;
    private static final String INTERFACE_WIFI = "wlan";
    private static final String NOMASK = "255.255.255.255";
    private static final String NOMAC = "00:00:00:00:00:00";
    public static final String DEFAULT_SSID = "<unknown ssid>";
    private static final String CMD_IP = " -f inet addr show %s";
    private static final String PTN_IF = "^%s: ip [0-9\\.]+ mask ([0-9\\.]+) flags.*";
    private static final String PTN_IP1 = "\\s*inet [0-9\\.]+\\/([0-9]+) brd [0-9\\.]+ scope global %s$";
    private static final String PTN_IP2 = "\\s*inet [0-9\\.]+ peer [0-9\\.]+\\/([0-9]+) scope global %s$";
    private String intf;
    private WifiInfo info;
    private int cidr = 24;
    private String ip = NOIP;
    private String ssid = null;
    private String gatewayIp = NOIP;
    private String macAddress = NOMAC;
    private String netmaskIp = NOMASK;

    public NetInfo() {
        initIp();
        getWifiInfo();
    }

    public void initIp() {
        intf = MikuyPreference.getNameInterface();
        try {
            if (intf == null) {
                for (Enumeration<NetworkInterface> en =
                     NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface ni = en.nextElement();
                    intf = ni.getName();
                    if (intf.contains(INTERFACE_WIFI)) {
                        LogUtil.d("Inter1:" + ni.getName());
                        ip = getInterfaceFirstIp(ni);
                        if (!ip.equals(NOIP)) {
                            LogUtil.d("Name:" + ni.getDisplayName());
                            MikuyPreference.saveInterfaceName(intf);
                            macAddress = HardwareAddress.bytesToHex(ni.getHardwareAddress());
                            LogUtil.d("Mac:" + macAddress);
                            break;
                        }
                    }
                }
            } else {
                ip = getInterfaceFirstIp(NetworkInterface.getByName(intf));
            }
        } catch (SocketException e) {
            LogUtil.d(e.getMessage());
            MikuyPreference.saveInterfaceName(DEFAULT_INTERFACE);
        }
        initCidr();
    }

    public int getCidr() {
        return cidr;
    }

    private String getInterfaceFirstIp(NetworkInterface ni) {
        if (ni != null) {
            for (Enumeration<InetAddress> nis = ni.getInetAddresses();
                 nis.hasMoreElements(); ) {
                InetAddress ia = nis.nextElement();
                if (!ia.isLoopbackAddress()) {
                    if (ia instanceof Inet4Address) {
                        return ia.getHostAddress();
                    }
                }
            }
        }
        return NOIP;
    }

    public String getMacAddress() {
        return macAddress;
    }

    private void initCidr() {
        if (!netmaskIp.equals(NOMASK)) {
            cidr = IpToCidr(netmaskIp);
        } else {
            String match;
            try {
                if ((match = runCommand("/system/xbin/ip", String.format(CMD_IP, intf),
                        String.format(PTN_IP1, intf))) != null) {
                    cidr = Integer.parseInt(match);
                } else if ((match = runCommand("/system/xbin/ip", String.format(CMD_IP, intf),
                        String.format(PTN_IP2, intf))) != null) {
                    cidr = Integer.parseInt(match);
                } else if ((match = runCommand("/system/bin/ifconfig", " " + intf,
                        String.format(PTN_IF, intf))) != null) {
                    cidr = IpToCidr(match);
                } else {
                    LogUtil.d("cannot find cidr, using default /24");
                }
            } catch (NumberFormatException e) {
                LogUtil.d(e.getMessage());
            }
        }
    }

    private String runCommand(String path, String cmd, String ptn) {
        try {
            if (new File(path).exists()) {
                String line;
                Matcher matcher;
                Pattern ptrn = Pattern.compile(ptn);
                Process p = Runtime.getRuntime().exec(path + cmd);
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()), BUF);
                while ((line = r.readLine()) != null) {
                    matcher = ptrn.matcher(line);
                    if (matcher.matches()) {
                        return matcher.group(1);
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.d(e.getMessage());
            return null;
        }
        return null;
    }

    public boolean getWifiInfo() {
        WifiManager wifi = (WifiManager) MikuyApplication.contextApp.
                getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            info = wifi.getConnectionInfo();
            ssid = info.getSSID();
            LogUtil.d("Aqui la mac.....:" + macAddress);
            netmaskIp = getIpFromIntSigned(wifi.getDhcpInfo().netmask);
            gatewayIp = getIpFromIntSigned(wifi.getDhcpInfo().gateway);
            return true;
        }
        return false;
    }

    public WifiInfo getInfo() {
        return info;
    }

    public String getIp() {
        return ip;
    }

    public String getGatewayIp() {
        return gatewayIp;
    }

    public String getSsid() {
        return ssid;
    }


    public SupplicantState getSupplicantState() {
        return info.getSupplicantState();
    }

    public static long getUnsignedLongFromIp(String ip_addr) {
        String[] a = ip_addr.split("\\.");
        return (Integer.parseInt(a[0]) * 16777216 + Integer.parseInt(a[1]) * 65536
                + Integer.parseInt(a[2]) * 256 + Integer.parseInt(a[3]));
    }

    private static String getIpFromIntSigned(int ip_int) {
        String ip = "";
        for (int k = 0; k < 4; k++) {
            ip = ip.concat(((ip_int >> k * 8) & 0xFF) + ".");
        }
        return ip.substring(0, ip.length() - 1);
    }

    public static String getIpFromLongUnsigned(long ip_long) {
        String ip = "";
        for (int k = 3; k > -1; k--) {
            ip = ip.concat(((ip_long >> k * 8) & 0xFF) + ".");
        }
        return ip.substring(0, ip.length() - 1);
    }

    private int IpToCidr(String ip) {
        double sum = -2;
        String[] part = ip.split("\\.");
        for (String p : part) {
            sum += 256D - Double.parseDouble(p);
        }
        return 32 - (int) (Math.log(sum) / Math.log(2d));
    }
}
