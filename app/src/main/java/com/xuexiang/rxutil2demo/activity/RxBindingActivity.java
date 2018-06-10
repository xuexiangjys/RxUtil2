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

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.xuexiang.rxutil2.RxBindingUtils;
import com.xuexiang.rxutil2.rxjava.DisposablePool;
import com.xuexiang.rxutil2.subsciber.SimpleThrowableAction;
import com.xuexiang.rxutil2demo.R;
import com.xuexiang.rxutil2demo.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
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

    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

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

        DisposablePool.get().add(Observable.combineLatest(RxBindingUtils.textChanges(mEtUsername), RxBindingUtils.textChanges(mEtPassword), new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence charSequence, CharSequence charSequence2) {
                return !TextUtils.isEmpty(mEtUsername.getText()) && !TextUtils.isEmpty(mEtPassword.getText());
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception{
                mBtnLogin.setEnabled(aBoolean);
            }
        }, new SimpleThrowableAction("RxBindingActivity")), "combineLatest");


    }

    @Override
    protected void onDestroy() {
        DisposablePool.get().remove("textChanges");
        DisposablePool.get().remove("combineLatest");
        super.onDestroy();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        toast("登录");
    }
}
