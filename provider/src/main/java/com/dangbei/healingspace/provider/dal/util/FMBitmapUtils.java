package com.dangbei.healingspace.provider.dal.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * <p>
 * <h2>简述:</h2>
 * <ol>无</ol>
 * <h2>功能描述:</h2>
 * <ol>无</ol>
 * <h2>修改历史:</h2>
 * <ol>无</ol>
 * </p>
 *
 * @author 11925
 * @date 2018/12/7/007
 */
public class FMBitmapUtils {



    public static Bitmap getRoundedTopCornerBitmap(Bitmap bitmap, int width, int height, float round) {
        if (bitmap == null) {
            return null;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        RectF rectF = new RectF(0, 0, width, height);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        //缩放位移
        Matrix matrix = new Matrix();
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        float scale = Math.max(height / (float) bitmapHeight, width / (float) bitmapWidth);
        matrix.setScale(scale, scale);

        float dx = 0, dy = 0;
        if (bitmapWidth * height > width * bitmapHeight) {
            dx = (width - bitmapWidth * scale) * 0.5f;
        } else {
            dy = (height - bitmapHeight * scale) * 0.5f;
        }
        matrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));

        canvas.drawRect(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, matrix, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        drawLeftUp(canvas, paint, width, height, round);
        drawRightUp(canvas, paint, width, height, round);
        drawLeftDown(canvas,paint,width,height,round);
        drawRightDown(canvas,paint,width,height,round);
        return output;
    }
    private static void drawLeftUp(Canvas canvas, Paint mPaint, int width, int height, float radius) {
        Path path = new Path();
        path.moveTo(0, radius);
        path.lineTo(0, 0);
        path.lineTo(radius, 0);
        //arcTo的第二个参数是以多少度为开始点，第三个参数-90度表示逆时针画弧，正数表示顺时针
        path.arcTo(new RectF(0, 0, radius * 2, radius * 2), -90, -90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private static void drawLeftDown(Canvas canvas, Paint mPaint, int width, int height, float radius) {
        Path path = new Path();
        path.moveTo(0, height - radius);
        path.lineTo(0, height);
        path.lineTo(radius, height);
        path.arcTo(new RectF(0, height - radius * 2, 0 + radius * 2, height), 90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private static void drawRightDown(Canvas canvas, Paint mPaint, int width, int height, float radius) {
        Path path = new Path();
        path.moveTo(width - radius, height);
        path.lineTo(width, height);
        path.lineTo(width, height - radius);
        path.arcTo(new RectF(width - radius * 2, height - radius * 2, width, height), 0, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private static void drawRightUp(Canvas canvas, Paint mPaint, int width, int height, float radius) {
        Path path = new Path();
        path.moveTo(width, radius);
        path.lineTo(width, 0);
        path.lineTo(width - radius, 0);
        path.arcTo(new RectF(width - radius * 2, 0, width, 0 + radius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

}
