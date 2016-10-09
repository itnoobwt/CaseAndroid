package com.example.cameraapp;

import android.app.Application;
import android.os.Environment;
import com.example.cameraapp.fileUtils.FileUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.File;

/**
 * Created by user on 2016/6/14.
 */
public class MyApplication extends Application
{
    public static File videoDir;
    public static String path;
    @Override
    public void onCreate()
    {
        super.onCreate();
        //初始化
        Fresco.initialize(this);
        videoDir =new File(FileUtils.createFile("Camerapp/Vieo"));
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
    }


}
