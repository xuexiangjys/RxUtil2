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

package com.xuexiang.rxutil2.subsciber.impl;

/**
 * 进度条加载者实现接口
 * @author xuexiang
 * @date 2018/3/10 上午12:50
 */
public interface IProgressLoader {
    /**
     * 当前是否在加载
     * @return
     */
    boolean isLoading();

    /**
     * 更新加载提示信息
     * @param tipMessage
     * @return
     */
    void updateMessage(String tipMessage);

    /**
     * 显示加载界面
     */
    void showLoading();

    /**
     * 隐藏加载界面
     */
    void dismissLoading();

    /**
     * 设置是否可取消
     * @param cancelable
     */
    void setCancelable(boolean cancelable);

    /**
     * 设置取消的回掉监听
     * @param listener
     */
    void setOnProgressCancelListener(OnProgressCancelListener listener);
}
