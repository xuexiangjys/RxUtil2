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

package com.xuexiang.rxutil2demo.fragment.rxbus;

import android.view.View;

import com.xuexiang.rxutil2.rxbus.RxBusUtils;
import com.xuexiang.rxutil2demo.R;
import com.xuexiang.rxutil2demo.entity.Event;
import com.xuexiang.rxutil2demo.entity.EventKey;
import com.xuexiang.rxutil2demo.base.BaseFragment;
import com.xuexiang.rxutil2demo.util.ThreadPoolManager;

import butterknife.OnClick;

/**
 * RxBus演示示例
 * @author xuexiang
 * @date 2018/3/3 下午5:10
 */
public class RxBusControlFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rxbus_control;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_send_data, R.id.btn_send_nodata, R.id.btn_back_mainthread, R.id.btn_back_normal, R.id.btn_one_by_one, R.id.btn_one_by_more, R.id.btn_clear_all})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_data:
                RxBusUtils.get().post(EventKey.EVENT_HAVE_DATA, new Event(EventKey.EVENT_HAVE_DATA, "这里携带的是数据"));
                break;
            case R.id.btn_send_nodata:
                RxBusUtils.get().postRxEvent(EventKey.EVENT_NO_DATA);
                break;
            case R.id.btn_back_mainthread:
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        RxBusUtils.get().post(EventKey.EVENT_BACK_MAINTHREAD);
                    }
                });
                break;
            case R.id.btn_back_normal:
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        RxBusUtils.get().post(EventKey.EVENT_BACK_NORMAL);
                    }
                });
                break;
            case R.id.btn_one_by_one:
                RxBusUtils.get().post(EventKey.EVENT_ONE_BY_ONE);
                break;
            case R.id.btn_one_by_more:
                RxBusUtils.get().post(EventKey.EVENT_ONE_BY_MORE);
                break;
            case R.id.btn_clear_all:
                RxBusUtils.get().post(EventKey.EVENT_CLEAR);
                break;
            default:
                break;
        }
    }
}
