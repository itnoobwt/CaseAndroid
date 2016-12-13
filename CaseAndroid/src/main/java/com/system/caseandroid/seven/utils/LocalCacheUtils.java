package com.system.caseandroid.seven.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.system.caseandroid.MyApplication;

import java.io.*;
import java.util.UUID;

/**
 * Created by user on 2016/11/25.
 */

public class LocalCacheUtils
{
//    public String imageName;
//    public LocalCacheUtils(){
//        UUID uuid = UUID.randomUUID();
//        imageName = uuid.toString();
//    }

    /**
     *  添加图片到本地
     * @param imageName
     * @param bitmap
     */
    public void addBitmapFileCache(String imageName, Bitmap bitmap){
        try
        {
            File file = new File(MyApplication.IMAGE_PATH,imageName);
            File parentFile = file.getParentFile();
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            Log.e("LocalCacheUtils",e.getMessage()+" addBitmapFileCache");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件图片
     * @param path     文件路径
     * @param name     图片名称
     * @return
     */
    public Bitmap getBitmapFileCache(String path,String name){
        path = path+"/"+name;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(bitmap == null){
            Log.e("LocalCacheUtils","no file path  getBitmapFileCache");
        }
        return bitmap;
    }
}
