package com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.response;

import com.dangbei.xlog.XLog;
import com.wangjiegulu.dal.request.core.interceptor.IOriginResponseInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Created by wenhechen on 2017/12/07 11:22
 * 说明 代码版权归 杭州当贝网 所有
 */
public class OriginResponseEncryptInterceptor implements IOriginResponseInterceptor {
    private static final String TAG = OriginResponseEncryptInterceptor.class.getSimpleName();

    @Override
    public byte[] onOriginResponseIntercept(XRequest xRequest, byte[] responseBytes) throws Throwable {
        String json = new String(responseBytes);
//        if (ProviderApplication.getInstance().isDebug()) {
        XLog.d("db_response", "url = " + xRequest.getUrl() + "  response = " + json);
//        }
//        byte[] result = DesHelper.getInstance().decode(responseBytes);
//        if(result == null){
//            result = new byte[0];
//        }
//        XLog.d("xcc", "解密后 url = " + xRequest.getUrl() + "  response = " + new String(result));
        return responseBytes;
    }
}
