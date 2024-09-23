package com.dangbei.healingspace.provider.bll.application.info;

import androidx.annotation.NonNull;

/**
 * http://www.jianshu.com/p/4eb3e6d07b78
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public final class DesHelper {

    public static final String SECRET = "dmdPP3dl";

    private String secretKey;
    private byte[] iv;

    private static DesHelper desHelper;

    public static DesHelper getInstance() {
        if (desHelper == null) {
            desHelper = new DesHelper(SECRET, SECRET.getBytes());
        }
        return desHelper;
    }

    private DesHelper(@NonNull String secretKey, @NonNull byte[] iv) {
        this.secretKey = secretKey;
        this.iv = iv;
    }

    public String encode(String plainText) throws Exception {
        return DesUtil.encode(secretKey, iv, plainText);
    }

    public String decode(String encryptText) throws Exception {
        return DesUtil.decode(secretKey, iv, encryptText);
    }

    public byte[] decode(byte[] encryptText) throws Exception {
        return DesUtil.decode(secretKey, iv, encryptText);
    }

}
