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

import android.widget.Button;
import android.widget.EditText;

import com.xuexiang.rxutil2.RxBindingUtils;
import com.xuexiang.rxutil2.rxjava.DisposablePool;
import com.xuexiang.rxutil2demo.R;
import com.xuexiang.rxutil2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author xuexiang
 * @date 2018/3/11 下午11:39
 */
public class RxBindingActivity extends BaseActivity {

    @BindView(R.id.btn_click)
    Button mBtnClick;

    @BindView(R.id.et_input)
    EditText mEtInput;

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_rxbinding;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {
        RxBindingUtils.setViewClicks(mBtnClick, 5, TimeUnit.SECONDS, new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                toast("触发点击");
            }
        });


        DisposablePool.get().add(RxBindingUtils.textChanges(mEtInput, 1, TimeUnit.SECONDS, new Consumer<CharSequence>() {
            @Override
            public void accept(CharSequence charSequence) throws Exception {
                toast("输入内容:" + charSequence);
            }
        }), "textChanges");
    }

    @Override
    protected void onDestroy() {
        DisposablePool.get().remove("textChanges");
        super.onDestroy();
    }
}
