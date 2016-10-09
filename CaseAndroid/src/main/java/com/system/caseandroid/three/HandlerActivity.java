package com.system.caseandroid.three;

import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.system.caseandroid.R;

public class HandlerActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
    }

    public void sendHandler(View view){
        //http://blog.csdn.net/feiduclear_up/article/details/46840523   HandlerThread讲解
        HandlerThread thread = new HandlerThread("work");
        thread.start();
        Handler handler = new Handler( thread.getLooper(), new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message message)
            {
                switch (message.what){
                    case 1:
                        Log.e("handler","主线程"+message.obj+"   当前线程"+Thread.currentThread
                        ().getId());
                        break;
                    case 2:
                        Log.e("handler2","主线程"+message.obj+"   当前线程"+Thread.currentThread
                                ().getId());
                        break;
                }
                return false;
            }
        });
        Message s = new Message();
        s.obj = Thread.currentThread().getId();
        s.what = 1;
        handler.sendMessage(s);
        Message s1 = new Message();
        s1.obj = Thread.currentThread().getId();
        s1.what = 2;
        handler.sendMessage(s1);
    }

    public void sendHandlerThread(View v){
        new myThread().start();
    }




    class myThread extends Thread{
        @Override
        public void run()
        {
            super.run();
            Looper.prepare();  //创建一个looper对象,并且创建MessageQueue
            //Handler 作用是发送消息和处理消息
            Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg)
                {
                    super.handleMessage(msg);
                    switch (msg.what){
                        case 1:
                            Toast.makeText(HandlerActivity.this, "子线程Hanlder使用", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };

            //Message作用是 接收和处理的消息对象
            Message message = handler.obtainMessage(1);
            message.sendToTarget();
            Looper.loop(); //循环处理消息发送给Handler

        }
    }
}
