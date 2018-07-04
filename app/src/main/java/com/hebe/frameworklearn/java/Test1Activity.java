package com.hebe.frameworklearn.java;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hebe.frameworklearn.R;
import com.hebe.frameworklearn.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action6;

public class Test1Activity extends BaseActivity {
    Button button;
    public static final String TAG = "Test1Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        button = findViewById(R.id.helloWorld);
        button.setText("Test1Activity");
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
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("jaj");
                subscriber.onError(new Exception("exception"));
//                subscriber.onNext("jaj1");
//                subscriber.onNext("jaj2");
                subscriber.onCompleted();
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG,s);
            }
        };
//        observable.subscribe(observer);
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
//        observable.subscribe(subscriber);
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
