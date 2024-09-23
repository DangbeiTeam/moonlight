package com.dangbei.moonlight.framework.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dangbei.gonzalez.GonScreenAdapter;
import com.dangbei.moonlight.util.ThreadSecheme;
import com.wangjie.seizerecyclerview.BaseRecyclerAdapter;
import com.wangjie.seizerecyclerview.BaseViewHolder;
import com.wangjie.seizerecyclerview.SeizeAdapter;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 08/08/2017.
 */
public class CommonLazyRecyclerAdapter extends BaseRecyclerAdapter {

    public static final String TAG = CommonLazyRecyclerAdapter.class.getSimpleName();
    private Set<CommonLazyViewHolder> viewHolders = new HashSet<>();
    WeakHashMap<Integer, CommonLazyViewHolder> disposeHolders = new WeakHashMap<>();
    private RecyclerView parentRecyclerView;
    private int scrollState;
    private LazyRunnable lazyRunnable;
    private View.OnAttachStateChangeListener onAttachStateChangeListener;

    /**
     * 是否开启懒加载
     */
    private boolean isOpenLazyHolder = false;

    public CommonLazyRecyclerAdapter() {
        //如果为false，刷新时会重新new viewHolder，默认为false，但是为true会导致itemmove刷新无效
        setHasStableIds(true);
    }

