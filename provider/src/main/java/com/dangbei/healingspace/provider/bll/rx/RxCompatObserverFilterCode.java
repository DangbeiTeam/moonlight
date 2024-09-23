package com.dangbei.healingspace.provider.bll.rx;

import com.lerad.lerad_base_support.bridge.compat.RxCompatObserver;
import com.lerad.lerad_base_support.bridge.compat.subscriber.RxCompatException;
import com.monster.rxbus.RxBus2;

import io.reactivex.disposables.Disposable;

public class RxCompatObserverFilterCode<T> extends RxCompatObserver<T> {

    @Override
    public void onSubscribeCompat(Disposable d) {

    }

    @Override
    public void onErrorCompat(RxCompatException compatThrowable) {
        int code = compatThrowable.getCode();
        if (code == 2200 ||code == 1004 ||code == 701) {
            // TODO lhb.
//            RxBus2.get().post(new NeedLoginEvent());
        }
    }

    @Override
    public void onNextCompat(T t) {

    }

}