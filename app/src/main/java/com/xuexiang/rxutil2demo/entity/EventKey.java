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
/** 
 * @author xx
 * @Date 2017-5-16 上午11:44:46 
 */
public class EventKey {

	/**
	 * 携带数据事件
	 */
	public final static String EVENT_HAVE_DATA = "event_have_data";
	/**
	 * 不携带事件
	 */
	public final static String EVENT_NO_DATA = "event_no_data";
	/**
	 * 事件返回主线程
	 */
	public final static String EVENT_BACK_MAINTHREAD = "event_back_mainthread";
	/**
	 * 事件普通返回
	 */
	public final static String EVENT_BACK_NORMAL = "event_back_normal";
	/**
	 * 一对一事件
	 */
	public final static String EVENT_ONE_BY_ONE = "event_one_by_one";
	/**
	 * 一对多事件
	 */
	public final static String EVENT_ONE_BY_MORE = "event_one_by_more";

	/**
	 * 清除事件
	 */
	public final static String EVENT_CLEAR = "event_clear";
}