    public static CommonLazyRecyclerAdapter single(SeizeAdapter seizeAdapter) {
        CommonLazyRecyclerAdapter adapter = new CommonLazyRecyclerAdapter();
        adapter.setSeizeAdapters(seizeAdapter);
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        //  有优化空间-lhb.
        if (holder instanceof CommonLazyViewHolder) {
            CommonLazyViewHolder holder1 = (CommonLazyViewHolder) holder;
            holder1.onInstantBindViewHolder(holder1, holder1.getSeizePosition());
            disposeHolders.put(position, holder1);

            // lazy binder
            if (isOpenLazyHolder) {
                if (!isScrolling()) {
                    holder1.onLazyBindViewHolder();
                } else {
                    viewHolders.add(holder1);
                }
            } else {
                holder1.onLazyBindViewHolder();
            }
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 是否有懒加载机制，一般横向recyclerview会有
     */
    public void setHasLazyLoad(boolean hasLazyLoad) {
        this.isOpenLazyHolder = hasLazyLoad;
    }


    @Override
    public void onViewRecycled(@NonNull BaseViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof CommonLazyViewHolder) {
            ((CommonLazyViewHolder) holder).onUnbindView();
            if (viewHolders != null) {
                viewHolders.remove(holder);
            }
        }
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (viewHolders != null && isOpenLazyHolder) {
            RecyclerView.OnScrollListener onScrollChangeListener = getOnScrollChangeListener();
            View.OnAttachStateChangeListener onAttachStateChangeListener = getOnAttachStateChangeListener();
            recyclerView.removeOnScrollListener(onScrollChangeListener);
            recyclerView.addOnScrollListener(onScrollChangeListener);
            recyclerView.removeOnAttachStateChangeListener(onAttachStateChangeListener);
            recyclerView.addOnAttachStateChangeListener(onAttachStateChangeListener);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        try {
            if (viewHolders != null) {
                for (CommonLazyViewHolder viewHolder : viewHolders) {
                    if (viewHolder != null) {
                        viewHolder.onViewDetachedFromWindow();
                    }
                }
            }
        } catch (Exception e) {
        }
        dispose();
        // 释放全部资源
        if (disposeHolders != null && !disposeHolders.isEmpty()) {
            for (Map.Entry<Integer, CommonLazyViewHolder> entry : disposeHolders.entrySet()) {
                if (entry.getValue() != null) {
                    if (!entry.getValue().isDisposed()) {
                        entry.getValue().onDispose();
                        entry.getValue().itemView.removeCallbacks(null);
                        entry.getValue().itemView.clearAnimation();
                        entry.getValue().itemView.clearFocus();
                        ((ViewGroup) entry.getValue().itemView).removeAllViews();
                    }
                }
            }
        }
        disposeHolders.clear();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder != null) {
            holder.onViewDetachedFromWindow();
        }
    }

    private RecyclerView getParentRecyclerView(View recyclerView) {
//        if (!(recyclerView instanceof HorizontalGridView)) {
//            return null;
//        }
        ViewParent parent = recyclerView.getParent();
        while (parent != null) {
            if (parent instanceof RecyclerView) {
                return (RecyclerView) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    private boolean isScrolling() {
        return scrollState != RecyclerView.SCROLL_STATE_IDLE
                || (parentRecyclerView != null && parentRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE);
    }

    public RecyclerView.OnScrollListener getOnScrollChangeListener() {
        if (onScrollChangeListener == null) {
            onScrollChangeListener = new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    scrollState = newState;
                    // 记录滚动状态
//                    LauncherApplication.IS_GLOBAL_SCROLLING = scrollState != RecyclerView.SCROLL_STATE_IDLE;
//                    XLog.d("lhbb", "recyclerView is Scrolling ?" + LauncherApplication.IS_GLOBAL_SCROLLING);

                    if (!isScrolling() && isOpenLazyHolder) {
                        recyclerView.removeCallbacks(lazyRunnable);
                        if (lazyRunnable == null) {
                            lazyRunnable = new LazyRunnable(viewHolders);
                        }
                        recyclerView.postDelayed(lazyRunnable, 200);
                    }
                }
            };
        }
        return onScrollChangeListener;
    }

    private RecyclerView.OnScrollListener onScrollChangeListener;

    public View.OnAttachStateChangeListener getOnAttachStateChangeListener() {
        if (onAttachStateChangeListener == null) {
            onAttachStateChangeListener = new View.OnAttachStateChangeListener() {

                @Override
                public void onViewAttachedToWindow(View v) {
                    parentRecyclerView = getParentRecyclerView(v);
                    if (parentRecyclerView != null) {
                        parentRecyclerView.removeOnScrollListener(onScrollChangeListener);
                        parentRecyclerView.addOnScrollListener(onScrollChangeListener);
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    if (parentRecyclerView != null) {
                        //放到add之前remove，不然会造成复用的滑动状态bug
                        parentRecyclerView = null;
                    }
                }
            };
        }
        return onAttachStateChangeListener;
    }

    public void dispose() {
        if (viewHolders != null) {
            viewHolders.clear();
        }
        onAttachStateChangeListener = null;
        if (parentRecyclerView != null) {
            parentRecyclerView.removeOnScrollListener(null);
            parentRecyclerView.addOnScrollListener(null);
        }
        parentRecyclerView = null;
        lazyRunnable = null;
    }


    private class LazyRunnable implements Runnable {
        int[] itemPos = new int[2];

        private WeakReference<Set<CommonLazyViewHolder>> viewHolders;

        public LazyRunnable(Set<CommonLazyViewHolder> viewHolders) {
            this.viewHolders = new WeakReference<>(viewHolders);
        }

        @Override
        public void run() {
            Set<CommonLazyViewHolder> viewHolders = this.viewHolders.get();
            if (viewHolders != null) {
                int delayTime = 0;
                for (CommonLazyViewHolder viewHolder : viewHolders) {
                    if (!CommonLazyRecyclerAdapter.this.isScrolling()) {

                        if (viewHolder.itemView != null) {
                            viewHolder.itemView.getLocationOnScreen(itemPos);
                            if ((itemPos[1] + viewHolder.itemView.getHeight()) < 0
                                    ||
                                    itemPos[1] >= (GonScreenAdapter.getInstance().getScreenHeight())
                                    ||
                                    (itemPos[0] == 0 && itemPos[1] == 0)) {
//                                XLog.d("lhb-adapter", "no need to render..." + viewHolder.toString());
                                continue;
                            }
                            ThreadSecheme.runUITask(viewHolder, delayTime += 45);
                        }
                    }
                }
            }
        }
    }
}
