package com.example.cameraapp.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.cameraapp.MyApplication;
import com.example.cameraapp.logutlis.LogUtlis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
/**
 * Created by user on 2016/9/8.
 * 本地缓存
 */
public class LocalCacheUtils
{
    /**
     * 从本地读取图片
     * @param url
     */
    public Bitmap getBitmapFromLocal(String url){
        String fileName = url;
        File file = new File(MyApplication.path+"/ImageCache",fileName);
        try
        {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            LogUtlis.e("本地获取图片",e.getMessage()+"");
        }
        return null;
    }
    /**
     * 从网络获取图片后,保存至本地缓存
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url,Bitmap bitmap){
        String fileName = url;
        File file = new File(MyApplication.path+"/ImageCache",fileName);
        //通过得到文件的父文件,判断父文件是否存在
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        try
        {
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            LogUtlis.e("LocalCacheUtils-----setBitmapToLocal",e.getMessage()+"");
        }
    }
}
