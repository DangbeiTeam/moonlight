package com.dangbei.moonlight.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 11/08/2017.
 */
public final class ViewUtil {

    private ViewUtil() {
    }


    public static boolean isViewVisible(View view) {
        if (view == null) {
            return false;
        }
        return (view.getVisibility() == View.VISIBLE);
    }

    public static void showView(View view, boolean isShow) {
        if (isShow) {
            showView(view);
        } else {
            hideView(view);
        }
    }

    public static void showView(View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideView(View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    public static void invisibleView(View view, boolean isShow) {
        if (isShow) {
            showView(view);
        } else {
            invisibleView(view);
        }
    }

    public static void invisibleView(View view) {
        if (view == null) {
            return;
        }
        if (view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    //设定RecyclerView最大滑动速度
    public static void setMaxFlingVelocity(RecyclerView recycleview, int velocity) {
        try {
            Field field = recycleview.getClass().getDeclaredField("mMaxFlingVelocity");
            field.setAccessible(true);
            field.set(recycleview, velocity);
        } catch (Exception e) {
        }
    }

    /**
     * 设置viewpager 之间的切换速度
     */
    public static void initSwitchTime(Context context, ViewPager viewPager, int time) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager, new ViewPagerScroller(context, time));
        } catch (Exception e) {
        }
    }

    static class ViewPagerScroller extends Scroller {
        int time;

        public ViewPagerScroller(Context context, int time) {
            super(context);
            this.time = time;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, time);
        }
    }

}
