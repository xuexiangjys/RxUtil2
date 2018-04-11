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

package com.xuexiang.rxutil2.rxjava;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * RxJava订阅池
 *
 * @author xuexiang
 * @date 2018/3/3 下午3:39
 */
public class DisposablePool {

    private static DisposablePool sInstance;

    //===================================网络请求订阅管理=======================================//
    /**
     * 网络请求订阅池，管理Subscribers订阅，防止内存泄漏
     */
    private ConcurrentHashMap<Object, CompositeDisposable> maps = new ConcurrentHashMap<>();

    private DisposablePool() {

    }

    /**
     * 获取订阅池
     * @return
     */
    public static DisposablePool get() {
        if (sInstance == null) {
            synchronized (DisposablePool.class) {
                if (sInstance == null) {
                    sInstance = new DisposablePool();
                }
            }
        }
        return sInstance;
    }


    /**
     * 根据tagName管理订阅【注册订阅信息】
     * @param tagName 标志
     * @param disposable 订阅信息
     */
    public Disposable add(@NonNull Object tagName, Disposable disposable) {
		/* 订阅管理 */
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            maps.put(tagName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * 根据tagName管理订阅【注册订阅信息】
     * @param disposable 订阅信息
     * @param tagName 标志
     *
     */
    public Disposable add(Disposable disposable, @NonNull Object tagName) {
		/* 订阅管理 */
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            maps.put(tagName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * 取消订阅【取消标志内所有订阅信息】
     * @param tagName 标志
     */
    public void remove(@NonNull Object tagName) {
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.dispose(); //取消订阅
            maps.remove(tagName);
        }
    }

    /**
     * 取消订阅【单个订阅取消】
     * @param tagName 标志
     * @param disposable 订阅信息
     */
    public void remove(@NonNull Object tagName, Disposable disposable) {
        CompositeDisposable compositeDisposable = maps.get(tagName);
        if (compositeDisposable != null) {
            compositeDisposable.remove(disposable);
            if (compositeDisposable.size() == 0) {
                maps.remove(tagName);
            }
        }
    }

    /**
     * 取消所有订阅
     */
    public void removeAll() {
        Iterator<Map.Entry<Object, CompositeDisposable>> it = maps.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = it.next();
            CompositeDisposable compositeDisposable = entry.getValue();
            if (compositeDisposable != null) {
                compositeDisposable.dispose(); //取消订阅
                it.remove();
            }
        }
        maps.clear();
    }


    /**
     * 取消订阅
     * @param disposable 订阅信息
     */
    public static void unsubscribe(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
