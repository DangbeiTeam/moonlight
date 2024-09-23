package com.dangbei.moonlight.framework.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.dangbei.gonzalez.view.GonTextView;
import com.dangbei.moonlight.R;
import com.dangbei.moonlight.framework.activity.CommonWebActivity;
import com.dangbei.moonlight.util.DrawableUtils;
import com.dangbei.moonlight.util.MMKVSpUtil;
import com.dangbei.palaemon.axis.Axis;
import com.lerad.lerad_base_viewer.base.BaseDialog;

/**
 * author : zhengxihong  e-mail : tomatozheng0212@gmail.com   time  : 2024/08/19
 */
public class UserAgreementDialog extends AlertDialog
        implements View.OnClickListener, View.OnFocusChangeListener {
    public static final String User_ULR = "http://sonyyktestapi.v5tv.com/moon/h5/protocol.html";
    public static final String ADVANCE_ULR = "https://sonyyktestapi.v5tv.com/moon/h5/advance.html";
    private TextView btnAgree, btnDisagree;
    private RelativeLayout rootView;
    private GonTextView agreement_title;
    private GonTextView textViewContent;

    private AgreementDialogListener mAgreementDialogListener;

    public UserAgreementDialog(Context context) {
        super(context, R.style.CustomDialogWithoutTitle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_user_agreement);
        setCancelable(false);
        initView();
    }

    private void initView() {
        rootView = findViewById(R.id.dialog_user_agreement_root);
        rootView.setBackground(DrawableUtils.getGradientDrawable(0xff1e1e1e, Axis.scale(18)));
        btnAgree = findViewById(R.id.dialog_agreement_agree);
        agreement_title = findViewById(R.id.dialog_agreement_title);
        btnDisagree = findViewById(R.id.dialog_agreement_disagree);
        textViewContent = findViewById(R.id.dialog_agreement_msg);
        textViewContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        textViewContent.setScrollBarSize(Axis.scaleX(12));
        findViewById(R.id.dialog_user_agreement_one_url_tv).setOnClickListener(this);
        findViewById(R.id.dialog_user_agreement_two_url_tv).setOnClickListener(this);
        btnAgree.setOnClickListener(this);
        btnDisagree.setOnClickListener(this);
        initMsg();
        btnAgree.requestFocus();
    }

    int a = 0;

    private void initMsg() {
        String title = MMKVSpUtil.getString(MMKVSpUtil.SP_NAME,
                MMKVSpUtil.SpKey.USER_AGREEMENT_TITLE.key,
                getContext().getResources().getString(R.string.agreement_title));

        agreement_title.setText(title);

        String content = MMKVSpUtil.getString(MMKVSpUtil.SP_NAME,
                MMKVSpUtil.SpKey.USER_AGREEMENT_CONTENT.key, "");
        textViewContent.setLineSpacing(Axis.scaleY(6), 1f);
        if (TextUtils.isEmpty(content)) {
            content = getContext().getResources().getString(R.string.agreement_content_weilai);
        }
        textViewContent.setText(content);
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                case KeyEvent.KEYCODE_ESCAPE:
                    if (btnAgree.isFocused()) {
                        if (mAgreementDialogListener != null) {
                            mAgreementDialogListener.onDisAgree();
                        }
                        dismiss();
                        return true;
                    }
                    btnAgree.requestFocus();
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_user_agreement_one_url_tv) {
            CommonWebActivity.run(getContext(),
                    MMKVSpUtil.getString(MMKVSpUtil.SP_NAME,
                            MMKVSpUtil.SpKey.USER_URL.key, User_ULR));
        } else if (v.getId() == R.id.dialog_user_agreement_two_url_tv) {
            CommonWebActivity.run(getContext(),
                    MMKVSpUtil.getString(MMKVSpUtil.SP_NAME,
                            MMKVSpUtil.SpKey.ADVANCE_ULR_URL.key, ADVANCE_ULR));
        } else if (v == btnAgree) {
            if (mAgreementDialogListener != null) {
                mAgreementDialogListener.onAgree();
                dismiss();
            }
        } else if (v == btnDisagree) {
            if (mAgreementDialogListener != null) {

                mAgreementDialogListener.onDisAgree();
                dismiss();
            }
        }
    }

    public UserAgreementDialog setOnAgreementDialogListener(
            AgreementDialogListener agreementDialogListener) {
        mAgreementDialogListener = agreementDialogListener;
        return this;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    public interface AgreementDialogListener {
        void onAgree();

        void onDisAgree();
    }
}
