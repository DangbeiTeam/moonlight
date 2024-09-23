package com.dangbei.healingspace.provider.bll.application.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.dangbei.healingspace.provider.bll.application.ProviderApplication;
import com.dangbei.xlog.XLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/5/16.
 */
public final class AppInfoUtil {
    private static final String TAG = AppInfoUtil.class.getSimpleName();

    private AppInfoUtil() {
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @author wangjie
     */
    public static String getVersionName() {
        String version = "0";
        try {
            Context context = ProviderApplication.getInstance().getApplication();
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            XLog.e(TAG, "getAppVersion", e);
        }
        XLog.d(TAG, "该应用的版本号: " + version);
        return version;
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @author wangjie
     */
    public static int getVersionCode() {
        int version = 0;
        try {
            Context context = ProviderApplication.getInstance().getApplication();
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            XLog.e(TAG, "getAppVersion", e);
        }
        XLog.d(TAG, "该应用的版本号: " + version);
        return version;
    }

    /**
     * 获取指定应用的版本名称
     *
     * @author wangjie
     */
    public static String getVersionName(String packageName) {
        String version = "0";
        try {
            Context context = ProviderApplication.getInstance().getApplication();
            version = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            XLog.e(TAG, "getAppVersion", e);
        }
        XLog.d(TAG, "该应用的版本号: " + version);
        return version;
    }


    public static String getCPU() {
        String abi = "";
//        if (Build.VERSION.SDK_INT < 21) {
//            abi = Build.CPU_ABI;
//        } else {
//            abi = Build.SUPPORTED_ABIS[0];
//        }
        return abi;
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceIMEI(Context context) {
        String deviceId = null;
        try {
            TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephony.getDeviceId();
            if (deviceId == null || deviceId.length() <= 1) {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Throwable throwable) {
            XLog.w(TAG, throwable);
        }
        if (null == deviceId) {
            deviceId = "Unknown";
        }
        XLog.d(TAG, "当前设备IMEI码: " + deviceId);
        return deviceId;
    }

    /**
     * 获得限定条件下的包名应用
     *
     * @param isSystemFilter 是否过滤系统应用
     * @param filterList     需要过滤的列表
     * @return 经过过滤条件下的包名列表
     */
    public static List<String> getAllInstalledPackage(boolean isSystemFilter, List<String> filterList) {
        final PackageManager packageManager = ProviderApplication.getInstance().getApplication().getPackageManager();
        List<PackageInfo> packageInfo = packageManager.getInstalledPackages(0);
        List<String> packages = new ArrayList<>();
        if (packageInfo != null) {
            for (int i = 0, len = packageInfo.size(); i < len; i++) {
                if (isSystemFilter && ((ApplicationInfo.FLAG_SYSTEM & packageInfo.get(i).applicationInfo.flags) != 0)) {
                    //过滤掉系统app
                    continue;
                }
                String pn = packageInfo.get(i).packageName;
                //过滤掉需要过滤的列表
                if (filterList != null && filterList.contains(pn)) {
                    continue;
                }
                packages.add(pn);
            }
        }
        return packages;
    }


    /**
     * 根据包名获取一个已经安装在系统的应用信息
     *
     * @param packageName 包名
     * @return 应用信息 可为null
     */
    public static PackageInfo getPackageInfo(String packageName) {
        final PackageManager packageManager = ProviderApplication.getInstance().getApplication().getPackageManager();
        try {
            return packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
        }
        return null;
    }

    public static boolean checkAppInstalledAndCode(String pkgName, int code) {
        if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo = getPackageInfo(pkgName);
        //true为安装了，false为未安装
        return packageInfo != null && packageInfo.versionCode > code;
    }

}
