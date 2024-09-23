package com.dangbei.healingspace.provider.dal.util;

import com.dangbei.xlog.XLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/5/16.
 */
public final class MD5Util {
    private MD5Util() {
    }

    private static final String TAG = MD5Util.class.getSimpleName();

    public static String md5(String str) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append('0');
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();  //md5 32bit
            // result = buf.toString().substring(8, 24))); //md5 16bit
        } catch (NoSuchAlgorithmException e) {
            XLog.e(TAG, "MD5加密失败, ", e);
        }
        return result;
    }

    /**
     * RandomAccessFile 获取文件的MD5值
     *
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMd5(File file) {
        MessageDigest messageDigest;
        RandomAccessFile randomAccessFile = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile=new RandomAccessFile(file,"r");
            byte[] bytes=new byte[1024*1024*10];
            int len=0;
            while ((len=randomAccessFile.read(bytes))!=-1){
                messageDigest.update(bytes,0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            String md5 = bigInt.toString(16);
            while (md5.length() < 32) {
                md5 = "0" + md5;
            }
            return md5;
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String getFileMd52(File file) {

        try {
            // md5
            MessageDigest digest = MessageDigest.getInstance("md5");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 10];
            int len = -1;
            while ((len = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] result = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getFileMd52(InputStream inputStream) {

        try {
            // md5
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] buffer = new byte[1024 * 10];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] result = digest.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
