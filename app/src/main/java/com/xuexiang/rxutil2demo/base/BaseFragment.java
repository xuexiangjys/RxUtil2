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

package com.xuexiang.rxutil2demo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.leakcanary.RefWatcher;
import com.xuexiang.rxutil2demo.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基础Fragment
 *
 * @author xuexiang
 * @date 2018/1/11 上午10:13
 */
public abstract class BaseFragment extends Fragment {
    private View mRootView;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initArgs();
        initViews();
        initListener();
        return mRootView;
    }

    /**
     * 初始化参数
     */
    protected void initArgs() {

    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    protected <T extends View> T $(int id) {
        return (T) mRootView.findViewById(id);
    }

    /**
     * 设置背景颜色
     * @param color
     */
    protected void setBackgroundColor(int color) {
        mRootView.setBackgroundColor(getResources().getColor(color));
    }
    /**
     * 布局的资源id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    protected void runOnUiThread(Runnable action) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(action);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getRefWatcher();
        refWatcher.watch(this);
    }
}
