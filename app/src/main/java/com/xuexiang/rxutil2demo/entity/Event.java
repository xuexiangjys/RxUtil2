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

package com.xuexiang.rxutil2demo.entity;

import android.os.Looper;

/**
 * 事件
 *
 * @author xx
 * @Date 2017-5-16 上午11:48:45
 */
public class Event {

    private String EventName;
    private String Content;

    public Event(String eventName, String content) {
        EventName = eventName;
        Content = content;
    }

    public String getEventName() {
        return EventName;
    }

    public String getContent() {
        return Content;
    }

    public Event setEventName(String eventName) {
        EventName = eventName;
        return this;
    }

    public Event setContent(String content) {
        Content = content;
        return this;
    }

    @Override
    public String toString() {
        return "携带的数据： {EventName:" + EventName + ", Content:" + Content + "}, 当前线程状态：" + getLooperStatus();
    }

    public static String getLooperStatus() {
        return Looper.myLooper() == Looper.getMainLooper() ? "主线程" : "子线程";
    }

}
