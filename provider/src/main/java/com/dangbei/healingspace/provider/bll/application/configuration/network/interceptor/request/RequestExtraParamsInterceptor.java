package com.dangbei.healingspace.provider.bll.application.configuration.network.interceptor.request;

import com.dangbei.healingspace.provider.bll.application.ProviderApplication;
import com.dangbei.healingspace.provider.bll.application.info.ProviderApplicationInfo;
import com.dangbei.lerad.util.SystemPropertyUtil;
import com.dangbei.xlog.XLog;
import com.wangjiegulu.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;


/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/18/16.
 */
public class RequestExtraParamsInterceptor implements IRequestInterceptor {

    @Override
    public void onRequestIntercept(XRequest xRequest) throws Throwable {
        ProviderApplicationInfo providerApplicationInfo = ProviderApplicationInfo.getInstance();

//        String currentRomVerCode = String.valueOf(SystemPropertiesUtil.getRomCode());//SystemInfoUtil.getRomCode();
//        String currentRomVerName = SystemPropertiesUtil.getRomVersion();//SystemInfoUtil.getRomName();

        xRequest
                .addParameter("brand", SystemPropertyUtil.getProductBrand())
                .addParameter("rommodel", SystemPropertyUtil.getProductMode())
                .addParameter("channel", providerApplicationInfo.getChannel())
                .addParameter("vcode", providerApplicationInfo.getVersionCode())
                .addParameter("vname", providerApplicationInfo.getVersionName())
                .addParameter("network", providerApplicationInfo.getNetworkType())
                .addParameter("sn", SystemPropertyUtil.getDeviceSN())
                .addParameter("cpuserials", SystemPropertyUtil.getCPUSerialSync())
                .addParameter("packagename", ProviderApplication.getInstance().getApplication())
                .addParameter("mac",
                        SystemPropertyUtil.getEthernetMacAddr(ProviderApplication.getInstance().getApplication()))
                //这个加入后 就是加密填充
                //目前加密的形式是 参数不加密 返回值加密
                .addParameter("dang", "bei")
        ;


        //post 请求时需要添加的参数
        if (xRequest.getMethod() == XRequest.METHOD_POST) {
            xRequest.addParameter("deviceEid", providerApplicationInfo.getDeviceId());
        }

        if (ProviderApplication.getInstance().isDebug()) {
            XLog.d(RequestExtraParamsInterceptor.class.getSimpleName(),

                    ":channel:" + providerApplicationInfo.getChannel()
                            + ":vcode:" + providerApplicationInfo.getVersionCode()
                            + ":vname:" + providerApplicationInfo.getVersionName()
                            + ":cpu:" + providerApplicationInfo.getCpu()
                            + ":os:" + providerApplicationInfo.getOsVersion()
                            + ":network:" + providerApplicationInfo.getNetworkType()
                            + ":clientfrm:" + providerApplicationInfo.getIsTv()
                            + ":device_id:" + providerApplicationInfo.getDeviceId()
                            + ":packagename:" + ProviderApplication.getInstance().getApplication()
                            + ":mac " + SystemPropertyUtil.getEthernetMacAddr(
                            ProviderApplication.getInstance().getApplication())
                            + "\n " + "code is " + providerApplicationInfo.getVersionCode());

        }
    }
}
