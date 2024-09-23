package com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.request;


import android.util.Log;

import com.dangbei.healingspace.provider.bll.application.info.DesHelper;
import com.wangjiegulu.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;

import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public class RequestSignAndEncryptInterceptor implements IRequestInterceptor {

    @Override
    public void onRequestIntercept(XRequest xRequest) throws Throwable {
        TreeMap<String, String> params = xRequest.getParameters();
        TreeMap<String, String> submitParams = new TreeMap<>();

        if (null != params) {
            if (xRequest.isSkipEncrypt()) {
                submitParams = params;
            } else {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    submitParams.put(key, DesHelper.getInstance().encode(value));
                }
            }
        }
//        if (ProviderApplication.getInstance().isDebug()) {
            Log.d("db_request", "url = " + appendParams(xRequest.getUrl(), submitParams));
//        }
        xRequest.setSubmitParameters(submitParams);
    }


    private String appendParams(String url, Map<String, String> params) {
        url += url.contains("?") ? "&" : "?";
        StringBuilder sb = new StringBuilder().append(url);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                if (key.equals("md5")) {
                    sb.append(params.get(key)).append("&");
                } else {
                    sb.append(key).append("=")
                            .append(URLEncoder.encode(params.get(key)))
                            .append("&");
                }
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
