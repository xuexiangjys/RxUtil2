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

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程调度工具
 *
 * @author XUE
 * @date 2017/7/3 16:01
 */
public final class RxSchedulerUtils {

    //==========================Flowable===========================//

    /**
     * 回到主线程
     *
     * @param flowable 被观察者
     */
    public static <T> Flowable<T> toMain(Flowable<T> flowable) {
        return flowable.observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 回到io线程
     *
     * @param flowable 被观察者
     */
    public static <T> Flowable<T> toIo(Flowable<T> flowable) {
        return flowable.observeOn(Schedulers.io());
    }

    /**
     * 订阅发生在主线程 （  ->  -> main)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _main_f() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return RxSchedulerUtils.toMain(flowable);
            }
        };
    }

    /**
     * 订阅发生在io线程 （  ->  -> io)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _io_f() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return RxSchedulerUtils.toIo(flowable);
            }
        };
    }

    /**
     * 处理在io线程，订阅发生在主线程（ -> io -> main)
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _io_main_f() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 处理在io线程，订阅也发生在io线程（ -> io -> io)
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> _io_io_f() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
            }
        };
    }

    //==========================Observable==========================//


    /**
     * 回到主线程
     *
     * @param observable 被观察者
     */
    public static <T> Observable<T> toMain(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 回到io线程
     *
     * @param observable 被观察者
     */
    public static <T> Observable<T> toIo(Observable<T> observable) {
        return observable.observeOn(Schedulers.io());
    }


    /**
     * 订阅发生在主线程 （  ->  -> main)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _main_o() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return RxSchedulerUtils.toMain(tObservable);
            }
        };
    }

    /**
     * 订阅发生在io线程 （  ->  -> io)
     * 使用compose操作符
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _io_o() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return RxSchedulerUtils.toIo(tObservable);
            }
        };
    }


    /**
     * 处理在io线程，订阅发生在主线程（ -> io -> main)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _io_main_o() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 处理在io线程，订阅也发生在io线程（ -> io -> io)
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _io_io_o() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
            }
        };
    }


}
