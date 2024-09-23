package com.dangbei.healingspace.provider.bll.application.configuration;

public class Config {
    public static class Main {
        /**
         * 默认播放时间间隔
         */
        public static final int DEFAULT_PLAYER_PERIORD = 22 * 1000;
    }

    public static class CustomMain {
        public static final int NUM_COLUMNS = 6;

        public static final int TYPE_ITEM_USER_NO_SELECT = 10;
        public static final int TYPE_ITEM_USER_SELECT = 11;


        public static final int TYPE_ITEM_FIXED_DEFAULT = 20;
        public static final int TYPE_ITEM_FIXED_FAST = 21;
        public static final int TYPE_ITEM_FIXED_USB = 22;
        public static final int TYPE_ITEM_FIXED_WX = 4;


        public static final int TYPE_ITEM_USER = 1;
        public static final int TYPE_ITEM_FIXED = 2;

        /**
         * 确定设置自定义屏保功能
         */
        public static final int TYPE_ITEM_VERIFY_SET_CUSTOMIZE = 3;
    }
    public static final class UsbReceiver{
        //有设备;
        public static final int DEVICES = 1;
        // 无设备;
        public static final int NO_DEVICES = 2;
    }

    public static final class Wx {

        /**
         * 上传 的验证码
         */
        public static final String WX_VERIFICATION_CODE_INFO = "WX_VERIFICATION_CODE_INFO";
        public static final String KEY_SCREEN_SAVER_GLOBAL_PREFS = "screen_saver_global_prefs";
    }

    public static final class Fast {
        public static final int HTTP_PORT = 5500;
        /**
         * 端口被占用
         */
        public static final int ERR_PORT_IN_USE = 0x0102;
        /**
         * 成功启动文件快传服务
         */
        public static final int SUCCESS_SERVER = 0x0101;
        /**
         * 成功接收上传文件
         */
        public static final int SUCCESSFULLY_RECEIVED_UPLOAD_FILE = 0x0103;

        /**
         * 文件快传中 删除指定的路径
         */
        public static final int DELECT_RECEIVED_UPLOAD_FILE = 0x0104;

    }
}
