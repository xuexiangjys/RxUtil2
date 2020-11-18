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

import androidx.annotation.NonNull;

import com.xuexiang.rxutil2.subsciber.SimpleThrowableAction;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * RxBus辅助工具类
 *
 * @author xuexiang
 * @since 2018/3/1 上午10:41
 */
public class RxBusUtils {
    private final static String TAG = "RxBusUtils";

    private static RxBusUtils sInstance;
    /**
     * 管理Subscribers订阅，防止内存泄漏
     * 事件订阅的订阅池，key：事件名， value：事件的订阅反馈信息
     */
    private ConcurrentHashMap<Object, CompositeDisposable> maps = new ConcurrentHashMap<>();

    private RxBusUtils() {

    }

    /**
     * 获取RxBus辅助工具类实例
     *
     * @return
     */
    public static RxBusUtils get() {
        if (sInstance == null) {
            synchronized (RxBusUtils.class) {
                if (sInstance == null) {
                    sInstance = new RxBusUtils();
                }
            }
        }
        return sInstance;
    }
    //===============================RxBus==================================//

    /**
     * RxBus注入监听（订阅发生在主线程）
     *
     * @param eventName 事件名
     * @param consumer  订阅动作
     */
    public SubscribeInfo<RxEvent> onMainThread(@NonNull Object eventName, Consumer<RxEvent> consumer) {
        return onMainThread(eventName, RxEvent.class, consumer, new SimpleThrowableAction(TAG));
    }


    /**
     * RxBus注入监听（订阅发生在主线程）
     *
     * @param eventName 事件名
     * @param clazz     接收数据的类型
     * @param consumer  订阅动作
     */
    public <T> SubscribeInfo<T> onMainThread(@NonNull Object eventName, Class<T> clazz, Consumer<T> consumer) {
        return onMainThread(eventName, clazz, consumer, new SimpleThrowableAction(TAG));
    }

    /**
     * RxBus注入监听（订阅发生在主线程）
     *
     * @param eventName     事件名
     * @param clazz         接收数据的类型
     * @param consumer      订阅动作
     * @param errorConsumer 错误订阅
     */
    public <T> SubscribeInfo<T> onMainThread(@NonNull Object eventName, Class<T> clazz, Consumer<T> consumer, Consumer<Throwable> errorConsumer) {
        Flowable<T> flowable = register(eventName, clazz); //注册后，返回订阅者
        /* 订阅管理 */
        SubscribeInfo<T> info = new SubscribeInfo<>(flowable);
        info.setDisposable(add(eventName, flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(consumer, errorConsumer)));
        return info;
    }

    //=======================================//

    /**
     * RxBus注入监听（订阅线程不变）
     *
     * @param eventName 事件名
     * @param consumer  订阅动作
     */
    public SubscribeInfo<RxEvent> on(@NonNull Object eventName, Consumer<RxEvent> consumer) {
        return on(eventName, RxEvent.class, consumer, new SimpleThrowableAction(TAG));
    }

    /**
     * RxBus注入监听（订阅线程不变）
     *
     * @param eventName 事件名
     * @param clazz     接收数据的类型
     * @param consumer  订阅动作
     */
    public <T> SubscribeInfo<T> on(@NonNull Object eventName, Class<T> clazz, Consumer<T> consumer) {
        return on(eventName, clazz, consumer, new SimpleThrowableAction(TAG));
    }

    /**
     * RxBus注入监听（订阅线程不变）
     *
     * @param eventName     事件名
     * @param clazz         接收数据的类型
     * @param consumer      订阅动作
     * @param errorConsumer 错误订阅
     */
    public <T> SubscribeInfo<T> on(@NonNull Object eventName, Class<T> clazz, Consumer<T> consumer, Consumer<Throwable> errorConsumer) {
        Flowable<T> Observable = register(eventName, clazz);//注册后，返回订阅者
        /* 订阅管理 */
        SubscribeInfo<T> info = new SubscribeInfo<>(Observable);
        info.setDisposable(add(eventName, Observable.subscribe(consumer, errorConsumer)));
        return info;
    }


    /**
     * RxBus注入监听（可指定订阅的线程）
     *
     * @param eventName 事件名
     * @param scheduler 指定订阅的线程
     * @param consumer  订阅动作
     */
    public SubscribeInfo<RxEvent> on(@NonNull Object eventName, Scheduler scheduler, Consumer<RxEvent> consumer) {
        return on(eventName, RxEvent.class, scheduler, consumer, new SimpleThrowableAction(TAG));
    }


    /**
     * RxBus注入监听（可指定订阅的线程）
     *
     * @param eventName 事件名
     * @param clazz     接收数据的类型
     * @param scheduler 指定订阅的线程
     * @param consumer  订阅动作
     */
    public <T> SubscribeInfo<T> on(@NonNull Object eventName, Class<T> clazz, Scheduler scheduler, Consumer<T> consumer) {
        return on(eventName, clazz, scheduler, consumer, new SimpleThrowableAction(TAG));
    }

