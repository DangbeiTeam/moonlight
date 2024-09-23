package com.dangbei.healingspace.provider.dal.net.http.webapi;

import com.dangbei.healingspace.provider.bll.application.ProviderApplication;
import com.dangbei.xlog.XLog;


/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public final class WebApiConstants {


    private WebApiConstants() {
    }

    private static final String HOST_STUFF = "screensaver.qun7.com";
    private static final String HOST_BACKUP_STUFF = "screensaver.fanqiejiang.net";
    private static final String TEST_HOST_STUFF = "screentestapi.qun7.com";
    private static String currentHost = HOST_STUFF;

    // http ITG (test)
    private static final String HOST_ITG = TEST_HOST_STUFF;
    private static final String HTTP_URL_ITG = "http://" + HOST_ITG;
    private static final int HTTP_PORT_ITG = 80;
    private static final String HTTPS_URL_ITG = "https://" + HOST_ITG;
    private static final int HTTPS_PORT_ITG = 443;

    // http STG
    private static final String HOST_STG = HOST_STUFF;
    private static final String HTTP_URL_STG = "http://" + HOST_STG;
    private static final int HTTP_PORT_STG = 80;
    private static final String HTTPS_URL_STG = "https://" + HOST_STG;
    private static final int HTTPS_PORT_STG = 443;

    // http BACKUP STG
    private static final String HOST_BACKUP_STG = HOST_BACKUP_STUFF;
    private static final String HTTP_BACKUP_URL_STG = "http://" + HOST_BACKUP_STG;
    private static final int HTTP_BACKUP_PORT_STG = 80;
    private static final String HTTPS_BACKUP_URL_STG = "https://" + HOST_BACKUP_STG;
    private static final int HTTPS_BACKUP_PORT_STG = 443;


    public static String getHttpHost() {
        return ProviderApplication.isProdEnv()
                ? getProdHost()
                : HTTP_URL_ITG + ":" + HTTP_PORT_ITG + WebApi.API_VERSION_V1;
    }


    public static String formatHttpWebApi(String httpWebApi) {
        String httpHost = getHttpHost();
        if (!httpWebApi.contains(httpHost)) {
            httpWebApi = httpHost + httpWebApi;
        }
        return httpWebApi;
    }

    private static String getProdHost() {
        String host = "http://" + currentHost + ":" + HTTP_PORT_STG + WebApi.API_VERSION_V1;
        XLog.d("getProdHost", "getProdHost " + host);
        //请求完成后 马上切换到主域名
        currentHost = HOST_STG;
        return host;
    }

    public static void changeHost() {
        XLog.d("getProdHost", "getProdHost " + "changeHost ");
        //备用的话 切换成主域名
        if (currentHost.equals(HOST_BACKUP_STUFF)) {
            currentHost = HOST_STUFF;
            //主域名切换成 备用域名
        } else {
            currentHost = HOST_BACKUP_STUFF;
        }
    }
}
