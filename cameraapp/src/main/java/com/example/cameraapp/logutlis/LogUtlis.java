package com.example.cameraapp.logutlis;

import android.util.Log;

/**
 * Created by user on 2016/6/15.
 */
public class LogUtlis
{
    public static boolean allowE = true;

    public static String getTAG(){
        StackTraceElement stackTrace= Thread.currentThread().getStackTrace()[4];
        return stackTrace.getClassName();
    }


    public static void e(String content) {
        if(allowE == false) return;
        if(content !=null)
            Log.e(getTAG(),content);
        else
            Log.e(getTAG(),"没有捕获到异常信息");
    }

    public static void e(String tag ,String content){
        if(allowE == false) return;
        Log.e(tag,content);
    }


}
