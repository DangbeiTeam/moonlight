package com.dangbei.healingspace.provider.dal.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dangbei.healingspace.provider.bll.application.ProviderApplication;

public class SpUtil {

    private SpUtil() {
    }

    public static SharedPreferences getSp(SpName name) {
        return ProviderApplication.getInstance().getApplication().getSharedPreferences(name.name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSpE(SpName name) {
        return getSp(name).edit();
    }

    public static String getString(SpKey key, String defValue) {
        return getString(SpName.CONFIG, key, defValue);
    }

    public static String getString(SpName name, SpKey key, String defValue) {
        return getSp(name).getString(key.key, defValue);
    }

    public static void putString(SpKey key, String value) {
        putString(SpName.CONFIG, key, value);
    }

    public static void putString(SpName name, SpKey key, String value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putString(key.key, value);
        spE.commit();
    }

    public static boolean getBoolean(SpKey key, boolean defValue) {
        return getBoolean(SpName.CONFIG, key, defValue);
    }

    public static boolean getBoolean(SpName name, SpKey key, boolean defValue) {
        return getSp(name).getBoolean(key.key, defValue);
    }

    public static void putBoolean(SpKey key, boolean value) {
        putBoolean(SpName.CONFIG, key, value);
    }

    public static void putBoolean(SpName name, SpKey key, boolean value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putBoolean(key.key, value);
        spE.commit();
    }

    public static int getInt(SpKey key, int defValue) {
        return getInt(SpName.CONFIG, key, defValue);
    }

    public static int getInt(SpName name, SpKey key, int defValue) {
        return getSp(name).getInt(key.key, defValue);
    }

    public static void putInt(SpKey key, int value) {
        putInt(SpName.CONFIG, key, value);
    }

    public static void putInt(SpName name, SpKey key, int value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putInt(key.key, value);
        spE.commit();
    }

    public static long getLong(SpKey key, long defValue) {
        return getLong(SpName.CONFIG, key, defValue);
    }

    public static long getLong(SpName name, SpKey key, long defValue) {
        return getSp(name).getLong(key.key, defValue);
    }

    public static void putLong(SpKey key, long value) {
        putLong(SpName.CONFIG, key, value);
    }

    public static void putLong(SpName name, SpKey key, long value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putLong(key.key, value);
        spE.commit();
    }

    public static void remove(SpKey key) {
        remove(SpName.CONFIG, key);
    }

    public static void remove(SpName name, SpKey key) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.remove(key.key);
        spE.commit();
    }

    /**
     * 判断SP中是否存在该key
     */
    public static boolean contains(SpKey key) {
        return contains(SpName.CONFIG, key);
    }

    /**
     * 判断SP中是否存在该key
     */
    public static boolean contains(SpName name, SpKey key) {
        SharedPreferences sp = getSp(name);
        return sp.contains(key.key);
    }

    private enum SpName {
        CONFIG("config");
        public String name;

        SpName(String name) {
            this.name = name;
        }
    }

    public enum SpKey {

        KEY_IS_WIFI_OPEN("key_is_wifi_open"),
        KEY_IS_WIFI_CONNECTED("key_is_wifi_connected"),
        KEY_IS_PROXY_URL_INFO("proxy_url_info"),
        KEY_IS_QR_INFO("QR_INFO"),
        KEY_IS_HOST_NAME("HOST_NAME"),
        KEY_IS_HOST_PORT("HOST_PORT"),
        KEY_IS_SELECT_PIC("CUSTOM_SELECT_PIC");


        public String key;

        SpKey(String key) {
            this.key = key;
        }
    }
}
