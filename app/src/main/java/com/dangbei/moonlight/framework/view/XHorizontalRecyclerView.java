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
import com.dangbei.palaemon.axis.Axis;
import com.dangbei.palaemon.layout.DBHorizontalRecyclerView;
import com.dangbei.palaemon.leanback.GridLayoutManager;
import com.dangbei.palaemon.leanback.VerticalGridView;

/**
 * 适配TV leanback 横向recyclerView
 */
public class XHorizontalRecyclerView extends DBHorizontalRecyclerView {

    public interface OnXHRFocusedListener {

        /**
         * 焦点是否丢失
         *
         * @param focusLost
         */
        void onFocusedLose(boolean focusLost);
    }

    //default offset
    private int DEFAULT_EDGE_OFFSET = 50;
    private View lastFocusedView;
    private OnXHRFocusedListener listener;
    /**
     * 缓慢滚动开关
     */
    private boolean isOpenSmoothScroll = false;
    /**
     * 拦截表示长按情况下，会滚动缓慢。 默认情况true拦截;
     */
    private boolean isInterceptLongKeyPressed = false;

    /**
     * set offset
     *
     * @param DEFAULT_EDGE_OFFSET numBer
     */
    public void setDefaultOffset(int DEFAULT_EDGE_OFFSET) {
        this.DEFAULT_EDGE_OFFSET = DEFAULT_EDGE_OFFSET;
    }

    public XHorizontalRecyclerView(Context context) {
        super(context);
        init();
    }

    public XHorizontalRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public XHorizontalRecyclerView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
        init();
    }

    private void init() {
        // ignore
    }


    @Override
    public void setSelectedPosition(int position) {
        super.setSelectedPosition(position);
    }

    public void setInterceptLongKeyPressed(boolean interceptLongKeyPressed) {
        isInterceptLongKeyPressed = interceptLongKeyPressed;
    }

    @Override
    public View focusSearch(View focused, int direction) {
        View focusSearch = _focusSearch(focused, direction);
        if (listener != null
                && focusSearch != focused
                && (direction == View.FOCUS_UP || direction == View.FOCUS_DOWN)) {
            //失去焦点
            if (lastFocusedView != null) {
                listener.onFocusedLose(true);
                lastFocusedView = null;
            }
        }
        return focusSearch;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (listener != null && lastFocusedView == null) {
            //首次获取焦点
            View view = getLayoutManager().findViewByPosition(getSelectedPosition());
            if (view != null) {
                lastFocusedView = view;
                listener.onFocusedLose(false);
            }
        }
        super.requestChildFocus(child, focused);
    }

    public void setListener(OnXHRFocusedListener listener) {
        this.listener = listener;
    }


    public void setItemOffset(boolean isFocusInMiddle) {
        if (!isFocusInMiddle) {
            setItemAlignmentOffset(-Axis.scaleX(DEFAULT_EDGE_OFFSET));
            setItemAlignmentOffsetPercent(
                    VerticalGridView.ITEM_ALIGN_OFFSET_PERCENT_DISABLED);
            setItemAlignmentOffsetWithPadding(true);
            setWindowAlignmentOffsetPercent(
                    VerticalGridView.WINDOW_ALIGN_OFFSET_PERCENT_DISABLED);
            setWindowAlignment(VerticalGridView.WINDOW_ALIGN_NO_EDGE);
        } else {
            setItemAlignmentOffset(VerticalGridView.WINDOW_ALIGN_NO_EDGE);
        }
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


    private Interpolator slowScrollInterpolator = new CubicBezierInterpolator(0.3f, 0.35, 0.1, 0.95);
    private Interpolator fastScrollInterpolator = new LinearInterpolator();

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isOpenSmoothScroll) {
                if (event.getRepeatCount() >= 2) {
//                    XLog.d("lhb-scroll", "fast scroll.....");
                    setScrollTimeMultiplier(1.0f / 0.935f);
                    setScrollTimeFactor(0.618f);
                    setScrollInterpolator(fastScrollInterpolator);
                } else {
//                    XLog.d("lhb-scroll", "slow scroll.....");
                    setScrollTimeMultiplier(1.3f);
                    setScrollTimeFactor(0.618f);
                    setScrollInterpolator(slowScrollInterpolator);
                }
            }
        }
        return super.dispatchKeyEventPreIme(event);
    }


    private View _focusSearch(View focused, int direction) {

        if (isInterceptLongKeyPressed) {
            final FocusFinder ff = FocusFinder.getInstance();
            if (direction == View.FOCUS_DOWN || direction == View.FOCUS_UP) {
                int loop = 1;
                while (true && --loop >= 0) {
                    View result = ff.findNextFocus(this, focused, direction);
                    if (result != null) {
                        View itemView = findContainingItemView(result);
                        if (itemView != null) {
                            RecyclerView.ViewHolder vh = this.findContainingViewHolder(itemView);
                            if (vh != null) {
                                if (direction == View.FOCUS_DOWN && vh.getAdapterPosition() > findLastVisibleItemPosition()) {
                                    continue;
                                } else if (direction == View.FOCUS_UP && vh.getAdapterPosition() < findFirstVisibleItemPosition()) {
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
