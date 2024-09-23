package com.dangbei.moonlight.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.Nullable;

import com.dangbei.moonlight.R;
import com.dangbei.moonlight.framework.view.agent.SingletonRoundedAgent;
import com.dangbei.moonlight.framework.view.agent.SingletonShadowAgent;
import com.dangbei.palaemon.layout.DBRelativeLayout;
import com.lerad.lerad_base_view.ext.fuctionclick.ClickDelegateImpl;
import com.lerad.lerad_base_view.ext.fuctionclick.OnUpClickListener;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 03/08/2017.
 */
public class XRelativeLayout extends DBRelativeLayout implements IRoundedCapability,IShadowCapability {
    public static final String TAG = XRelativeLayout.class.getSimpleName();

    protected View lasFocusedView;
    private boolean saveFocused;
    private ClickDelegateImpl clickDelegate;
    private OnClickListener listener;
    /**
     * 点击后用于放大的view
     */
    private View scaleView;

    public XRelativeLayout(Context context) {
        this(context, null);
    }

    public XRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
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
            boolean isShadow = ta.getBoolean(R.styleable.common_params_enableShadow, false);
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
    public void setShadow(float shadow) {
        SingletonShadowAgent.shadow(this, shadow);
    }

    @Override
    public void setShadow() {
        setShadow(DEFAULT_SHADOW);
    }

    private void init() {
        setClipChildren(false);
        setClipToPadding(false);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    @Override
    public void setFocusable(boolean focusable) {
        super.setFocusable(focusable);
    }

    public void setSaveFocused(boolean saveFocused) {
        this.saveFocused = saveFocused;
        setFocusable(saveFocused);
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    public void setLasFocusedView(View lasFocusedView) {
        this.lasFocusedView = lasFocusedView;
    }


    public void mergeView(int resId) {
        inflate(getContext(), resId, this);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        View view = super.focusSearch(focused, direction);
        if (saveFocused && !isChildView(view)) {
            lasFocusedView = focused;
            setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }
        return view;
    }

    protected boolean isChildView(View view) {
        if (view == null) {
            return false;
        }
        while (view.getParent() != null) {
            ViewParent parent = view.getParent();
            if (parent == this) {
                return true;
            } else if (parent instanceof View) {
                view = (View) parent;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (saveFocused && lasFocusedView != null) {
            setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        }
        return (saveFocused && lasFocusedView != null && lasFocusedView.requestFocus())
                || super.requestFocus(direction, previouslyFocusedRect);
    }

    public void setScaleView(View view) {
        if (null != clickDelegate) {
            clickDelegate.setScaleView(view);
        } else {
            scaleView = view;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev == null) {
            return false;
        }
        return (null != clickDelegate && clickDelegate.dispatchTouchEvent(ev))
                || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        return (null != clickDelegate && clickDelegate.dispatchKeyEventPreIme(event))
                || super.dispatchKeyEventPreIme(event);
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
        this.listener = l;
        //点击水波纹，需要设置该属性(设置该属性是为了 触发 ontouch的 up事件,从而支持鼠标点击)
        super.setOnClickListener(v -> {
        });
    }


    private void createDelegate() {
        if (null == clickDelegate) {
            clickDelegate = new ClickDelegateImpl(this);
            if (null != scaleView) {
                clickDelegate.setScaleView(scaleView);
            }
        }
    }

    public void setScaleDisable(boolean scaleDisable) {
        createDelegate();
        clickDelegate.setScaleDisable(scaleDisable);
    }

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

    int roundPx;

//    @Override
//    public void setTranslationX(float translationX) {
//        float x = GonScreenAdapter.getInstance().scaleX((int) translationX);
//        super.setTranslationX(x);
//    }
//
//    @Override
//    public void setTranslationY(float translationY) {
//        float x = GonScreenAdapter.getInstance().scaleY((int) translationY);
//        super.setTranslationY(x);
//    }
}
