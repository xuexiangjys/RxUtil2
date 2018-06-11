package com.xuexiang.rxutil2.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.Field;

/**
 * RxLifecycle，自动绑定到Activity的生命周期中，自动进行订阅的注销
 *
 * @author xuexiang
 * @since 2018/6/11 上午1:07
 */
public final class RxLifecycle {

    private static final String FRAGMENT_TAG = "rx_lifecycle_tag";

    private RxLifecycle() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //=========================注入并绑定Activity的生命周期================================//

    /**
     * 注入并绑定Activity的生命周期<br>
     * use in {@link Activity} onCreate
     * <pre> {@code
     * public class BaseActivity extends AppCompatActivity {
     *      protected void onCreate(Bundle savedInstanceState) {
     *          super.onCreate(savedInstanceState);
     *          RxLifecycle.injectRxLifecycle(this);
     *      }
     * }
     * }</pre>
     *
     * @param activity
     */
    public static void injectRxLifecycle(Activity activity) {
        with(activity);
    }

    /**
     * 注入并绑定Activity的生命周期<br>
     *
     * @param object
     */
    private static void injectRxLifecycle(Object object) {
        if (object instanceof View) {
            with((View) object);
        } else {
            with(object);
        }
    }

    /**
     * 注入并绑定Activity的生命周期<br>
     * <p>
     * use in {@link Application} oncreate
     * <pre> {@code
     * public class RxLifecycleAPP extends Application {
     *      public void onCreate() {
     *          super.onCreate();
     *          RxLifecycle.injectRxLifecycle(this);
     *      }
     * }
     * }</pre>
     *
     * @param application
     */
    public static void injectRxLifecycle(Application application) {
        application.registerActivityLifecycleCallbacks(new ActivityRxLifecycleCallbacks());
    }

    //===========================获得生命周期绑定管理者，进行生命周期的绑定==============================//

    /**
     * 获得生命周期绑定管理者，进行生命周期的绑定
     *
     * @param activity
     * @return
     */
    public static LifecycleManager with(Activity activity) {
        if (activity instanceof FragmentActivity) {
            return with((FragmentActivity) activity);
        }
        FragmentManager fm = activity.getFragmentManager();
        Fragment fragment = fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new LifecycleFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
        return (LifecycleManager) fragment;
    }

    /**
     * 获得生命周期绑定管理者，进行生命周期的绑定
     *
     * @param activity
     * @return
     */
    private static LifecycleManager with(FragmentActivity activity) {
        android.support.v4.app.FragmentManager fm = activity.getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fm.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new LifecycleV4Fragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commitNowAllowingStateLoss();
        }

        return (LifecycleManager) fragment;
    }

    /**
     * 获得生命周期绑定管理者，进行生命周期的绑定
     *
     * @param fragment
     * @return
     */
    public static LifecycleManager with(Fragment fragment) {
        return with(fragment.getActivity());
    }

    /**
     * 获得生命周期绑定管理者，进行生命周期的绑定
     *
     * @param fragment
     * @return
     */
    public static LifecycleManager with(android.support.v4.app.Fragment fragment) {
        return with(fragment.getActivity());
    }

    /**
     * 获得生命周期绑定管理者，进行生命周期的绑定
     *
     * @param context ensure context can be cast {@link Activity}
     */
    public static LifecycleManager with(Context context) {
        if (context instanceof AppCompatActivity) {
            return with((FragmentActivity) context);
        }
        if (context instanceof Activity) {
            return with((Activity) context);
        }
        if (context instanceof ContextWrapper) {
            return with(((ContextWrapper) context).getBaseContext());
        }
        throw new ClassCastException(context.getClass().getSimpleName() + " can\'t cast Activity !");
    }

    public static LifecycleManager with(View view) {
        return with(view.getContext());
    }

    private static LifecycleManager with(Object object) {
        if (object instanceof Context) {
            return with((Context) object);
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value instanceof Context) {
                    return with((Context) value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new ClassCastException(object.getClass().getSimpleName() + " can\'t convert Context !");
    }

}
