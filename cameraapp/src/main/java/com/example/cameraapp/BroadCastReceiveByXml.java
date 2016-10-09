package com.example.cameraapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.cameraapp.logutlis.LogUtlis;

/**
 * Created by user on 2016/6/27.
 */
public class BroadCastReceiveByXml extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        LogUtlis.e("BroadCastReceiveByXml","静态注册广播");
    }
}
