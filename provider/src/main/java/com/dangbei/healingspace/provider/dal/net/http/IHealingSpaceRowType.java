package com.dangbei.healingspace.provider.dal.net.http;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 定义治愈空间的类型
 */
@StringDef({
        IHealingSpaceRowType.ART_PIC,
        IHealingSpaceRowType.ART_PIC_FRAME,
        IHealingSpaceRowType.ART_PIC_FRAME_SIZE,
        IHealingSpaceRowType.LIVE,
        IHealingSpaceRowType.MUSIC,
        IHealingSpaceRowType.MUSIC_ALBUM,
        IHealingSpaceRowType.MUSIC_LYRIC_EFFECT,
        IHealingSpaceRowType.UNKNOWN
})
@Retention(RetentionPolicy.SOURCE)
public @interface IHealingSpaceRowType {

    String UNKNOWN = "0"; // 未知
    // ------------------ music ---------------------------
    String MUSIC = "1"; // 音乐--当贝酷狗音乐
    String MUSIC_ALBUM = "11"; // 音乐--当贝酷狗音乐
    String MUSIC_LYRIC_EFFECT = "1"; // 音乐--当贝酷狗音乐

    // ------------------ art pic----------------------------
    String ART_PIC = "2"; // 艺术图片
    String ART_PIC_FRAME = "21"; // 艺术图片
    String ART_PIC_FRAME_SIZE = "22"; // 艺术图片

    // ------------------ live ----------------------------
    String LIVE = "3"; // 生活
}
