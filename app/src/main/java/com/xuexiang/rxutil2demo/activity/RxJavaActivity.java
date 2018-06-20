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

package com.xuexiang.rxutil2demo.activity;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xuexiang.rxutil2.lifecycle.RxLifecycle;
import com.xuexiang.rxutil2.logs.RxLog;
import com.xuexiang.rxutil2.rxjava.DisposablePool;
import com.xuexiang.rxutil2.rxjava.RxJavaUtils;
import com.xuexiang.rxutil2.rxjava.task.RxAsyncTask;
import com.xuexiang.rxutil2.rxjava.task.RxIOTask;
import com.xuexiang.rxutil2.rxjava.task.RxIteratorTask;
import com.xuexiang.rxutil2.rxjava.task.RxUITask;
import com.xuexiang.rxutil2.subsciber.ProgressDialogLoader;
import com.xuexiang.rxutil2.subsciber.ProgressLoadingSubscriber;
import com.xuexiang.rxutil2.subsciber.SimpleSubscriber;
import com.xuexiang.rxutil2.subsciber.impl.IProgressLoader;
import com.xuexiang.rxutil2demo.R;
import com.xuexiang.rxutil2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.SafeSubscriber;

/**
 * RxJavaUtils演示示例
 *
 * @author xuexiang
 * @date 2018/3/8 下午3:37
 */
public class RxJavaActivity extends BaseActivity {
    private final static String TAG = "RxJavaActivity";

    private IProgressLoader mProgressLoader;

    @BindView(R.id.btn_count_down)
    Button mBtnCountDown;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected void initViews() {
        mProgressLoader = new ProgressDialogLoader(this, "正在加载数据，请稍后...");
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_do_in_io, R.id.btn_do_in_ui, R.id.btn_do_in_io_ui, R.id.btn_loading, R.id.btn_polling, R.id.btn_count_down, R.id.btn_foreach})
    void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_do_in_io:
                RxJavaUtils.doInIOThread(new RxIOTask<String>("我是入参123") {
                    @Override
                    public Void doInIOThread(String s) {
                        Log.e(TAG, "[doInIOThread]  " + getLooperStatus() + ", 入参:" + s);
                        return null;
                    }
                });
                break;
            case R.id.btn_do_in_ui:
                RxJavaUtils.doInUIThread(new RxUITask<String>("我是入参456") {
                    @Override
                    public void doInUIThread(String s) {
                        Log.e(TAG, "[doInUIThread]  " + getLooperStatus() + ", 入参:" + s);
                    }
                });
                break;
            case R.id.btn_do_in_io_ui:
//                RxJavaUtils.executeAsyncTask2("我是入参789", new Function<String, Integer>() {
//                    @Override
//                    public Integer apply(String s) throws Exception {
//                        Log.e(TAG, "[doInIOThread]  " + getLooperStatus() + ", 入参:" + s);
//                        return 12345;
//                    }
//                }, new SimpleSubscriber<Integer>() {
//                    @Override
//                    public void onSuccess(Integer integer) {
//                        Log.e(TAG, "[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
//                    }
//                });
//                RxJavaUtils.executeAsyncTask(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer) throws Exception {
//                        Log.e(TAG, "[doInIOThread]  " + getLooperStatus());
//                        return 12345;
//                    }
//                }).subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
//                    }
//                });

//                RxJavaUtils.executeAsyncTask2(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer) throws Exception {
//                        Log.e(TAG, "[doInIOThread]  " + getLooperStatus());
//                        return 12345;
//                    }
//                }, new SimpleSubscriber<Integer>() {
//                    @Override
//                    public void onSuccess(Integer integer) {
//                        Log.e(TAG, "[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
//                    }
//                });

                RxJavaUtils.executeAsyncTask(new RxAsyncTask<String, Integer>("我是入参789") {
                    @Override
                    public Integer doInIOThread(String s) {
                        Log.e(TAG, "[doInIOThread]  " + getLooperStatus() + ", 入参:" + s);
                        return 12345;
                    }

                    @Override
                    public void doInUIThread(Integer integer) {
                        Log.e(TAG, "[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
                    }
                });
                break;
            case R.id.btn_loading:
                RxJavaUtils.delay("加载完毕！", 3, TimeUnit.SECONDS, new ProgressLoadingSubscriber<String>(mProgressLoader) {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_polling:
                RxJavaUtils.polling(5)
                        .compose(RxLifecycle.with(this).<Long>bindToLifecycle())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long o) throws Exception {
                                toast("正在监听:" + o);
                            }
                        });
                break;
            case R.id.btn_count_down:
                DisposablePool.get().add(RxJavaUtils.countDown(30, new SimpleSubscriber<Long>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mBtnCountDown.setEnabled(false);
                    }

                    @Override
                    public void onSuccess(Long aLong) {
                        mBtnCountDown.setText(String.format("%s s后重新获取", aLong));
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mBtnCountDown.setText("重新获取");
                        mBtnCountDown.setEnabled(true);
                    }
                }), "countDown");

                break;
            case R.id.btn_foreach:
//                RxJavaUtils.foreach(new String[]{"123", "456", "789"}, new Function<String, Integer>() {
//                    @Override
//                    public Integer apply(String s) throws Exception {
//                        RxLog.e("[doInIOThread]" + getLooperStatus() + ", 入参:" + s);
//                        return Integer.parseInt(s);
//                    }
//
//                }, new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        RxLog.e("[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
//                    }
//                });
                RxJavaUtils.executeRxIteratorTask(new RxIteratorTask<String, Integer>(new String[]{"123", "456", "789"}) {
                    @Override
                    public Integer doInIOThread(String s) {
                        RxLog.e("[doInIOThread]" + getLooperStatus() + ", 入参:" + s);
                        return Integer.parseInt(s);
                    }

                    @Override
                    public void doInUIThread(Integer integer) {
                        RxLog.e("[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
                    }
                });
                break;
            default:
                break;
        }

    }

    /**
     * 获取当前线程的状态
     *
     * @return
     */
    public String getLooperStatus() {
        return "当前线程状态：" + (Looper.myLooper() == Looper.getMainLooper() ? "主线程" : "子线程");
    }


    @Override
    protected void onDestroy() {
        DisposablePool.get().remove("countDown");
        super.onDestroy();
    }
}
