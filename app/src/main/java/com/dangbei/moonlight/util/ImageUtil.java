package com.dangbei.moonlight.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

public class ImageUtil {


    /**
     * bitmap 保存到本地文件
     *
     * @param bmp
     * @param savePath
     * @return
     */
    public static String BitmapToFile(Bitmap bmp, String savePath) {

        String path = null;
        try {
            File f = new File(savePath);
            if (f.exists())
                f.delete();

            f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();

            path = f.getAbsolutePath();
        } catch (Exception e) {
        } finally {
            if (bmp != null) {
                bmp.recycle();
            }
        }
        return path;
    }


    /**
     * Bitmap → Drawable
     */
    public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
        @SuppressWarnings("deprecation")
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        // 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
        return bd;
    }

    /**
     * 获取圆形图片
     *
     * @param img
     * @return
     */
    public static Bitmap getRoundBitmap(Bitmap img) {

        if (img == null)
            return null;

        try {
            int min = Math.min(img.getHeight(), img.getWidth());
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Bitmap target = Bitmap.createBitmap(min, min,
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(target);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(0xff424242);
            canvas.drawCircle(min / 2, min / 2, min / 2 - 2, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(img, 0, 0, paint);

            return target;
        } catch (Exception e) {
            return img;
        }
    }


    /**
     * 二维码
     *
     * @param QRimageUrl
     * @param wdith
     * @param height
     * @return
     */
    public static Drawable createQRimageDrawable(String QRimageUrl, int wdith, int height) {
        Bitmap bitmap = createQRimageBitmap(QRimageUrl, wdith, height);
        if (bitmap != null)
            return convertBitmap2Drawable(bitmap);

        return null;
    }

    /**
     * 二维码
     *
     * @param QRimageUrl
     * @param wdith
     * @param height
     * @return
     */
    public static Bitmap createQRimageBitmap(String QRimageUrl, int wdith, int height) {
        try {

            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(QRimageUrl,
                    BarcodeFormat.QR_CODE, wdith, height, hints);

            // 图像数据转换，使用了矩阵转换
            int[] pixels = new int[wdith * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < wdith; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * wdith + x] = 0xff000000;
                    } else {
                        pixels[y * wdith + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(wdith, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, wdith, 0, 0, wdith, height);

            return bitmap;

        } catch (WriterException e) {
        }
        return null;
    }

    /**
     * 先建立的关于从资源处 获取bitmap的方法 这里的sampleSize 目前设置为1 还没有想好具体该如何处理
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static Bitmap getBitmapFromResource(Context context, int resourceId) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inDither = false;
        o.inSampleSize = 1;
        o.inPurgeable = true;
        o.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeResource(context.getResources(), resourceId,
                o);
    }

    /**
     * 先建立的关于从资源处 获取bitmap的方法 这里的sampleSize 目前设置为1 还没有想好具体该如何处理
     *
     * @param context
     * @param resourceId
     * @return
     */
    public static Bitmap getBitmapFromResource(Context context, int resourceId, Bitmap.Config config) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inDither = false;
        o.inSampleSize = 1;
        o.inPurgeable = true;
        o.inPreferredConfig = config;
        return BitmapFactory.decodeResource(context.getResources(), resourceId,
                o);
    }


    public static Drawable getGradientDrawable(float radius, GradientDrawable.Orientation orientation, int... colors) {
//        int colors[] = {startColor, endColor};//分别为开始颜色，结束颜色
        GradientDrawable gd = new GradientDrawable(orientation, colors);//创建drawable
        gd.setCornerRadius(radius);
        return gd;
    }

}
