package com.dangbei.moonlight.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.dangbei.moonlight.framework.view.agent.SingletonRoundedAgent;
import com.dangbei.moonlight.framework.view.agent.SingletonShadowAgent;
import com.dangbei.moonlight.R;
import com.dangbei.palaemon.view.DBButton;
import com.lerad.lerad_base_view.ext.fuctionclick.ClickDelegateImpl;
import com.lerad.lerad_base_view.ext.fuctionclick.OnUpClickListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 03/08/2017.
 */
public class XButton extends DBButton implements IRoundedCapability, IShadowCapability {
    private ClickDelegateImpl clickDelegate;


    public XButton(Context context) {
        this(context, null);
    }

    public XButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }



    private void initAttr(Context context, AttributeSet attributeSet) {
        try {
            TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.common_params);
            // round
            boolean isRound = ta.getBoolean(R.styleable.common_params_enableRound, false);
            int roundRadius = ta.getInt(R.styleable.common_params_roundRadius, DEFAULT_ROUND_CORNER);
            if (isRound) {
                roundCorner(roundRadius);
            }
            // shadow
            boolean isShadow = ta.getBoolean(R.styleable.common_params_enableShadow, true);
            float shadow = ta.getFloat(R.styleable.common_params_shadow, DEFAULT_SHADOW);
            if (isShadow) {
                setShadow(shadow);
            }

            ta.recycle();
        } catch (Exception e) {
            //ignore
        }
    }


    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
    }


    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        return (null != clickDelegate && clickDelegate.dispatchKeyEventPreIme(event))
                || super.dispatchKeyEventPreIme(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return (null != clickDelegate && clickDelegate.dispatchTouchEvent(ev))
                || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        createDelegate();
        clickDelegate.setOnLongClickListener(l);
        super.setOnLongClickListener(v -> l != null && l.onLongClick(v));
    }


    public void setOnUpClickListener(OnUpClickListener l) {
        createDelegate();
        clickDelegate.setOnUpClickListener(l);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        createDelegate();
        clickDelegate.setOnClickListener(l);
        //点击水波纹，需要设置该属性 (设置该属性是为了 触发 ontouch的 up事件,从而支持鼠标点击)
        super.setOnClickListener(v -> {
        });
    }

    private void createDelegate() {
        if (null == clickDelegate) {
            clickDelegate = new ClickDelegateImpl(this);
        }
    }

    private int roundPx;

    @Override
    public void roundCorner(int px) {
        roundPx = px;
        SingletonRoundedAgent.roundCorner(this, px);
    }

    @Override
    public void roundCorner() {
        roundCorner(DEFAULT_ROUND_CORNER);
    }


    @Override
    public int getRoundRadius() {
        return roundPx;
    }

    @Override
    public void setShadow(float shadow) {
        SingletonShadowAgent.shadow(this, shadow);
    }

    @Override
    public void setShadow() {
        setShadow(DEFAULT_SHADOW);
    }


    //    @Override
//    public void setTranslationX(float translationX) {
//        float x = GonScreenAdapter.getInstance().scaleX((int) Math.abs(translationX));
//        super.setTranslationX(translationX < 0 ? -x : x);
//    }
//
//    @Override
//    public void setTranslationY(float translationY) {
//        float x = GonScreenAdapter.getInstance().scaleY((int) Math.abs(translationY));
//        super.setTranslationY(translationY < 0 ? -x : x);
//    }
}
