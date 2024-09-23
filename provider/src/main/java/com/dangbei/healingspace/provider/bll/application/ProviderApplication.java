package com.dangbei.healingspace.provider.bll.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.dangbei.healingspace.provider.bll.app.data.DataController;
import com.dangbei.healingspace.provider.bll.application.configuration.ApplicationConfiguration;
import com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.request.RequestExtraParamsInterceptor;
import com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.request.RequestHeaderInterceptor;
import com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.request.RequestSignAndEncryptInterceptor;
import com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.response.OriginResponseEncryptInterceptor;
import com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.response.ResponseTokenExpiresInterceptor;
import com.dangbei.healingspace.provider.bll.utils.Constant;
import com.dangbei.healingspace.provider.dal.db.helper.DatabaseFactory;
import com.dangbei.healingspace.provider.dal.net.gson.GsonHelper;
import com.dangbei.healingspace.provider.dal.prefs.PrefsHelper;
import com.lerad.lerad_base_support.scheduler.ProviderSchedulers;
import com.wangjie.rapidorm.constants.RapidORMConfig;
import com.wangjiegulu.dal.request.XHttpManager;
import com.wangjiegulu.dal.request.gson.DefaultGsonResponseConverter;

/**
 * 与主项目之间上下文环境的同步
 * <p>
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 10/27/16.
 */
public class ProviderApplication {

    private static class Holder {
        @SuppressLint("StaticFieldLeak")
        private static ProviderApplication instance = new ProviderApplication();
    }

    private ApplicationConfiguration applicationConfiguration;


    private int assertFileCopyNum = 0;
    private static boolean IS_PRODUCT_ENV = false; // 是否正式环境.

    public static ProviderApplication getInstance() {
        return Holder.instance;
    }

    public static boolean isProdEnv() {
        return IS_PRODUCT_ENV;
    }

    private ProviderApplication() {
    }

    public ProviderApplication setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
        return this;
    }


    public void initialize() {

        boolean isDebug = applicationConfiguration.isBuildConfigDebug();

        //webapi config
        IS_PRODUCT_ENV = !isDebug;

        // init provider schedulers
        ProviderSchedulers.initialize();

        // rapidorm
        RapidORMConfig.DEBUG = isDebug;

        // configuration dal_request
        XHttpManager.getInstance()
                // 添加请求默认header
                .setApplication(applicationConfiguration.getApplication())
                .addRequestParamsInterceptor(new RequestHeaderInterceptor())
                // 添加请求默认参数
                .addRequestParamsInterceptor(new RequestExtraParamsInterceptor())
                // 签名&加密
                .setRequestSubmitParamsInterceptor(new RequestSignAndEncryptInterceptor())
                // Response 解密
                .setOriginResponseInterceptor(new OriginResponseEncryptInterceptor())
                // Token过期处理
                .addResponseInterceptor(new ResponseTokenExpiresInterceptor())
                // responseConverter
                .setResponseConverter(DefaultGsonResponseConverter.create(GsonHelper.getOriginalGson()))
                .setDebug(isDebug);
        //初始化数据库
        resetDataBase(getApplication(), null);
        //初始化数据控制器
        DataController.getInstance().init();

        if (PrefsHelper.getInstance().getBoolean(Constant.SP.isFirst, true)) {
            PrefsHelper.getInstance().putBoolean(Constant.SP.isFirst, false).commit();
        }
    }

    public static void resetDataBase(Context context, String databaseName) {
        //database
        if (null == databaseName) {
            DatabaseFactory.getInstance(context).resetDatabase(Constant.DB.DB_SYMBOL + ".db");
        } else if (!databaseName.endsWith(".db")) {
            throw new RuntimeException("databaseName should be ended with .db");
        } else {
            DatabaseFactory.getInstance(context).resetDatabase(databaseName);
        }
    }

    public Application getApplication() {
        return applicationConfiguration.getApplication();
    }

    public boolean isDebug() {
        return applicationConfiguration.isBuildConfigDebug();
    }


    public int getAssertFileCopyNum() {
        return assertFileCopyNum;
    }
}
