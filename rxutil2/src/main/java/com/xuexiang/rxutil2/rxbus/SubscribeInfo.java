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

package com.xuexiang.rxutil2.rxbus;


import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * 订阅信息
 *
 * @author xuexiang
 * @since 2018/3/3 下午11:43
 */
public final class SubscribeInfo<T> {
    /**
     * 订阅者
     */
    private Flowable<T> mFlowable;
    /**
     * 订阅信息
     */
    private Disposable mDisposable;

    public SubscribeInfo(Flowable<T> flowable) {
        mFlowable = flowable;
    }

    public Flowable<T> getFlowable() {
        return mFlowable;
    }

    public SubscribeInfo setFlowable(Flowable<T> flowable) {
        mFlowable = flowable;
        return this;
    }

    public Disposable getDisposable() {
        return mDisposable;
    }

    public SubscribeInfo setDisposable(Disposable disposable) {
        mDisposable = disposable;
        return this;
    }
}
