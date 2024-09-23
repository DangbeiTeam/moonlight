package com.dangbei.healingspace.provider.dal.net.http;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 定义治愈空间的类型
 */
@IntDef({
        IHealingSpaceType.ART_PIC,
        IHealingSpaceType.LO_FI,
        IHealingSpaceType.INFO_WEATHER,
        IHealingSpaceType.LIVE,
        IHealingSpaceType.MUSIC,
        IHealingSpaceType.UNKNOWN
})
@Retention(RetentionPolicy.SOURCE)
public @interface IHealingSpaceType {

    int UNKNOWN = 0; // 未知
    int MUSIC = 1; // 音乐--当贝酷狗音乐
    int ART_PIC = 2; // 艺术图片
    int LIVE = 3; // 生活
    int INFO_WEATHER = 4; // 资讯--天气
    int LO_FI = 5; // lo-fi白噪音
}
