package com.hebe.frameworklearn.java;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hebe.frameworklearn.R;
import com.hebe.frameworklearn.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Test1Activity extends BaseActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.helloWorld);
        textView.setText("Test1Activity");
        EventBus.getDefault().register(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().postSticky("haha");
                        startActivity(new Intent(Test1Activity.this,Test2Activity.class));
                    }
                }).start();

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onresponse(String msg){
        Log.i("event test",msg);
        textView.setText(msg);
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
