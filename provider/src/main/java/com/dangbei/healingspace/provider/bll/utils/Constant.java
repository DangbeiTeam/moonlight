package com.dangbei.healingspace.provider.bll.utils;

import android.os.Environment;

/**
 *
 * @Description:
 */
public class Constant {


    public interface SP {
        /**
         * 是否是第一次启动
         */
        String isFirst = "isFirst";
    }

    public interface DB {
        String DB_SYMBOL = "com.dangbei.moonlight";
    }

    /**
     * 下载的文件总目录
     * 目前只包含video的
     * 在d1上面的路径大概是这个样子
     * /storage/emulated/0/screenSave/0_7
     */
    public static String DOWNLOAD_FOLDER = Environment.getExternalStorageDirectory() + "/moonlight" + "/";
    /**
     * 自定义cover路径
     */
    public static String DOWNLOAD_COVER_CUSTOM_FOLDER = DOWNLOAD_FOLDER + "coverCustom" + "/";

}
