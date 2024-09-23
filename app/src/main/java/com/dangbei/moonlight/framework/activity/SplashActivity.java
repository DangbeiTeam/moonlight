package com.dangbei.moonlight.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import com.dangbei.healingspace.provider.dal.net.http.XRequestCreator;
import com.dangbei.healingspace.provider.dal.net.http.webapi.WebApiConstants;
import com.dangbei.moonlight.LauncherApplication;
import com.dangbei.moonlight.R;
import com.dangbei.moonlight.framework.bean.ConfigBeanResponse;
import com.dangbei.moonlight.framework.dialog.UserAgreementDialog;
import com.dangbei.moonlight.util.MMKVSpUtil;
import com.dangbei.xlog.XLog;
import com.lerad.lerad_base_support.bridge.compat.RxCompat;
import com.lerad.lerad_base_support.bridge.compat.RxCompatObserver;
import com.lerad.lerad_base_support.bridge.compat.subscriber.RxCompatException;
import com.limelight.PcView;
import com.wangjiegulu.dal.request.XHttpManager;
import io.reactivex.disposables.Disposable;

import static com.lerad.lerad_base_support.interactor.BaseInteractor.checkResponseDefault;

/**
 * author : zhengxihong  e-mail : tomatozheng0212@gmail.com   time  : 2024/08/19
 */
public class SplashActivity extends Activity {
    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_splash);
        initDialog();
    }

    private void initDialog() {
        int versionCode =
                MMKVSpUtil.getInt(MMKVSpUtil.SP_NAME, MMKVSpUtil.SpKey.APGREE_VERSION.key, 1);
        boolean aBoolean =
                MMKVSpUtil.getBoolean(MMKVSpUtil.SP_NAME,
                        MMKVSpUtil.SpKey.AGREEN_PRIV.key + versionCode, false);
        if (aBoolean) {
            enterToMain();
        } else {
            UserAgreementDialog userAgreementDialog = new UserAgreementDialog(this);
            userAgreementDialog.setOnAgreementDialogListener(
                    new UserAgreementDialog.AgreementDialogListener() {
                        @Override public void onAgree() {
                            MMKVSpUtil.putBoolean(MMKVSpUtil.SP_NAME,
                                    MMKVSpUtil.SpKey.AGREEN_PRIV.key, true);
                            enterToMain();
                        }

                        @Override public void onDisAgree() {
                            finish();
                        }
                    });
            userAgreementDialog.show();
        }

        XRequestCreator.getInstance()
                .createRequest("https://sonyyktestapi.v5tv.com/moon/index/config")
                .get()
                .observable(ConfigBeanResponse.class)
                .compose(RxCompat.subscribeOnNet())
                .subscribe(new RxCompatObserver<>() {
                    @Override
                    public void onSubscribeCompat(Disposable d) {
                        // ignore
                    }

                    @Override
                    public void onNextCompat(ConfigBeanResponse configBeanResponse) {
                        Log.i("zxh", "ConfigBeanResponse " + configBeanResponse);
                        if (configBeanResponse.getData().getProtocolVerCode() > versionCode) {
                            MMKVSpUtil.putInt(MMKVSpUtil.SP_NAME,
                                    MMKVSpUtil.SpKey.APGREE_VERSION.key, 1);
                        }
                        MMKVSpUtil.putString(MMKVSpUtil.SP_NAME,
                                MMKVSpUtil.SpKey.USER_URL.key,
                                configBeanResponse.getData().getProtocol());
                        MMKVSpUtil.putString(MMKVSpUtil.SP_NAME,
                                MMKVSpUtil.SpKey.ADVANCE_ULR_URL.key,
                                configBeanResponse.getData().getAdvance());
                        MMKVSpUtil.putString(MMKVSpUtil.SP_NAME,
                                MMKVSpUtil.SpKey.USER_AGREEMENT_TITLE.key,
                                configBeanResponse.getData().getTitle());
                        MMKVSpUtil.putString(MMKVSpUtil.SP_NAME,
                                MMKVSpUtil.SpKey.USER_AGREEMENT_CONTENT.key,
                                configBeanResponse.getData().getContent());
                    }

                    @Override public void onErrorCompat(RxCompatException compatThrowable) {
                        super.onErrorCompat(compatThrowable);
                        XLog.i("zxh", "onErrorCompat " + compatThrowable);
                    }
                });
    }

    @Override protected void onStart() {
        XLog.i(TAG, "onStart");

        super.onStart();
    }

    @Override protected void onResume() {
        XLog.i(TAG, "onResume");

        super.onResume();
    }

    public void enterToMain() {
        XLog.i(TAG, "enterToMain");

        startActivity(new Intent(this, PcView.class));
        finish();
    }
}
