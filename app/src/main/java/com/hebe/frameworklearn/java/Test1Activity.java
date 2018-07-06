package com.hebe.frameworklearn.java;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hebe.frameworklearn.BuildConfig;
import com.hebe.frameworklearn.R;
import com.hebe.frameworklearn.base.BaseActivity;
import com.hebe.frameworklearn.bean.RxTestBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ObjectStreamException;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action6;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class Test1Activity extends BaseActivity {
    Button button;
    public static final String TAG = "Test1Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        button = findViewById(R.id.helloWorld);
        button.setText("Test1Activity");
        button.setText(BuildConfig.DEBUG+"");
        EventBus.getDefault().register(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post("test");
//                        startActivity(new Intent(Test1Activity.this,Test2Activity.class));
                    }
                }).start();

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onresponse(String msg){
        Log.i("event test",msg);
//        button.setText(msg);

        //一个string的observable 被观察
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG,Thread.currentThread().toString());
                subscriber.onNext("jaj");
                subscriber.onError(new Exception("exception"));
//                subscriber.onNext("jaj1");
//                subscriber.onNext("jaj2");
                subscriber.onCompleted();
            }
        });

        //一个string的观察者
        Observer<String> observerstr = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,Thread.currentThread().toString());
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,Thread.currentThread().toString());
                Log.d(TAG,"onError " + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,Thread.currentThread().toString());
                Log.d(TAG,s);
            }
        };
//        observable.subscribe(observer);

        //一个string的观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError "+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }
        };

        //action用法 一个简单的观察者
        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"action1 call "+s);
            }
        };
        Action2<String,String> action2 = new Action2<String,String>() {
            @Override
            public void call(String s,String s1) {
                Log.d(TAG,"action1 call "+s +s1);
            }
        };
//        observable.subscribe(action1);

        Observable<RxTestBean> observablebean = Observable.create(new Observable.OnSubscribe<RxTestBean>() {
            @Override
            public void call(Subscriber<? super RxTestBean> subscriber) {
                Log.d(TAG,Thread.currentThread().toString());
                subscriber.onNext(new RxTestBean("哈罗",10));
                subscriber.onCompleted();
            }
        });

        Subscriber<RxTestBean> subscriberbean = new Subscriber<RxTestBean>() {

            @Override
            public void onCompleted() {
                Log.d(TAG,Thread.currentThread().toString());
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,Thread.currentThread().toString());
                Log.d(TAG,"onError " +e.getMessage());
            }

            @Override
            public void onNext(RxTestBean rxTestBean) {
                Log.d(TAG,Thread.currentThread().toString());
                Log.d(TAG,rxTestBean.toString());
            }
        };
        observablebean=observablebean.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
//        observablebean.subscribe(subscriberbean);


        //map
        //传统写法
        observablebean.map(new Func1<RxTestBean,String>(){
            @Override
            public String call(RxTestBean rxTestBean) {
                return null;
            }
        });
        //精简第一步
        observablebean.map((Func1<RxTestBean,String>) bean->{
            return bean.getName();
        });
        //第二步
        observablebean.map((Func1<RxTestBean,String>) bean->bean.getName());
        //精简第三步
        observablebean.map(bean->bean.getName());//.subscribe(observerstr);

//        Observable.from(new RxTestBean("213",1));//.subscribe(subscriberbean);


        //flatmap
        observablebean.flatMap(new Func1<RxTestBean, Observable<String>>() {
            @Override
            public Observable<String> call(RxTestBean rxTestBean) {
                return Observable.from(rxTestBean.getList());
            }
        }).subscribe(observerstr);

    }

    @Subscribe
    public void onresponse1(int msg){
        Log.i("event test",msg+" int");
    }

    @Subscribe
    public void onresponse3(String msg){
         Log.i("event test",msg+333);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
