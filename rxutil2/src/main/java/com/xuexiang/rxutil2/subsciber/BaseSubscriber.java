/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.rxutil2.subsciber;

import com.xuexiang.rxutil2.exception.RxException;
import com.xuexiang.rxutil2.exception.RxExceptionHandler;
import com.xuexiang.rxutil2.logs.RxLog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.observers.DisposableObserver;


/**
 * 基础订阅者
 * @author xuexiang
 * @date 2018/3/9 上午9:00
 */
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    public BaseSubscriber() {

    }

    @Override
    protected void onStart() {
        RxLog.e("-->Subscriber is onStart");
    }

    @Override
    public void onComplete() {
        RxLog.e("-->Subscriber is Complete");
    }


    @Override
    public final void onError(Throwable e) {
        RxLog.e("-->Subscriber is onError");
        try {
            if (e instanceof RxException) {
                RxLog.e("--> e instanceof RxException, message:" + e.getMessage());
                onError((RxException) e);
            } else {
                RxLog.e("e !instanceof RxException, message:" + e.getMessage());
                onError(RxExceptionHandler.handleException(e));
            }
        } catch (Throwable throwable) {  //防止onError中执行又报错导致rx.exceptions.OnErrorFailedException: Error occurred when trying to propagate error to Observer.onError问题
            e.printStackTrace();
        }
    }

    /**
     * 出错
     * @param e
     */
    public abstract void onError(RxException e);

}
