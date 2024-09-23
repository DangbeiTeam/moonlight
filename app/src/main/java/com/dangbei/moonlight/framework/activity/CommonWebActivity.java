package com.dangbei.moonlight.framework.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.dangbei.moonlight.BuildConfig;
import com.dangbei.moonlight.R;
import com.dangbei.moonlight.util.ImageUtil;
import com.dangbei.palaemon.axis.Axis;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CommonWebActivity extends Activity {

    private RelativeLayout rootView;
    private WebView webView;

    private String url;
    //private String newUrl = "";

    public static void run(Context context, String url) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);
        rootView = findViewById(R.id.activity_loading_root);
        rootView.setBackground(ImageUtil.getGradientDrawable(Axis.scaleX(20),
                GradientDrawable.Orientation.TOP_BOTTOM, 0xff000619, 0xff014B9D));
        url = getIntent().getStringExtra("url");
        //if (!TextUtils.isEmpty(url)) {
        //    newUrl = transParamsUrl(url);
        //}
        //if(AppManage.isTestEnv()){
        //    newUrl = changeUrl(newUrl, Urls.TEST_HOST_URL, Urls.HOST_URL, Urls.getHostUrl());
        //}else {
        //    newUrl = changeUrl(newUrl, Urls.HOST_URL, Urls.TEST_HOST_URL, Urls.getHostUrl());
        //}
        webView = findViewById(R.id.activity_common_web_view);
        webView.setBackgroundColor(0x00000000);
        initWebView();
    }

    private void initWebView() {
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setAllowFileAccess(true);

        //解决方案一，捕获异常
        try {
            webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        } catch (Throwable e) {
            e.printStackTrace();
            return;

        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webSettings.setDomStorageEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setMinimumFontSize(1);
        webSettings.setMinimumLogicalFontSize(1);
        //其他细节操作
        webSettings.setDatabaseEnabled(true);
        //webSettings.setAppCacheEnabled(true);//取消应用缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDomStorageEnabled(true);
        webSettings.setTextZoom(100);
        webSettings.setDefaultFontSize(Axis.scaleTextSize(20));
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                showLoadingDialog("");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                cancelLoadingDialog();

                try {
                    if(url.contains("dbapinew/yinsi.php") && webView != null){
                        webView.evaluateJavascript("javascript:document.body.style.setProperty('color', '#FFFFFF');", null);
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(CommonWebActivity.this, "啊哦~页面被外星人劫持了", Toast.LENGTH_LONG).show();
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                } else {
                    view.loadUrl(request.toString());
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (handler != null) {
                    handler.proceed();
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.setBackgroundResource(android.R.color.transparent);
        webSettings.setNeedInitialFocus(true);

        webView.loadUrl(url);
        //LogUtil.d("db_sony", newUrl);
    }

    private static String changeUrl(String originUrl, String currentHost, String badHost, String changeHost) {
        String url = originUrl;
        if(url.contains(badHost)){
            url = url.replace(badHost, currentHost);
        }
        if(!TextUtils.isEmpty(changeHost) && !currentHost.equals(changeHost)) {
            if(url.contains(currentHost)){
                url = url.replace(currentHost, changeHost);
            }else {
                if(url.startsWith("https")){
                    String u = url.substring(8);
                    u = u.substring(u.indexOf("/"));
                    url = "https://" + changeHost+u;
                }else {
                    String u = url.substring(7);
                    u = u.substring(u.indexOf("/"));
                    url = "http://" + changeHost+u;
                }
            }
        }
        return url;
    }

    //private String transParamsUrl(String url) {
    //    Map<String, String> params = new HashMap<>();
    //    params.put("chanel", AppManage.getAppChannel());
    //    params.put("model", Tool.getSonyModel().replace(" ", ""));
    //    params.put("vcode", Tool.getClientVersionCode(getApplicationContext()) + "");
    //    params.put("vname", Tool.getClientVersionName(getApplicationContext()));
    //    params.put("sdkinfo", Tool.getVersionRelease());
    //    params.put("sdkcode", Tool.getSDKRelease() + "");
    //    params.put("packagename", getPackageName());
    //    params.put("system", Tool.getTVLauncherType(getApplicationContext()));
    //    params.put("licence", Tool.getLicenseTypeNew(getApplicationContext()));
    //    params.put("device_model", Tool.getDeviceAndModel());
    //    StringBuilder mStringBuilder;
    //    if(url.contains("?")){
    //        mStringBuilder = new StringBuilder(url);
    //    }else {
    //        mStringBuilder = new StringBuilder(url).append("?");
    //    }
    //    for (String key : params.keySet()) {
    //        if (!mStringBuilder.toString().isEmpty()
    //                && !mStringBuilder.toString().endsWith("?")) {
    //            mStringBuilder.append("&");
    //        }
    //        try {
    //            mStringBuilder.append(key);
    //            mStringBuilder.append("=");
    //            mStringBuilder.append(URLEncoder.encode(params.get(key), "UTF-8"));
    //        }catch (Exception e){
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    return mStringBuilder.toString();
    //}

    @SuppressLint("RestrictedApi")
    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                case KeyEvent.KEYCODE_ESCAPE:
                    onBackPressed();
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setWebViewClient(null);
            webView.setWebChromeClient(null);
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }
}
