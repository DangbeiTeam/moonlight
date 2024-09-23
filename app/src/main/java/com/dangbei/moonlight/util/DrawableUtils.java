package com.dangbei.moonlight.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.ByteArrayOutputStream;

/**
 * Created by zhanghongjie on 2018/4/2.
 */

public class DrawableUtils {
    @Nullable
    public static Bitmap takeScreenShot(@NonNull Activity activity) {
        return takeScreenShot(activity.getWindow().getDecorView());
    }


    public static Bitmap takeScreenShot(@NonNull View view) {
        return takeScreenShot(view, null);
    }

    @Nullable
    public static Bitmap takeScreenShot(@NonNull View view,Bitmap bitmap) {
        try {
            // View是你需要截图的View
            view.setDrawingCacheEnabled(true); //启用缓存绘制
            //会触发view的绘制。
            view.buildDrawingCache(); //构建缓存绘制
            //不调用buildDrawingCache()，也能取得图片；getDrawingCache内部判断缓存是否存在，若不在，则调用buildDrawingCache()
            Bitmap cacheBitmap = view.getDrawingCache();// view.getDrawingCache();getDrawingCache(view);
            if (null != cacheBitmap) {
                Bitmap dst = (bitmap != null)? bitmap : Bitmap.createBitmap(cacheBitmap.getWidth(), cacheBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(dst);
//                canvas.drawBitmap(cacheBitmap, 0, 0, null); //以缓存图创建一个新的bitmap；从而可以清除缓存
                canvas.drawBitmap(cacheBitmap, null, new Rect(0,0,canvas.getWidth(),canvas.getHeight()), null);
                view.setDrawingCacheEnabled(false); //缓存绘制false， 同时将已有缓存清空
                return dst;
            }
        } catch (Exception e) {
            // yl 会发生一个崩溃错误 暂时这样处理
            e.printStackTrace();
        }

        return null;
    }


    public static Drawable getGradientDrawable(int bgColor, float radius) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(bgColor);
        gd.setCornerRadius(radius);
        return gd;
    }

    public static GradientDrawable getGradientDrawable(int bgColor, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(bgColor);
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        return gd;
    }


    /**
     * 圆角矩形
     *
     * @param color     填充的颜色
     * @param outRadius 圆角弧度
     * @return
     */
    public static Drawable getRoundShapeDrawable(int color, int outRadius) {
        int innerRadius = 0;
        /*圆角矩形*/
        float[] outerRadii = {outRadius, outRadius, outRadius, outRadius, outRadius, outRadius, outRadius, outRadius};//左上x2,右上x2,右下x2,左下x2，注意顺序（顺时针依次设置）
        int spaceBetOutAndInner = outRadius - innerRadius;//内外矩形间距
        RectF inset = new RectF(spaceBetOutAndInner, spaceBetOutAndInner, spaceBetOutAndInner, spaceBetOutAndInner);
        float[] innerRadii = {innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, innerRadius, innerRadius};//内矩形 圆角半径
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii);

        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }

    /**
     * 圆角矩形，空心的
     * @param color 颜色
     * @param radius 弧度
     * @param width 宽度
     * */
    public static Drawable getRoundShapeDrawable(int color, int radius, int width) {
        /*圆角矩形*/
        float[] outerRadii = {radius, radius, radius, radius, radius, radius, radius, radius};//左上x2,右上x2,右下x2,左下x2，注意顺序（顺时针依次设置）
        RectF inset = new RectF(width, width, width, width);
        float[] innerRadii = {radius, radius, radius, radius, radius, radius, radius, radius};//内矩形 圆角半径
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, inset, innerRadii);

        ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setAntiAlias(true);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }


    /**
     * @param startColor startColor
     * @param endColor   endColor
     * @param radius     corner
     * @return
     */
    public static Drawable getGradientDrawable(int startColor, int endColor, float radius) {
        int colors[] = {startColor, endColor};//分别为开始颜色，中间夜色，结束颜色
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);//创建drawable
        gd.setCornerRadius(radius);
        return gd;
    }

    /**
     * @param startColor startColor
     * @param endColor   endColor
     * @param radius     corner
     * @return 自定义渐变方向
     */
    public static Drawable getGradientDrawableOrientation(int startColor, int endColor, GradientDrawable.Orientation orientation, float radius) {
        int colors[] = {startColor, endColor};//分别为开始颜色，中间夜色，结束颜色
        GradientDrawable gd = new GradientDrawable(orientation, colors);//创建drawable
        gd.setCornerRadius(radius);
        return gd;
    }

    public static Drawable getGradientDrawableOrientation(int startColor, int endColor, GradientDrawable.Orientation orientation) {
        int[] colors = {startColor, endColor};
        GradientDrawable gd = new GradientDrawable(orientation, colors);
        return gd;
    }

    /**
     * 从左到右渐变的，带圆角的drawable
     *
     * @param startColor  startColor
     * @param endColor    endColor
     * @param topLeft     corner
     * @param topRight    corner
     * @param bottomRight corner
     * @param bottomLeft  corner
     * @return 对应drawable
     */
    public static Drawable getGradientDrawable(int startColor, int endColor, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        int colors[] = {startColor, endColor};//分别为开始颜色，结束颜色
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);//创建drawable
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        return gd;
    }

    public static Drawable getGradientDrawable(int startColor, int endColor, float topLeft, float topRight, float bottomRight, float bottomLeft,GradientDrawable.Orientation orientation) {
        int colors[] = {startColor, endColor};//分别为开始颜色，结束颜色
        GradientDrawable gd = new GradientDrawable(orientation, colors);//创建drawable
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        return gd;
    }

    /**
     * 从上到下渐变的，带圆角的drawable
     *
     * @param startColor  startColor
     * @param endColor    endColor
     * @param topLeft     corner
     * @param topRight    corner
     * @param bottomRight corner
     * @param bottomLeft  corner
     * @return 对应drawable
     */
    public static Drawable getGradientDrawableOrientation(int startColor, int endColor, GradientDrawable.Orientation orientation, float topLeft, float topRight, float bottomRight, float bottomLeft) {
        int colors[] = {startColor, endColor};//分别为开始颜色，结束颜色
        GradientDrawable gd = new GradientDrawable(orientation,colors);//创建drawable
        gd.setCornerRadii(new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft});
        return gd;
    }
