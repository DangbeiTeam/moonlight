package com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.response;


import com.wangjiegulu.dal.request.core.interceptor.IResponseInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public class ResponseTokenExpiresInterceptor implements IResponseInterceptor {
    private static final String TAG = ResponseTokenExpiresInterceptor.class.getSimpleName();

    @Override
    public void onResponseIntercept(XRequest xRequest, Object dalBaseResponse) throws Throwable {
//        if (response.getResult(false)) {
//            return;
//        }

        // 如果Response 返回Token过期，则判断当前是否在登录界面，如果不是，则跳转过去
//        if (ResponseCode.ERROR_TOKEN_EXPIRES == response.getCode(-1)) {
//            if (!LoginActivity.class.getName().equals(ActivityLifeCycleListener.get().getTopActivityClassName())) {
//                Context context = HaquApplication.instance;
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        }

    }
}
