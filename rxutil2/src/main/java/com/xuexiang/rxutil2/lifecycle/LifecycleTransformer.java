package com.xuexiang.rxutil2.lifecycle;

import org.reactivestreams.Publisher;

import io.reactivex.BackpressureStrategy;
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
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import static com.xuexiang.rxutil2.lifecycle.ActivityLifecycle.onDestroy;
import static com.xuexiang.rxutil2.lifecycle.ActivityLifecycle.onPause;
import static com.xuexiang.rxutil2.lifecycle.ActivityLifecycle.onStop;

/**
 * 生命周期转化器
 *
 * @author xuexiang
 * @since 2018/6/11 上午12:50
 */
public class LifecycleTransformer<T> implements ObservableTransformer<T, T>, FlowableTransformer<T, T>, SingleTransformer<T, T>, MaybeTransformer<T, T>, CompletableTransformer {
    private Observable<?> mObservable;


    LifecycleTransformer(Observable<ActivityLifecycle> lifecycleObservable) {
        Observable<ActivityLifecycle> observable = lifecycleObservable.share();
        mObservable = Observable.combineLatest(observable.take(1).map(ACTIVITY_LIFECYCLE), observable.skip(1),
                new BiFunction<ActivityLifecycle, ActivityLifecycle, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull ActivityLifecycle ActivityLifecycle, @NonNull ActivityLifecycle ActivityLifecycle2) throws Exception {
                        return ActivityLifecycle.equals(ActivityLifecycle2);
                    }
                })
                .filter(new Predicate<Boolean>() {
                    @Override
                    public boolean test(@NonNull Boolean aBoolean) throws Exception {
                        return aBoolean;
                    }
                });

    }

    LifecycleTransformer(Observable<ActivityLifecycle> lifecycleObservable, final ActivityLifecycle ActivityLifecycle) {
        mObservable = lifecycleObservable
                .filter(new Predicate<ActivityLifecycle>() {
                    @Override
                    public boolean test(@NonNull ActivityLifecycle event) throws Exception {
                        return event.equals(ActivityLifecycle);
                    }
                })
                .take(1);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.takeUntil(mObservable);
    }

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        return upstream.takeUntil(mObservable.toFlowable(BackpressureStrategy.LATEST));
    }

    @Override
    public SingleSource<T> apply(Single<T> upstream) {
        return upstream.takeUntil(mObservable.firstOrError());
    }

    @Override
    public MaybeSource<T> apply(Maybe<T> upstream) {
        return upstream.takeUntil(mObservable.firstElement());
    }

    @Override
    public CompletableSource apply(Completable upstream) {
        return Completable.ambArray(upstream);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LifecycleTransformer<?> that = (LifecycleTransformer<?>) o;

        return mObservable.equals(that.mObservable);
    }

    @Override
    public int hashCode() {
        return mObservable.hashCode();
    }

    @Override
    public String toString() {
        return "LifecycleTransformer{" +
                "mObservable=" + mObservable +
                '}';
    }


    // Figures out which corresponding next lifecycle event in which to unsubscribe, for Activities
    private static final Function<ActivityLifecycle, ActivityLifecycle> ACTIVITY_LIFECYCLE =
            new Function<ActivityLifecycle, ActivityLifecycle>() {
                @Override
                public ActivityLifecycle apply(@NonNull ActivityLifecycle lastEvent) throws Exception {
                    switch (lastEvent) {
                        case onCreate:
                            return onDestroy;
                        case onStart:
                            return onStop;
                        case onResume:
                            return onPause;
                        case onPause:
                            return onStop;
                        case onStop:
                            return onDestroy;
                        case onDestroy:
                            throw new IllegalStateException("Cannot injectRxLifecycle to Activity lifecycle when outside of it.");
                        default:
                            throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };
}
