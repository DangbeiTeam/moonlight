package com.dangbei.moonlight.framework.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dangbei.xfunc.utils.collection.CollectionUtil;
import com.wangjie.seizerecyclerview.BaseRecyclerAdapter;
import com.wangjie.seizerecyclerview.BaseViewHolder;
import com.wangjie.seizerecyclerview.SeizePosition;
import com.wangjie.seizerecyclerview.attacher.Func1R;
import com.wangjie.seizerecyclerview.attacher.MultiSeizeAdapter;
import com.wangjie.seizerecyclerview.attacher.ViewHolderOwner;

import java.util.Iterator;
import java.util.List;


public class CommonMultiSeizeAdapter<T> extends MultiSeizeAdapter<T> {
    private RecyclerView recyclerView;
    private Runnable resetFocusedRunnable;


    @Nullable
    public T getItemSafe(int subSourcePosition) {
        if (CollectionUtil.isValidatePosition(getList(), subSourcePosition)) {
            return getItem(subSourcePosition);
        }
        return null;
    }

    @Override
    public void addList(@Nullable List<T> list) {
        removeUnsupportedType(list);
        super.addList(list);
    }

    @Override
    public void setList(@Nullable List<T> list) {
        removeUnsupportedType(list);
        super.setList(list);
    }

    private void removeUnsupportedType(@Nullable List<T> list) {
        if (null == list) {
            return;
        }
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            // 不支持类型
            if (!hasViewType(getSourceItemViewType(t))) {
                iterator.remove();
            }
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, SeizePosition seizePosition) {
        if (holder.itemView != null
                && recyclerView != null
                && holder.itemView.isActivated() != recyclerView.isActivated()) {
            // wenhechen 二级列表根据这个activated值判断是否显示标签（viewHolder复用导致activated不一致，这里重新设置统一）
            holder.itemView.setActivated(recyclerView.isActivated());
        }

        super.onBindViewHolder(holder, seizePosition);
    }

    public void notifyParentDataSetChanged() {
        parentAdapter.notifyItemRangeChanged(0, parentAdapter.getItemCount());
    }

    @Override
    public void attachToRecyclerView(RecyclerView recyclerView) {
        super.attachToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }


    @Override
    public void notifyItemInserted(int position) {
        super.notifyItemInserted(position);
        resetFocused();
    }

    @Override
    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        super.notifyItemRangeInserted(positionStart, itemCount);
        resetFocused();
    }

    private void resetFocused() {
        if (recyclerView == null) {
            return;
        }
        recyclerView.removeCallbacks(resetFocusedRunnable);
        resetFocusedRunnable = () -> {
            View focus = recyclerView.findFocus();
            if (focus == null) {
                return;
            }
            focus.clearFocus();
            focus.requestFocus();
        };
        recyclerView.postDelayed(resetFocusedRunnable, 100);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public void setParentAdapter(BaseRecyclerAdapter parentAdapter) {
        super.setParentAdapter(parentAdapter);
    }


    @Override
    public void addSupportViewHolder(int type, ViewHolderOwner viewHolderOwner) {
        super.addSupportViewHolder(type, viewHolderOwner);
    }

    public void addSupportViewHolder(String type, ViewHolderOwner viewHolderOwner) {
        int intType = stringType2InTye(type);
        this.addSupportViewHolder(intType, viewHolderOwner);
    }


    public int stringType2InTye(String strType) {
        int intType = strType.hashCode();
        return intType;
    }


    /**
     * same owner
     *
     * @param types
     * @param viewHolderOwner
     */
    public void addSupportViewHolders(int[] types, ViewHolderOwner viewHolderOwner) {
        for (int type : types) {
            this.addSupportViewHolder(type, viewHolderOwner);
        }
    }


    @Override
    public void setGetItemType(Func1R<T, Integer> getItemType) {
        super.setGetItemType(getItemType);
    }
}
