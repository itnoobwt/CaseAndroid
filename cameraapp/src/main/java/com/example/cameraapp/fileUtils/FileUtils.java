package com.example.cameraapp.fileUtils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by user on 2016/6/14.
 */
public class FileUtils
{
    /**
     * 判断是否有sd卡
     * @return
     */
    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    /**
     *
     * @param folder  文件夹名称
     * @return
     */
    public static String createFile(String folder){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+folder;
        File file = new File(path);
        if(!file.exists()){
             file.mkdirs();
        }
        return  file.toString();
    }

    public static File createNewFile(String folder){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+folder;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return  file;
    }

}
