package com.xuexiang.rxutil2.rxjava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Rx常用操作符集合 【使用compose操作符】
 *
 * @author xuexiang
 * @since 2018/6/10 下午7:08
 */
public final class RxOperationUtils {

    private RxOperationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 【定期发射】， 用于解决快速点击。只取一定时间间隔发射的第一个数据<br>
     * 使用compose操作符
     *
     * @param duration 间隔时间
     * @param unit     时间的单位
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _throttleFirst(final long duration, final TimeUnit unit) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.throttleFirst(duration, unit);
            }
        };
    }

    /**
     * 【超时发射】，用于像TextWatcher这种频繁变化的事件。只取两次数据的发射间隔大于指定时间的数据<br>
     * <p>【发射数据时，如果两次数据的发射间隔小于指定时间，就会丢弃前一次的数据,直到指定时间内都没有新数据发射时才进行发射】</p>
     *
     * @param timeout 间隔时间
     * @param unit    时间的单位
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> _debounce(final long timeout, final TimeUnit unit) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.debounce(timeout, unit);
            }
        };
    }

}