//
//    public static Drawable getFocusDrawable(Context context, int outColor) {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(AxisUtil.scaleX(7), Color.TRANSPARENT);
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(AxisUtil.scaleX(3), outColor);
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }


    public static Drawable generateTabSelector(Drawable selected, Drawable unselected, Drawable focus, Drawable unfocus) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected}, selected);
        drawable.addState(new int[]{-android.R.attr.state_selected}, unselected);
        drawable.addState(new int[]{android.R.attr.state_focused}, focus);
        drawable.addState(new int[]{-android.R.attr.state_focused}, unfocus);
        return drawable;
    }

//
//    public static Drawable getFocusCircleDrawable(Context context) {
//        return getFocusCircleDrawable(context, Color.parseColor("#F0C41C"));
//    }
//
//    public static Drawable getFocusCircleDrawable(Context context, int color) {
//        Drawable[] drawables = new Drawable[2];
//        GradientDrawable gd = new GradientDrawable();
//        gd.setStroke(AxisUtil.scaleX(7), Color.TRANSPARENT);
//        gd.setShape(GradientDrawable.OVAL);
//        drawables[0] = gd;
//        gd = new GradientDrawable();
//        gd.setStroke(AxisUtil.scaleX(3), color);
//        gd.setShape(GradientDrawable.OVAL);
//        drawables[1] = gd;
//        return new LayerDrawable(drawables);
//    }
//
//    public static Drawable generateTabTitleLeftDrawable(Context context) {
//        GradientDrawable gd = new GradientDrawable();//创建drawable
//        gd.setColor(Color.parseColor("#F0C41C"));
//        gd.setCornerRadius(AxisUtil.scaleX(5));
//        gd.setSize(AxisUtil.scaleX(5), AxisUtil.scaleY(40));
//        return gd;
//    }

    public static Drawable getCircleDrawable(int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(color);
        return gd;
    }

    public static Drawable getCircleDrawable(int startColor, int endColor) {
        int colors[] = {startColor, endColor};//分别为开始颜色，中间夜色，结束颜色
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);//创建drawable
        gd.setShape(GradientDrawable.OVAL);
        return gd;
    }


    /**
     * 从应用icon获取bitmap
     */
    @SuppressLint("NewApi")
    public static Bitmap getBitmapFromIcon(Drawable drawable) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && drawable instanceof AdaptiveIconDrawable) {
            Drawable[] drr = new Drawable[2];
            drr[0] = ((AdaptiveIconDrawable) drawable).getBackground();
            drr[1] = ((AdaptiveIconDrawable) drawable).getForeground();

            LayerDrawable layerDrawable = new LayerDrawable(drr);
            int width = layerDrawable.getIntrinsicWidth();
            int height = layerDrawable.getIntrinsicHeight();

            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            layerDrawable.draw(canvas);
        } else {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        return bitmap;
    }

    //将drawable转换成可以用来存储的byte[]类型
    public static byte[] getPicture(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable){
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            return baos.toByteArray();
        }
        return null;
    }


    /**
     * 这里的效果是： 可以渐变显示图片
     * 处理首页横向的RecycleView的item的图片不重复加载。
     * 但是处理的方式导致imageview加载的时候没有渐变，就用以下方式。以下方式是从Glide 复制过来的。
     * @param imageView
     * @param target
     */
    public static void transitionCrossFade(ImageView imageView, Drawable target) {
        Drawable previous = imageView.getDrawable();
        if(previous instanceof TransitionDrawable && ((TransitionDrawable) previous).getNumberOfLayers() > 1){
            previous = ((TransitionDrawable) previous).getDrawable(1);
        }
        if (previous == null) {
            previous = new ColorDrawable(Color.TRANSPARENT);
        }
        TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{previous, target});
        transitionDrawable.setCrossFadeEnabled(true);
        transitionDrawable.startTransition(300);
        imageView.setImageDrawable(transitionDrawable);

    }


    public static StateListDrawable selectorFocusedSimple(Drawable focused, Drawable notFocused) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_enabled}, focused);
        drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, focused);
        drawable.addState(new int[]{}, notFocused);
        return drawable;
    }

    public static Path getPath(float radius, boolean topLeft, boolean topRight,
                         boolean bottomRight, boolean bottomLeft, float left, float top, float right, float bottom) {

        final Path path = new Path();
        final float[] radii = new float[8];

        if (topLeft) {
            radii[0] = radius;
            radii[1] = radius;
        }

        if (topRight) {
            radii[2] = radius;
            radii[3] = radius;
        }

        if (bottomRight) {
            radii[4] = radius;
            radii[5] = radius;
        }

        if (bottomLeft) {
            radii[6] = radius;
            radii[7] = radius;
        }

        path.addRoundRect(new RectF(left, top, right, bottom),
                radii, Path.Direction.CW);

        return path;
    }
}
