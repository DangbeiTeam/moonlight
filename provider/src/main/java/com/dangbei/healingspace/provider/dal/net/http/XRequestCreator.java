package com.dangbei.healingspace.provider.dal.net.http;

import android.annotation.SuppressLint;

import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 27/03/2018.
 */
public class XRequestCreator {

    public static class Holder {
        @SuppressLint("StaticFieldLeak")
        static XRequestCreator INSTANCE = new XRequestCreator();
    }

    public static XRequestCreator getInstance() {
        return Holder.INSTANCE;
    }


    /**
     * 默认https
     */
    public  XRequest createRequest(String httpUrl) {
        return XRequest.create(httpUrl);
    }

}