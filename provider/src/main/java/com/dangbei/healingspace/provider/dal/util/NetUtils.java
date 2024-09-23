package com.dangbei.healingspace.provider.dal.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtils {

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {

                        String ip = inetAddress.getHostAddress().toString();
                        if (ip.startsWith("10.0")) continue;

                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取IPV4地址
     * @return
     */
    public static String getLocalIpV4Address() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress instanceof Inet4Address) {
                        String ip = inetAddress.getHostAddress();
                        if (ip.startsWith("10.0")) continue;

                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }





    public static boolean isLocalPortInUse(int port)  {
        boolean flag = false;
        InetAddress theAddress = null;
        try {
            theAddress = InetAddress.getLocalHost();
            Socket socket = new Socket(theAddress, port);
            socket.close();
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

}
