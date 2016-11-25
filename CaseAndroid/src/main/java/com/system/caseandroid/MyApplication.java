package com.system.caseandroid;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by user on 2016/11/25.
 */

public class MyApplication extends Application
{
    public static int maxMemory; //获取运行内存大小
    @Override
    public void onCreate()
    {
        super.onCreate();
        maxMemory = ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        Log.e("MyApplication---获取内存大小",maxMemory+"M");
        maxMemory = maxMemory*1024*1024/8;
        Log.e("MyApplication---获取内存大小",maxMemory+"M");
    }
}
