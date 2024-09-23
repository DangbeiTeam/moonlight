package com.dangbei.moonlight.framework.adapter;


import android.view.View;

import com.wangjie.seizerecyclerview.BaseViewHolder;
import com.wangjie.seizerecyclerview.SeizePosition;

/**
 * 懒加载holder
 * 滚动的时候不进行视图的刷新
 * 停下来后进行视图的刷新
 * 单独使用无效 需和
 * {@link com.dangbei.leard.leradlauncher.ui.base.view.adapter.CommonMultiSeizeAdapter 一起使用}
 * <p>
 * <p>
 * Created by yl on 2018/7/6.
 */
public abstract class CommonLazyViewHolder extends BaseViewHolder implements Runnable {

    private boolean isDisposed = false;

    /**
     * 实时需要绑定并且展示的数据
     *
     * @param holder        holder
     * @param seizePosition seizePosition
     */
    public abstract void onInstantBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition);

    /**
     * 滚动不加载 停下来后需要展示的数据
     *
     * @param holder        holder
     * @param seizePosition seizePosition
     */
    public abstract void onLazyBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition);

    public void onUnbindView() {
        // ignore
    }


    /**
     * recyclerView重新绑定新的adapte会触发此方式，意在释放viewholder所关联的资源
     */
    public void onDispose() {
        this.isDisposed = true;
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public final void onLazyBindViewHolder() {
        onLazyBindViewHolder1(this, getSeizePosition());
    }

    private void onLazyBindViewHolder1(BaseViewHolder holder, SeizePosition seizePosition) {
        if (lazeBind) {
            return;
        }
        lazeBind = true;
        onLazyBindViewHolder(holder, seizePosition);
    }


    public CommonLazyViewHolder(View itemView) {
        super(itemView);
    }

    private boolean lazeBind;


    @Override
    public void run() {
        if (!this.isDisposed() ) {
            this.onLazyBindViewHolder();
        }
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        this.lazeBind = false;
        View view = onCreateTransitionView();
//        if (view != null) {
//            view.postOnAnimation(() -> {
//                String transitionViewName = onCreateTransitionViewName();
//                ViewCompat.setTransitionName(view,
//                        transitionViewName != null ? transitionViewName : ResUtil.getString(R.string.transition_film_cover));
//                Context context = view.getContext();
//                if (context instanceof Activity) {
//                    ActivityCompat.startPostponedEnterTransition(((Activity) context));
//                }
//            });
//        } else {
//            if (itemView.getContext() instanceof Activity) {
//                ActivityCompat.startPostponedEnterTransition(((Activity) itemView.getContext()));
//            }
//        }
    }

    protected View onCreateTransitionView() {
        return null;
    }

    protected String onCreateTransitionViewName() {
        return null;
    }

}
