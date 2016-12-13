package com.system.caseandroid.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by user on 2016/11/29.
 */

public class FileUtils
{
    /**
     * 创建图片文件夹
     * @return
     */
    public static String createFile(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/CaseAndroid/img/";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.toString();
    }
}
