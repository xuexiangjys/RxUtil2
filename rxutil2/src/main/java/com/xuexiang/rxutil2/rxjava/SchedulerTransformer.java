package com.xuexiang.rxutil2.rxjava;

import com.xuexiang.rxutil2.rxjava.scheduler.SchedulerType;

import org.reactivestreams.Publisher;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换
 *
 * @author xuexiang
 * @since 2018/6/12 下午11:25
 */
public class SchedulerTransformer<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T> , CompletableTransformer {

    /**
     * 线程类型
     */
    private SchedulerType mSchedulerType;

    public SchedulerTransformer(SchedulerType schedulerType) {
        mSchedulerType = schedulerType;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        switch (mSchedulerType) {
            case _main:
                upstream.observeOn(AndroidSchedulers.mainThread());
                break;
            case _io:
                upstream.observeOn(Schedulers.io());
                break;
            case _io_main:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case _io_io:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            default:
                break;
        }
        return upstream;
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        switch (mSchedulerType) {
            case _main:
                upstream.observeOn(AndroidSchedulers.mainThread());
                break;
            case _io:
                upstream.observeOn(Schedulers.io());
                break;
            case _io_main:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case _io_io:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            default:
                break;
        }
        return upstream;
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        switch (mSchedulerType) {
            case _main:
                upstream.observeOn(AndroidSchedulers.mainThread());
                break;
            case _io:
                upstream.observeOn(Schedulers.io());
                break;
            case _io_main:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case _io_io:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            default:
                break;
        }
        return upstream;
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        switch (mSchedulerType) {
            case _main:
                upstream.observeOn(AndroidSchedulers.mainThread());
                break;
            case _io:
                upstream.observeOn(Schedulers.io());
                break;
            case _io_main:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case _io_io:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            default:
                break;
        }
        return upstream;
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        switch (mSchedulerType) {
            case _main:
                upstream.observeOn(AndroidSchedulers.mainThread());
                break;
            case _io:
                upstream.observeOn(Schedulers.io());
                break;
            case _io_main:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case _io_io:
                upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
                break;
            default:
                break;
        }
        return upstream;
    }
}
