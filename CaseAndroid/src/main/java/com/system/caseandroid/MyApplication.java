package com.system.caseandroid;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.system.caseandroid.utils.FileUtils;

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
        maxMemory = 1024*1024*maxMemory/8;
        FileUtils.init(this);
        Log.e("MyApplication","进来了。。。。");
    }
}