    /**
     * RxBus注入监听（可指定订阅的线程）
     *
     * @param eventName     事件名
     * @param clazz         接收数据的类型
     * @param scheduler     指定订阅的线程
     * @param consumer      订阅动作
     * @param errorConsumer 错误订阅
     */
    public <T> SubscribeInfo<T> on(@NonNull Object eventName, Class<T> clazz, Scheduler scheduler, Consumer<T> consumer, Consumer<Throwable> errorConsumer) {
        Flowable<T> flowable = register(eventName, clazz);//注册后，返回订阅者
        /* 订阅管理 */
        SubscribeInfo<T> info = new SubscribeInfo<>(flowable);
        info.setDisposable(add(eventName, flowable.observeOn(scheduler).subscribe(consumer, errorConsumer)));
        return info;
    }

    //====================================================//

    /**
     * 单纯的Observables 和Subscribers管理
     *
     * @param eventName  事件名
     * @param disposable 订阅信息
     */
    public Disposable add(@NonNull Object eventName, Disposable disposable) {
        /* 订阅管理 */
        CompositeDisposable compositeDisposable = maps.get(eventName);
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            maps.put(eventName, compositeDisposable);
        }
        compositeDisposable.add(disposable);
        return disposable;
    }

    /**
     * 取消事件的所有订阅并注销事件
     *
     * @param eventName 事件名
     */
    public void unregisterAll(@NonNull Object eventName) {
        CompositeDisposable compositeDisposable = maps.get(eventName);
        if (compositeDisposable != null) {
            compositeDisposable.dispose(); //取消订阅
            maps.remove(eventName);
        }
        RxBus.get().unregisterAll(eventName);
    }

    /**
     * 取消事件的指定订阅
     *
     * @param eventName  事件名
     * @param disposable 订阅信息
     * @param flowable   订阅者
     */
    public void unregister(@NonNull Object eventName, Disposable disposable, Flowable flowable) {
        CompositeDisposable compositeDisposable = maps.get(eventName);
        if (compositeDisposable != null) {
            compositeDisposable.remove(disposable); //先取消特定的事件订阅
            if (compositeDisposable.size() == 0) {
                maps.remove(eventName);
                RxBus.get().unregisterAll(eventName); //没有订阅信息了，直接注销事件
            }
        }
        RxBus.get().unregister(eventName, flowable); //取消事件的订阅者
    }

    /**
     * 取消事件的指定订阅
     *
     * @param eventName     事件名
     * @param subscribeInfo 订阅信息
     */
    public void unregister(@NonNull Object eventName, SubscribeInfo subscribeInfo) {
        if (subscribeInfo != null) {
            unregister(eventName, subscribeInfo.getDisposable(), subscribeInfo.getFlowable());
        }
    }

    /**
     * 注册事件
     *
     * @param eventName 事件名
     * @param clazz     接收数据的类型
     * @param <T>
     * @return 订阅者
     */
    public <T> Flowable<T> register(@NonNull Object eventName, Class<T> clazz) {
        return RxBus.get().register(eventName, clazz);
    }

    /**
     * 发送指定的事件(不携带数据)
     *
     * @param eventName 事件名
     */
    public void post(@NonNull Object eventName) {
        RxBus.get().post(eventName);
    }

    /**
     * 发送指定的事件（携带数据）
     *
     * @param eventName 注册标识
     * @param content   发送的内容
     */
    public void post(@NonNull Object eventName, Object content) {
        RxBus.get().post(eventName, content);
    }

    //=================postRxEvent=================//

    /**
     * 发送指定的RxEvent事件
     *
     * @param rxEvent 发送的事件
     */
    public void postRxEvent(@NonNull RxEvent rxEvent) {
        RxBus.get().post(rxEvent.getName(), rxEvent);
    }

    /**
     * 发送指定的RxEvent事件（不携带数据）
     *
     * @param eventName 事件名
     */
    public void postRxEvent(@NonNull String eventName) {
        postRxEvent(new RxEvent(eventName));
    }

    /**
     * 发送指定的事件（携带数据）
     *
     * @param eventName 事件名
     * @param data      携带的数据
     */
    public void postRxEvent(@NonNull String eventName, Object data) {
        postRxEvent(new RxEvent(eventName, data));
    }

    /**
     * 发送指定的事件（携带数据）
     *
     * @param eventName 事件名
     * @param data      携带的数据
     */
    public void postRxEvent(@NonNull String eventName, Object type, Object data) {
        postRxEvent(new RxEvent(eventName, type, data));
    }


}
