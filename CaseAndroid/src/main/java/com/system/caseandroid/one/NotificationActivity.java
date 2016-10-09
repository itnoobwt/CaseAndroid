package com.system.caseandroid.one;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.system.caseandroid.R;

public class NotificationActivity extends AppCompatActivity
{
    private final int NOTIFICATION_ID = 0x123;
    private NotificationManager manager;
    private Button btn_send,btn_del;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_del = (Button) findViewById(R.id.btn_del);
    }

    public void send(View v){
        Intent intent = new Intent(NotificationActivity.this,OneMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this,0,intent,0);
        Notification notification = new Notification.Builder(this)
                .setAutoCancel(true)//设置打开通知，该通知自动取消
                .setTicker("有新消息")
                .setSmallIcon(R.mipmap.a)
                .setContentTitle("一条新通知")
                .setContentText("恭喜您，您加薪了，工资增加20%")
                .setDefaults(Notification.DEFAULT_ALL)
//                .setSound(Uri.parse("android.resource://org.crazyit.ui/"+R.raw.beep)) //设置通知自定义声音
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(NOTIFICATION_ID,notification);
    }
    public void del(View v){
        manager.cancel(NOTIFICATION_ID);
    }

}
