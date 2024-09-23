package com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.request;


import com.wangjiegulu.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public class RequestHeaderInterceptor implements IRequestInterceptor {
    @Override
    public void onRequestIntercept(XRequest xRequest) throws Throwable{
        xRequest.addHeader("Accept-Encoding", "gzip");
        xRequest.addHeader("Content-Type", "application/json;charset=utf-8");
    }
}
