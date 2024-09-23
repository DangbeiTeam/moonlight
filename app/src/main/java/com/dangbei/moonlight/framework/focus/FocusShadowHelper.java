package com.dangbei.moonlight.framework.focus;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import com.dangbei.leanback.component.widget.FocusHighlight;
import com.dangbei.leanback.component.widget.FocusHighlightHandler;
import com.dangbei.leanback.component.widget.FocusHighlightHelper;

/**
 * Created by tangzhiyuan on 2018/6/30.
 * 给view 获取焦点时设置阴影效果
 * <p>
 * how to use:
 * <p>
 * after initializing your view
 * call setViewShdowEffect(View view)
 * and then don't forget to call setViewFocused(View view, boolean isFocus)
 * in the method onFocusChange(View v, boolean hasFocus)
 * <p>
 * here below is the sample:
 * <p>
 * focusShadowHelper = new FocusShadowHelper.Builder().setZoomFacotor(FocusHighlight.ZOOM_FACTOR_XSMALL).build();
 * focusShadowHelper.initViewFocusedOption(view);
 *
 * @Override public void onFocusChange(View v, boolean hasFocus) {
 * focusShadowHelper.setViewFocused(v,hasFocus);
 * }
 */

public class FocusShadowHelper {

//    public static final ShadowOverlayHelper SHADOW_INSTANCE = new ShadowOverlayHelper.Builder()
//            .needsShadow(true)
//            .needsRoundedCorner(true)
//            .build(LauncherApplication.instance);
//
//    public static final ShadowOverlayHelper ROUND_RECT_INSTANCE = new ShadowOverlayHelper.Builder()
//            .needsRoundedCorner(true)
//            .build(LauncherApplication.instance);

    private final int DEFAULT_ANIMA_TIME = 200; // 默认动画时间
    private int zoomFactor = FocusHighlight.ZOOM_FACTOR_MEDIUM;
    private float zoomRatio = 1.0f;
    private int scaleType = FocusHighlightHandler.SCALE_CENTER_TO_CENTER;
    private boolean useDimmer;
    private boolean disableShadow;
    private int animaTime = DEFAULT_ANIMA_TIME;
    private FocusHighlightHelper.BrowseItemFocusHighlight highlight;
    /*默认差值器*/
    private Interpolator mInterpolator = new LinearInterpolator();

    public FocusShadowHelper() {

    }

//    /**
//     * 初始化放大VIew 缩放起点 从左往右放大时必须调用
//     *
//     * @param view
//     */
//    public void initViewFocusedOption(View view) {
//        if (!disableShadow) {
//            SHADOW_INSTANCE.onViewCreated(view);
//        }
//        if (scaleType == FocusHighlightHandler.SCALE_LEFT_TO_RIGHT) {
//            view.setTag(R.id.id_view_focused_option, FocusHighlightHandler.SCALE_LEFT_TO_RIGHT);
//        }
//    }

    @SuppressLint("RestrictedApi")
    public void setViewFocused(View view, boolean isFocus) {
        getFocusHighlightHandler()
                .onItemFocused(view, isFocus);
    }

    public FocusShadowHelper setZoomFactor(int zoomFactor) {
        this.zoomFactor = zoomFactor;
        return this;
    }

    public FocusShadowHelper setZoomRatio(float zoomRatio) {
        this.zoomRatio = zoomRatio;
        return this;
    }

    public FocusShadowHelper setUseDimmer(boolean useDimmer) {
        this.useDimmer = useDimmer;
        return this;
    }

    public FocusShadowHelper setDisableShadow(boolean disableShadow) {
        this.disableShadow = disableShadow;
        return this;
    }

    public FocusShadowHelper setScaleType(int scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public FocusShadowHelper setAnimaTime(int animaTime) {
        this.animaTime = animaTime;
        return this;
    }


    private FocusHighlightHandler getFocusHighlightHandler() {
        if (highlight == null || zoomRatio != highlight.getmScaleRatio()) {
            highlight = new FocusHighlightHelper.BrowseItemFocusHighlight(
                    zoomFactor,
                    useDimmer,
                    zoomRatio,
                    mInterpolator,
                    animaTime);
        }
        return highlight;
    }

    public FocusShadowHelper setInterpolator(@NonNull Interpolator interpolator) {
        this.mInterpolator = interpolator;
        return this;
    }

}
