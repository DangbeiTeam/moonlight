package com.dangbei.healingspace.provider.bll.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;

import com.dangbei.healingspace.provider.bll.application.ProviderApplication;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * zml created on 2018.8.23  10:02
 */
public class DeviceInfoUtil {

    /**
     * 获取android系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取设备类型
     *
     * @return
     */
    public static String getDeviceType() {
        return Build.MODEL.replaceAll(" ", "");
    }

    /**
     * 获取设备序列号
     *
     * @return
     */
    public static String getDeviceSerial() {
        return Build.SERIAL;
    }

    /**
     * 获取系统开机时间
     *
     * @return
     */
    public static String getBootstrapTime() {
        int totalSeconds = (int) SystemClock.elapsedRealtime() / 1000;
        int second = totalSeconds % 60;
        int minute = (totalSeconds / 60) % 60;
        int hour = totalSeconds / 3600;
        return (hour <= 0 ? "" : hour + "小时") + (minute <= 0 ? "" : minute + "分") + (second <= 0 ? "" : second + "秒");
    }

    /**
     * 获取设备IP地址
     *
     * @return
     */
    public static String getDeviceIP() {
        NetworkInfo info = ((ConnectivityManager) ProviderApplication.getInstance()
                .getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {

                }
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface networkInterface = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {

                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager manager = (WifiManager) ProviderApplication.getInstance().getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = manager.getConnectionInfo();
                return intIP2StringIP(wifiInfo.getIpAddress());
            }
        }
        return "0.0.0.0";
    }

    /**
     * 获取设备Mac地址
     *
     * @return
     */
    public static String getDeviceMac() {
        StringBuilder buf = new StringBuilder();
        try {
            byte[] mac;
            NetworkInterface ne = NetworkInterface.getByInetAddress(InetAddress.getByName(getDeviceIP()));
            mac = ne.getHardwareAddress();
            for (byte b : mac) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            return buf.toString();
        } catch (Exception e) {

        }
        return "unKnown";
    }


    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}
