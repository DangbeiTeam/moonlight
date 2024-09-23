package com.dangbei.healingspace.provider.dal.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.dangbei.xlog.XLog;

import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：lhb on 2019/8/22 16:05
 * Mail：lihaibo@znds.com
 */
public class VideoAuthUtil {
    private final static String KEY = "101193d7181cc88340ae5b2b17bba8a1";


    public static String privateKeyA(String baseUrl, long activeTimeSec) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl);

        //获取完整的域名
        Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(baseUrl);
        matcher.find();
        int filePathStartIndex = matcher.end();
        String fileName = baseUrl.substring(filePathStartIndex);
        // add "?"
        if (!baseUrl.contains("?")) {
            stringBuilder.append("?");
        }
        // add key == "auth_key"
        stringBuilder.append("auth_key=");
        String timeStamp = "" + (System.currentTimeMillis() / 1000 + activeTimeSec);
        // add timestamp
        stringBuilder.append(timeStamp);
        //
        stringBuilder.append("-");
        String random = MD5Util.md5(UUID.randomUUID().toString());
        // add random
        stringBuilder.append(random);
        //
        stringBuilder.append("-");
        // add uid
        String uid = "uid";
        stringBuilder.append(uid);
        //
        stringBuilder.append("-");
        // add md5Hash
        String authValue = MD5Util.md5(fileName + "-" + timeStamp + "-" + random + "-" + uid + "-" + KEY);
        stringBuilder.append(authValue);
//        XLog.d("lhb", "auth url =" + stringBuilder.toString());
        return stringBuilder.toString();
    }


    public static String genterateVideoFileKey(String url) {
        //获取完整的域名
        String baseUrl = null;
        if (url.contains("?auth_key")) {
            baseUrl = url.substring(0, url.indexOf("?auth_key"));
        } else {
            baseUrl = url;
            return baseUrl;
        }
        XLog.d("VideoAuthUtil", "genterateVideoFileKey - baseUrl = " + baseUrl);
        Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(baseUrl);
        matcher.find();
        int filePathStartIndex = matcher.end();
        String fileName = baseUrl.substring(filePathStartIndex);
        // /pingbao/20191017/24515.mp4  b700283bac63c0c7eaccafade368144d
        XLog.d("VideoAuthUtil", "genterateVideoFileKey - filename = " + fileName);
        XLog.d("VideoAuthUtil", "genterateVideoFileKey - md5 = " + MD5Util.md5(fileName));
        return MD5Util.md5(fileName);
    }

    /**
     * MediaStore.Images.Thumbnails.MINI_KIND));
     *
     * @param filePath
     * @param kind
     * @return
     */
    public static Bitmap createVideoThumbnail(String filePath, int kind) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (filePath.startsWith("http://")
                    || filePath.startsWith("https://")
                    || filePath.startsWith("widevine://")) {
                retriever.setDataSource(filePath, new Hashtable<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime(-1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC); //retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }catch (IOException e){

            }
        }
        if (bitmap == null) {
            return null;
        }
        if (kind == MediaStore.Images.Thumbnails.MINI_KIND) {//压缩图片 开始处
            // Scale down the bitmap if it's too large.
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int max = Math.max(width, height);
            if (max > 512) {
                float scale = 512f / max;
                int w = Math.round(scale * width);
                int h = Math.round(scale * height);
                bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
            }//压缩图片 结束处
        } else if (kind == MediaStore.Images.Thumbnails.MICRO_KIND) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,
                    960,
                    540,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }



    public static Bitmap drawableToBitmap(Drawable drawable) {

        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();

        drawable.setBounds(0, 0, width, heigh);

        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

}
