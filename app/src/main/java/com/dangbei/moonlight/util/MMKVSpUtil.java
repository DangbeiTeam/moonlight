package com.dangbei.moonlight.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import com.dangbei.moonlight.LauncherApplication;

/**
 * 实现方式修改为KV
 */
public class MMKVSpUtil {
    public static final String SP_NAME = "config";

    private MMKVSpUtil() {
    }

    public static SharedPreferences getSp(String name) {
        return LauncherApplication.instance
                .getApplicationContext()
                .getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    private static SharedPreferences.Editor getSpE(String name) {
        return getSp(name).edit();
    }

    public static String getString(String name, String key, String defValue) {
        return getSp(name).getString(key, defValue);
    }

    @Nullable
    public static String getString(String name, String key) {
        return getSp(name).getString(key, null);
    }

    public static void remove(String name, String key) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.remove(key);
//        spE.commit();
    }

    public static void putString(String name, String key, String value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putString(key, value).apply();
//        spE.commit();
    }

    public static int getInt(String name, String key) {
        return getSp(name).getInt(key, 0);
    }

    public static void putInt(String name, String key, int value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putInt(key, value).apply();
//        spE.commit();
    }


    public static boolean getBoolean(String name, String key, boolean defValue) {
        return getSp(name).getBoolean(key, defValue);
    }


    public static void putBoolean(String name, String key, boolean value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putBoolean(key, value).apply();
//        spE.commit();
    }


    public static int getInt(String name, String key, int defValue) {
        return getSp(name).getInt(key, defValue);
    }


    public static long getLong(String name, String key, long defValue) {
        return getSp(name).getLong(key, defValue);
    }


    public static void putLong(String name, String key, long value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putLong(key, value).apply();
//        spE.commit();
    }



    /**
     * 判断SP中是否存在该key
     */
    public static boolean contains(String name, String key) {
        SharedPreferences sp = getSp(name);
        return sp.contains(key);
    }

    public static float getFloat(String name, String key, float defValue) {
        return getSp(name).getFloat(key, defValue);
    }

    public static void putFloat(String name, String key, float value) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.putFloat(key, value).apply();
    }

    public static void clear(String name) {
        SharedPreferences.Editor spE = getSpE(name);
        spE.clear();
    }

    public enum SpKey {
        /**
         * bai location info
         */
        AGREEN_PRIV("agreen_priv"),
        USER_AGREEMENT_CONTENT("user_agreement_content"),
        APGREE_VERSION("user_agreement_txt"),
        USER_URL("user_url"),
        ADVANCE_ULR_URL("advance_ulr"),
        USER_AGREEMENT_TITLE("user_agreement_title");

        public String key;

        SpKey(String key) {
            this.key = key;
        }
    }
}
