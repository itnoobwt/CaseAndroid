package com.system.caseandroid.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by user on 2016/11/29.
 */

public class FileUtils
{
    private File mBaseDir;
    private static FileUtils fileUtils;
    private String cache = "cache";
    private String image = "image";
    private String duandian = "duandian";
    private static Context context;

    public static void init(Context context1){
        context = context1;
    }

    public static FileUtils getInstance()
    {
        if (fileUtils == null)
        {
            synchronized (FileUtils.class)
            {
                if (fileUtils == null)
                {
                    fileUtils = new FileUtils();
                }
            }
        }
        return fileUtils;
    }

    /**
     * 判断是否存储卡状态
     */
    public FileUtils()
    {
        String externalStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState))
        {
            mBaseDir = new File(Environment.getExternalStorageDirectory(), "CaseAndroid"); //自带存储卡
        }
        else
        {
            mBaseDir = context.getFilesDir();
        }
    }

    /**
     * 缓存文件夹
     * @return
     */
    public File getCacheDir(){
        return  new File(mBaseDir,cache);
    }
    /**
     * 图片文件夹
     * @return
     */
    public File getImageDir(){
        return new File(mBaseDir,image);
    }

    /**
     * 断点续传
     * @return
     */
    public File getDuandianDir(){
        return  new File(mBaseDir,duandian);
    }
    /**
     * 创建文件夹
     *
     * @param file
     * @return
     */
    public File tryMakeDirs(File file)
    {
        if (!file.exists())
            file.mkdirs();
        return file;
    }

    public File getmBaseDir()
    {
        return mBaseDir;
    }

    public void setmBaseDir(File mBaseDir)
    {
        this.mBaseDir = mBaseDir;
    }

}
