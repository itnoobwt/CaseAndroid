package com.system.caseandroid.utils;

import android.util.Log;

/**
 * Created by user on 2017/1/12.
 */

public class LogUtils
{
    private static boolean isLog = true;
    public static void e(String tag,String mes){
        if(isLog)
            Log.e(tag,mes);
    }
}
