package com.system.caseandroid.three;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 2016/9/28.
 */

public class SendSmsListener implements View.OnLongClickListener
{
    private Activity activity;
    private EditText editText;
    private EditText content;
    public SendSmsListener(Activity activity , EditText editText,EditText content){
        this.activity = activity;
        this.editText = editText;
        this.content = content;
    }
    @Override
    public boolean onLongClick(View view)
    {
        String address = editText.getText().toString();
        String contentstr = content.getText().toString();
        //获取短信管理器
        SmsManager smsManager = SmsManager.getDefault();
        //创建发送短信
        PendingIntent senIntent = PendingIntent.getBroadcast(activity,0,new Intent(),0);
        //发送文本短信
        try
        {
            smsManager.sendTextMessage(address,null,contentstr,senIntent,null);
        }catch (Exception e){
            Log.e("error",e.getMessage()+"");
        }
        Toast.makeText(activity, "短信发送完成", Toast.LENGTH_SHORT).show();
        return false;
    }
}
