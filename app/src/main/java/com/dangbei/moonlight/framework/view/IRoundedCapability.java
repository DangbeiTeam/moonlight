package com.dangbei.moonlight.framework.view;

import com.lerad.lerad_base_util.ResUtil;

/**
 * 圓角能力
 */
public interface IRoundedCapability {
    /**
     * 默认
     */
    int DEFAULT_ROUND_CORNER = Math.min(ResUtil.px2GonY(20), ResUtil.px2GonX(20));

    void roundCorner(int px);

    void roundCorner();

    int getRoundRadius();
}
