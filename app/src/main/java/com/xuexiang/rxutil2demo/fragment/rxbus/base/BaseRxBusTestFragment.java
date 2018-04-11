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

package com.xuexiang.rxutil2demo.fragment.rxbus.base;

import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.xuexiang.rxutil2.rxbus.RxBusUtils;
import com.xuexiang.rxutil2.rxbus.SubscribeInfo;
import com.xuexiang.rxutil2demo.R;
import com.xuexiang.rxutil2demo.base.BaseFragment;
import com.xuexiang.rxutil2demo.entity.EventKey;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * @author xuexiang
 * @date 2018/3/3 下午5:18
 */
public abstract class BaseRxBusTestFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @BindView(R.id.tv_content)
    protected TextView mTvContent;

    protected SubscribeInfo mSubscribeInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rxbus_test;
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }


    @Override
    protected void initListener() {
        mSubscribeInfo = RxBusUtils.get().onMainThread(EventKey.EVENT_CLEAR, String.class, new Consumer<String>() {
            @Override
            public void accept(String eventName) throws Exception {
                showContent("");
            }
        });

    }

    /**
     * 显示内容
     *
     * @param msg
     */
    public void showContent(String eventName, final String msg) {
        showContent("事件Key:" + eventName + "\n" + msg);
    }

    /**
     * 显示内容
     *
     * @param msg
     */
    public void showContent(final String msg) {
        if (isMainLooper()) {
            mTvContent.setText(msg);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvContent.setText(msg);
                }
            });
        }
    }

    public boolean isMainLooper() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public

    @OnClick({R.id.bt_cancel, R.id.bt_clear})
    void OnClick(View v) {
        switch(v.getId()) {
            case R.id.bt_cancel:
                onCancelEvent();
                break;
            case R.id.bt_clear:
                showContent("");
                break;
            default:
                break;
        }
    }

    protected abstract void onCancelEvent();

    @Override
    public void onDestroy() {
        onCancelEvent();
        super.onDestroy();
    }
}
