package com.dangbei.healingspace.provider.bll.vm;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * 基本的VM
 * <p>
 * Created by wangjie on 16/1/8.
 */
public class VM<T> implements Serializable {
    public static final int TYPE_DEFAULT = -0x34544;

    private T model;

    public VM(@NonNull T model) {
        this.model = model;
    }

    @NonNull
    public T getModel() {
        return model;
    }

    public void setModel(@NonNull T model) {
        this.model = model;
    }

    public int getViewType() {
        return 0;
    }

    public static boolean equals(Object o1, Object o2) {
        return o1 != null
                && o1 == o2
                && o1.hashCode() == o2.hashCode();
    }
}
