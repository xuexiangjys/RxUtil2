package com.xuexiang.rxutil2.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 应用的生命周期
 *
 * @author xuexiang
 * @since 2018/6/11 上午1:09
 */
final class ActivityRxLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        RxLifecycle.injectRxLifecycle(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
