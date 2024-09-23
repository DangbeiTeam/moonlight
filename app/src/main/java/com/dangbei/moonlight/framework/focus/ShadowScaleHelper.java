package com.dangbei.moonlight.framework.focus;

import com.dangbei.leanback.component.widget.FocusHighlight;
import com.dangbei.leanback.component.widget.FocusHighlightHandler;
import com.dangbei.xfunc.usage.XLazy;

/**
 * Created by $xuzhili on 2018/7/5.
 */

public final class ShadowScaleHelper {
    public static final XLazy<FocusShadowHelper> ZOOM_XLARGE_CENTER_TO_CENTER_NO_SHADOW = new XLazy<>(
            () -> new FocusShadowHelper()
                    .setUseDimmer(false)
                    .setDisableShadow(true)
                    .setScaleType(FocusHighlightHandler.SCALE_CENTER_TO_CENTER)
                    .setZoomFactor(FocusHighlight.ZOOM_FACTOR_XLARGE)
    );

    /**
     * 自定义放大倍率。外部可设置
     */
    public static final XLazy<FocusShadowHelper> ZOOM_DEFINED_CENTER_TO_CENTER_NO_SHADOW = new XLazy<>(
            () -> new FocusShadowHelper()
                    .setUseDimmer(false)
                    .setDisableShadow(true)
                    .setScaleType(FocusHighlightHandler.SCALE_CENTER_TO_CENTER)
                    .setZoomFactor(FocusHighlight.ZOOM_FACTOR_DEFINED)
    );

    /**
     * 自定义放大倍率。外部可设置
     */
    public static final XLazy<FocusShadowHelper> ZOOM_DEFINED_CENTER_TO_CENTER = new XLazy<>(
            () -> new FocusShadowHelper()
                    .setUseDimmer(false)
                    .setScaleType(FocusHighlightHandler.SCALE_CENTER_TO_CENTER)
                    .setZoomFactor(FocusHighlight.ZOOM_FACTOR_DEFINED)
    );

}
