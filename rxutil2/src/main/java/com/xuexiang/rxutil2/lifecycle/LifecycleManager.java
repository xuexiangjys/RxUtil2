package com.xuexiang.rxutil2.lifecycle;

import io.reactivex.Observable;

/**
 * 生命周期管理者，绑定生命周期
 *
 * @author xuexiang
 * @since 2018/6/11 上午12:49
 */
public interface LifecycleManager {

    /**
     * 获取Activity绑定的生命周期
     * @return
     */
    Observable<ActivityLifecycle> getActivityLifecycle();

    /**
     * 绑定到特定的Activity生命周期进行订阅注销
     * @param activityLifecycle
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToActivityLifecycle(ActivityLifecycle activityLifecycle);

    /**
     * 自动绑定Activity生命周期进行订阅注销
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindToLifecycle();

    /**
     * 绑定到Activity的OnDestroy进行订阅注销
     * @param <T>
     * @return
     */
    <T> LifecycleTransformer<T> bindOnDestroy();

}
