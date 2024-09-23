package com.dangbei.healingspace.provider.bll.application.info;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public final class DesUtil {
    public static String encode(String secretKey, byte[] iv, String plainText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(plainText.getBytes());
        return Base64.encodeToString(encryptedData, Base64.NO_WRAP);
    }

    public static String decode(String secretKey, byte[] iv, String encryptText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.NO_WRAP));
        return new String(decryptData);
    }

    public static byte[] decode(String secretKey, byte[] iv, byte[] encryptText) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.NO_WRAP));
        return decryptData;
    }
}
