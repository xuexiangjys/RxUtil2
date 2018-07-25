# RxUtil2
[![RxUtil2][rxSvg]][rx]  [![api][apiSvg]][api]

一个实用的RxJava2工具类库。

> 如果你习惯RxJava1，请移步[RxUtil](https://github.com/xuexiangjys/RxUtil)

## 关于我
[![github](https://img.shields.io/badge/GitHub-xuexiangjys-blue.svg)](https://github.com/xuexiangjys)   [![csdn](https://img.shields.io/badge/CSDN-xuexiangjys-green.svg)](http://blog.csdn.net/xuexiangjys)

## 特征

* RxBus 支持多事件定义，支持数据携带，支持全局和局部的事件订阅和注销。
* 订阅池管理。
* 支持非侵入式的订阅生命周期绑定。
* 线程调度辅助工具。
* RxBinding 使用工具类。
* RxJava常用方法工具类。

## 1、演示（请star支持）

### 1.1、RxBus
![](https://github.com/xuexiangjys/RxUtil/blob/master/img/rxbus.gif)


## 2、如何使用
目前支持主流开发工具AndroidStudio的使用，直接配置build.gradle，增加依赖即可.

### 2.1、Android Studio导入方法，添加Gradle依赖

先在项目根目录的 build.gradle 的 repositories 添加:
```
allprojects {
     repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

然后在dependencies添加:

```
dependencies {
   ...
   implementation 'io.reactivex.rxjava2:rxjava:2.1.12'
   implementation 'io.reactivex.rxjava2:rxandroid:2.0.2'
   //rxbinding的sdk
   implementation 'com.jakewharton.rxbinding2:rxbinding:2.1.1'

   implementation 'com.github.xuexiangjys:RxUtil2:1.1.5'
}
```
### 3.1、RxBus使用

#### 3.1.1、事件注册订阅

1.使用`RxBusUtils.get().onMainThread`方法注册事件，并指定订阅发生在主线程。

```
RxBusUtils.get().onMainThread(EventKey.EVENT_HAVE_DATA, Event.class, new Consumer<Event>() {
    @Override
    public void accept(Event event) throws Exception {
        showContent(EventKey.EVENT_HAVE_DATA, event.toString());
    }
});
```
2.使用`RxBusUtils.get().on`方法注册事件，订阅所在线程为事件发生线程，也可指定订阅发生的线程。

```
RxBusUtils.get().on(EventKey.EVENT_BACK_NORMAL, String.class, new Consumer<String>() {
    @Override
    public void accept(String eventName) throws Exception {
        final String msg = "事件Key:" + EventKey.EVENT_BACK_NORMAL + "\n   EventName:" + eventName + ", 当前线程状态： " + Event.getLooperStatus();
        showContent(msg);
    }
});
```

3.与RxBus1相比，使用RxJava2的RxBus2需要指定接收数据的类型，但如果使用默认的`RxEvent`进行事件注册, 就不需要指定类型了。

```
RxBusUtils.get().onMainThread(EventKey.EVENT_NO_DATA, new Consumer<RxEvent>() {
    @Override
    public void accept(RxEvent rxEvent) throws Exception {
        showContent(rxEvent.toString());
    }
});
```


#### 3.1.2、事件发送

1.使用`RxBusUtils.get().post(Object eventName)`发送不带数据的事件。
```
RxBusUtils.get().post(EventKey.EVENT_NO_DATA);

RxBusUtils.get().postRxEvent(EventKey.EVENT_NO_DATA); //发送使用RxEvent注册的事件
```

2.使用`RxBusUtils.get().post(Object eventName, Object content)`发送携带数据的事件。
```
RxBusUtils.get().post(EventKey.EVENT_HAVE_DATA, new Event(EventKey.EVENT_HAVE_DATA, "这里携带的是数据"));
RxBusUtils.get().post(EventKey.EVENT_HAVE_DATA, true);
```

#### 3.1.3、事件注销

1.使用`RxBusUtils.get().unregisterAll(Object eventName)`取消事件的所有订阅并注销事件。
```
RxBusUtils.get().unregisterAll(EventKey.EVENT_HAVE_DATA);
```

2.使用`RxBusUtils.get().unregister(Object eventName, SubscribeInfo subscribeInfo)`取消事件的某个指定订阅。
SubscribeInfo是事件注册订阅后返回的订阅信息。如果在取消该订阅后，该事件如无其他订阅，便自动注销该事件。
```
RxBusUtils.get().unregister(EventKey.EVENT_CLEAR, mSubscribeInfo);
```

### 3.2、RxJavaUtils使用

#### 3.2.1、线程任务

1.RxIOTask：在io线程中操作的任务
```
RxJavaUtils.doInIOThread(new RxIOTask<String>("我是入参123") {
    @Override
    public Void doInIOThread(String s) {
        Log.e(TAG, "[doInIOThread]  " + getLooperStatus() + ", 入参:" + s);
        return null;
    }
});
```

2.RxUITask：在UI线程中操作的任务

```
RxJavaUtils.doInUIThread(new RxUITask<String>("我是入参456") {
    @Override
    public void doInUIThread(String s) {
        Log.e(TAG, "[doInUIThread]  " + getLooperStatus() + ", 入参:" + s);
    }
});
```

3.RxAsyncTask：在IO线程中执行耗时操作 执行完成后在UI线程中订阅的任务。
```
RxJavaUtils.executeAsyncTask(new RxAsyncTask<String, Integer>("我是入参789") {
    @Override
    public Integer doInIOThread(String s) {
        Log.e(TAG, "[doInIOThread]  " + getLooperStatus() + ", 入参:" + s);
        return 12345;
    }

    @Override
    public void doInUIThread(Integer integer) {
        Log.e(TAG, "[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
    }
});
```

4.RxIteratorTask:遍历集合或者数组的任务，在IO线程中执行耗时操作 执行完成后在UI线程中订阅的任务。
```
RxJavaUtils.executeRxIteratorTask(new RxIteratorTask<String, Integer>(new String[]{"123", "456", "789"}) {
    @Override
    public Integer doInIOThread(String s) {
        RxLog.e("[doInIOThread]" + getLooperStatus() + ", 入参:" + s);
        return Integer.parseInt(s);
    }

    @Override
    public void doInUIThread(Integer integer) {
        RxLog.e("[doInUIThread]  " + getLooperStatus() + ", 入参:" + integer);
    }
});
```

#### 3.2.2、订阅者Subscriber

1.SimpleSubscriber：简单的订阅者，已对错误进行捕获处理，并对生命周期进行日志记录。可设置IExceptionHandler接口自定义错误处理，设置ILogger接口自定义日志记录。

2.ProgressLoadingSubscriber：带进度条加载的订阅者，实现`IProgressLoader`接口可自定义加载方式。
```
Observable.just("加载完毕！")
        .delay(3, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new ProgressLoadingSubscriber<String>(mProgressLoader) {
            @Override
            public void onNext(String s) {
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
```

### 3.3、DisposablePool使用

DisposablePool：RxJava的订阅池

1.增加订阅：`add(@NonNull Object tagName, Disposable disposable)` 或者 `add(Disposable disposable, @NonNull Object tagName)`
```
DisposablePool.get().add(RxJavaUtils.polling(5, new Consumer<Long>() {
    @Override
    public void accept(Long o) throws Exception {
        Toast.makeText(RxJavaActivity.this, "正在监听", Toast.LENGTH_SHORT).show();
    }
}), "polling");
```

2.取消订阅：`remove(@NonNull Object tagName)`、`remove(@NonNull Object tagName, Disposable disposable)`、`removeAll()`
```
DisposablePool.get().remove("polling");
```

### 3.4、RxBindingUtils使用

1.setViewClicks:设置点击事件
```
RxBindingUtils.setViewClicks(mBtnClick, 5, TimeUnit.SECONDS, new Action1<Object>() {
    @Override
    public void call(Object o) {
        toast("触发点击");
    }
});
```

2.setItemClicks:设置条目点击事件


### 3.5、RxSchedulerUtils使用

1. 订阅发生在主线程 （  ->  -> main)

```
.compose(RxSchedulerUtils.<T>_main_o())  //Observable使用
.compose(RxSchedulerUtils.<T>_main_f())  //Flowable使用

```

2. 订阅发生在io线程 （  ->  -> io)

```
.compose(RxSchedulerUtils.<T>_io_o())  //Observable使用
.compose(RxSchedulerUtils.<T>_io_f())  //Flowable使用

```

3. 处理在io线程，订阅发生在主线程（ -> io -> main)

```
.compose(RxSchedulerUtils.<T>_io_main_o()) //Observable使用
.compose(RxSchedulerUtils.<T>_io_main_f()) //Flowable使用

```

4. 处理在io线程，订阅也发生在io线程（ -> io -> io)

```
.compose(RxSchedulerUtils.<T>_io_io_o()) //Observable使用
.compose(RxSchedulerUtils.<T>_io_io_f()) //Flowable使用

```

5. 自定义线程池

由于`Schedulers.io()`内部使用的是`CachedWorkerPool`，而他最终创建线程池的方法是`newScheduledThreadPool`，它是一个核心只有1个线程，有效时间为60s，但是线程池的线程容纳数量是`Integer.MAX_VALUE`的线程池。

如果线程在执行的过程中发生了长时间的阻塞，导致线程一直在工作状态的话，线程池将无法回收线程，只能不断地创建线程，最终有可能造成线程创建的数量过多，导致程序OOM。

使用`RxSchedulerUtils.setIOExecutor`可以替换工具类中使用的io线程：

```
RxSchedulerUtils.setIOExecutor(AppExecutors.get().poolIO());
```

### 3.6、RxLifecycle使用

1.使用`RxLifecycle.injectRxLifecycle`进行生命周期的绑定。

（1）在Activity的`onCreate`方法中进行注入和生命周期绑定

```
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    RxLifecycle.injectRxLifecycle(this);
}
```
（2）当然，如果你嫌麻烦，可以在Application的`onCreate`方法中进行注入和生命周期绑定。

```
RxLifecycle.injectRxLifecycle(this);
```

2.使用`compose`将订阅绑定至生命周期。

使用`RxLifecycle.with`可以获取生命周期管理者`LifecycleManager`，通过它我们可以将订阅绑定至生命周期。

* `bindToActivityLifecycle`:绑定到特定的Activity生命周期进行订阅注销

* `bindToLifecycle`:自动绑定Activity生命周期进行订阅注销

* `bindOnDestroy`:绑定到Activity的OnDestroy进行订阅注销

```
RxJavaUtils.polling(5)
        .compose(RxLifecycle.with(this).<Long>bindToLifecycle())
        .subscribe(new SimpleSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                toast(" 正在监听 :" + aLong);
            }
        });
```

## 联系方式

[![](https://img.shields.io/badge/点击一键加入QQ群-602082750-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=9922861ef85c19f1575aecea0e8680f60d9386080a97ed310c971ae074998887)

![](https://github.com/xuexiangjys/XPage/blob/master/img/qq_group.jpg)

[rxSvg]: https://img.shields.io/badge/RxUtil2-1.1.5-brightgreen.svg
[rx]: https://github.com/xuexiangjys/RxUtil2
[apiSvg]: https://img.shields.io/badge/API-14+-brightgreen.svg
[api]: https://android-arsenal.com/api?level=14

