package com.dangbei.moonlight;

import android.app.Application;
import android.content.Context;
import android.os.Looper;

import androidx.multidex.MultiDex;

import com.dangbei.healingspace.provider.bll.application.ProviderApplication;
import com.dangbei.healingspace.provider.bll.application.configuration.ApplicationConfiguration;
import com.dangbei.xlog.XLog;
import com.dangbei.xlog.XLogDelegateAndroid;
import com.lerad.lerad_base_support.bridge.compat.scheduler.AppSchedulers;
import com.lerad.lerad_base_util.AppUtil;
import com.monster.rxbus.RxBus2;
import com.wangjie.rapidorm.constants.RapidORMConfig;

/**
 * app 入口
 */
public class LauncherApplication extends Application {
    private static final String TAG = LauncherApplication.class.getSimpleName();
    public static LauncherApplication instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (AppUtil.isUIProcess(this)) {
            initUIProcess();
        }
    }


    private void initUIProcess() {
        boolean isDebug = BuildConfig.DEBUG;
        // init app schedulers
        AppSchedulers.initialize();
        // 初始化Log
        XLog.initialize(new XLogDelegateAndroid());
        XLog.setDEBUG(isDebug);
        // Provider
        ProviderApplication.getInstance()
                .setApplicationConfiguration(
                        new ApplicationConfiguration()
                                .setApplication(this)
                                .setBuildConfigDebug(isDebug)
                )
                .initialize();
        // rapidorm
        RapidORMConfig.DEBUG = isDebug;
        // RxBus
        RxBus2.setDebug(isDebug);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (AppUtil.isUIProcess(this)) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                // ...
            }
        }
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
