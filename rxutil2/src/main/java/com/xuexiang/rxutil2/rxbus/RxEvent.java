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

package com.xuexiang.rxutil2.rxbus;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * 万能的事件对象
 *
 * @author xuexiang
 * @date 2018/3/26 下午11:01
 */
public class RxEvent {

    /**
     * 事件的名称（必须）
     */
    private String mName;
    /**
     * 事件的类型
     */
    private Object mType;
    /**
     * 事件携带的数据
     */
    private Object mData;

    /**
     * 构造方法
     *
     * @param name 事件的名称
     */
    public RxEvent(String name) {
        mName = name;
    }

    /**
     * 构造方法
     *
     * @param name 事件的名称
     * @param data 事件携带的数据
     */
    public RxEvent(String name, Object data) {
        mName = name;
        mData = data;
    }

    /**
     * 构造方法
     *
     * @param name 事件的名称
     * @param type 事件的类型
     * @param data 事件携带的数据
     */
    public RxEvent(String name, Object type, Object data) {
        mName = name;
        mType = type;
        mData = data;
    }

    public Object getType() {
        return mType;
    }

    public RxEvent setType(Object type) {
        mType = type;
        return this;
    }

    public Object getData() {
        return mData;
    }

    public RxEvent setData(Object value) {
        mData = value;
        return this;
    }

    /**
     * 获取事件名
     *
     * @return
     */
    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return "[RxEvent] { \n" +
                "  mName:" + mName + "\n" +
                "  mType:" + RxEvent.toString(mType) + "\n" +
                "  mData:" + RxEvent.toString(mData) + "\n" +
                "}";
    }

    /**
     * 判断是否是指定的事件
     *
     * @param eventType
     * @return
     */
    public boolean isEvent(Object eventType) {
        return Objects.equals(mType, eventType);
    }


    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }
}
