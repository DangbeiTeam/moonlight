package com.dangbei.moonlight.framework.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.recyclerview.widget.RecyclerView;

import com.dangbei.moonlight.framework.interpolator.CubicBezierInterpolator;
import com.dangbei.palaemon.layout.DBVerticalRecyclerView;
import com.dangbei.palaemon.leanback.GridLayoutManager;

/**
 * 适配TV leanback 纵向recyclerView
 */
public class XVerticalRecyclerView extends DBVerticalRecyclerView {
    private static final String TAG = XVerticalRecyclerView.class.getSimpleName();
    /**
     * 缓慢滚动开关
     */
    private boolean isOpenSmoothScroll = false;

    private Interpolator slowScrollInterpolator = new CubicBezierInterpolator(0.3f, 0.35, 0.1, 0.95);
    private Interpolator fastScrollInterpolator = new LinearInterpolator();

    public XVerticalRecyclerView(Context context) {
        super(context);
        init();
    }

    public XVerticalRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();

    }

    public XVerticalRecyclerView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init();
    }

    private void init() {
        setItemAnimator(null);
    }

    public void setOpenSmoothScroll(boolean isOpenSmoothScroll) {
        if (isOpenSmoothScroll) {
            // ignore
        } else {
            // 滚动时间控制
            setScrollTimeFactor(1);
            setScrollTimeMultiplier(1);
            setScrollInterpolator(null);
        }
        this.isOpenSmoothScroll = isOpenSmoothScroll;
    }

    int curKeyCode;

    public int getCurKeyCode() {
        return curKeyCode;
    }

    @Override
    protected boolean dispatchPalaemonEventPreIme(KeyEvent event) {
        this.curKeyCode = event.getKeyCode();

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isOpenSmoothScroll) {
                if (event.getRepeatCount() >= 2) {
//                    XLog.d("lhb-scroll", "fast scroll.....");
                    setScrollTimeMultiplier(1.0f / 0.935f);
                    setScrollInterpolator(fastScrollInterpolator);
                } else {
//                    XLog.d("lhb-scroll", "slow scroll.....");
                    setScrollTimeMultiplier(1.3f);
                    setScrollInterpolator(slowScrollInterpolator);
                }
            }
        }
        return super.dispatchPalaemonEventPreIme(event);
    }


    @Override
    public View focusSearch(View focused, int direction) {

        if (isOpenSmoothScroll) {
            final FocusFinder ff = FocusFinder.getInstance();
            if (direction == View.FOCUS_DOWN || direction == View.FOCUS_UP) {
                int loop = 2;
                while (true && --loop >= 0) {
                    View result = ff.findNextFocus(this, focused, direction);
                    if (result != null) {
                        View itemView = findContainingItemView(result);
                        if (itemView != null) {
                            RecyclerView.ViewHolder vh = this.findContainingViewHolder(itemView);
                            if (vh != null) {
//                                XLog.d(TAG, "cur position = " + vh.getAdapterPosition() + "|" + findLastVisibleItemPosition() + "|" + findFirstVisibleItemPosition());
                                if (direction == View.FOCUS_DOWN && vh.getAdapterPosition() > findLastVisibleItemPosition()) {
//                                    XLog.d(TAG, " viewHolder position = " + vh.getAdapterPosition() + "||" + findLastVisibleItemPosition());
                                    continue;
                                } else if (direction == View.FOCUS_UP && vh.getAdapterPosition() < findFirstVisibleItemPosition()) {
//                                    XLog.d(TAG, " viewHolder position = " + vh.getAdapterPosition() + "||" + findFirstVisibleItemPosition());
                                    continue;
                                }
                            }
                        }
                        return result;
                    }
                }
            }

            if (focused != null && this.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                return focused;
            }
        }
        return super.focusSearch(focused, direction);
    }


    private int findFirstVisibleItemPosition() {
        if (getLayoutManager() == null) {
            return -1;
        }
        return ((GridLayoutManager) getLayoutManager()).getFirstVisibleIndex();
    }


    private int findLastVisibleItemPosition() {
        if (getLayoutManager() == null) {
            return -1;
        }
        return ((GridLayoutManager) getLayoutManager()).getLastVisibleIndex();
    }


}
