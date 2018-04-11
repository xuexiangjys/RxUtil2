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

import android.view.View;

import com.xuexiang.rxutil2demo.R;
import com.xuexiang.rxutil2demo.base.BaseActivity;

import butterknife.OnClick;

/**
 * @author xuexiang
 * @date 2018/3/8 下午3:24
 */
public class MainActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_rxbus, R.id.btn_rxjava, R.id.btn_rxbinding})
    void OnClick(View v) {
        switch(v.getId()) {
            case R.id.btn_rxbus:
                startActivity(RxBusActivity.class);
                break;
            case R.id.btn_rxjava:
                startActivity(RxJavaActivity.class);
                break;
            case R.id.btn_rxbinding:
                startActivity(RxBindingActivity.class);
                break;
            default:
                break;
        }

    }
}
