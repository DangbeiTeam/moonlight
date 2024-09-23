package com.dangbei.healingspace.provider.bll.application.info;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;

import com.dangbei.edeviceid.DeviceUtils;
import com.dangbei.healingspace.provider.bll.application.ProviderApplication;
import com.dangbei.lerad.util.SystemPropertyUtil;


/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public class ProviderApplicationInfo {
    private static class Holder {
        private static ProviderApplicationInfo instance = new ProviderApplicationInfo();
    }

    public static ProviderApplicationInfo getInstance() {
        return Holder.instance;
    }

    /**
     * 版本名
     */
    private String versionName;
    /**
     * 版本号
     */
    private int versionCode = -1;
    /**
     * CPU架构
     */
    private String cpu;
    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 是否是运行平台
     */
    private String clientPlatform;

    /**
     * 渠道信息
     */
    private String channel;

    /**
     * MAC地址
     */
    private String macAddress;

    public String getVersionName() {
        if (null == versionName) {
            versionName = AppInfoUtil.getVersionName();
        }
        return versionName;
    }

    public int getVersionCode() {
        if (-1 == versionCode) {
            versionCode = AppInfoUtil.getVersionCode();
        }
        return versionCode;
    }

    public String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getCpu() {
        if (null == cpu) {
            cpu = AppInfoUtil.getCPU();
        }
        return cpu;
    }

    public String getChannel() {
        if (channel == null) {
            channel = "DBOS_" + Build.MODEL.replaceAll(" ", "");
        }
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceId() {
        if (null == deviceId) {
            deviceId = DeviceUtils.getDeviceIdByHardware(ProviderApplication.getInstance().getApplication());
        }
        return deviceId;
    }

//    String cpuserial;
//
//    public String getCpuserial() {
//        if (cpuserial == null) {
//            cpuserial = DeviceInfoUtils.getCPUSerialSync();
//        }
//        return cpuserial;
//    }
//
//    String sn;
//
//    public String getSn() {
//        if (sn == null) {
//            sn = DeviceInfoUtils.getSystemSerialNum();
//        }
//        return sn;
//    }
//
//    String macAddress;
//
//    public String getMacAddress() {
//        if (TextUtils.isEmpty(macAddress)) {
//            macAddress = DeviceInfoUtils.getEthernetMacAddr(ProviderApplication.getInstance().getApplication());
//        }
//        return macAddress;
//    }

    public String getIsTv() {
        if (clientPlatform == null) {
            UiModeManager uiModeManager = (UiModeManager) ProviderApplication.getInstance()
                    .getApplication().getSystemService(Context.UI_MODE_SERVICE);
            if (uiModeManager != null && uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION) {
                clientPlatform = "tv";
            } else {
                clientPlatform = "unKnown";
            }
        }
        return clientPlatform;
    }

    public String getNetworkType() {
        return NetUtil.getNetworkState(ProviderApplication.getInstance().getApplication());
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNetworkStatus() {
        Context context = ProviderApplication.getInstance().getApplication();
        if (NetUtil.isWifi(context)) {
            return "wifi";
        }
        return String.valueOf(NetUtil.getNetWorkClass(context));
    }
    public String getMacAddress() {
        if (TextUtils.isEmpty(macAddress)) {
            macAddress = SystemPropertyUtil.getEthernetMacAddr(ProviderApplication.getInstance().getApplication());
        }
        return macAddress;
    }
}
