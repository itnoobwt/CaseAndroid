package com.example.cameraapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadActivity extends AppCompatActivity
{

    @BindView(R.id.btn_thread)
    Button btnThread;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_thread)
    public void setBtnThread(View view){
        pool();
    }

    public void pool()
    {
        Toast.makeText(ThreadActivity.this, "123", Toast.LENGTH_SHORT).show();
        //创建一个线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(ThreadActivity.this, "321", Toast.LENGTH_SHORT).show();
            }
        }, 1, TimeUnit.SECONDS);
    }

    public void all()
    {
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {

            }
        };
    }
}
