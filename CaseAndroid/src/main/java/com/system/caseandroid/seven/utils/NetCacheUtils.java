package com.system.caseandroid.seven.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by user on 2016/11/25.
 */

public class NetCacheUtils
{
    private static final String TAG = "NetCacheUtils";
    private LocalCacheUtils mLocalCacheUtils;     //本地缓存
    private MemoryCacheUtils mMemoryCacheUtils;    //内存缓存
    public NetCacheUtils(MemoryCacheUtils mMemoryCacheUtils,LocalCacheUtils mLocalCacheUtils){
        this.mMemoryCacheUtils = mMemoryCacheUtils;
        this.mLocalCacheUtils = mLocalCacheUtils;
    }

    /**
     * 网络获取图片，并且添加到缓存
     */
    public void getBitmapFromNetWorkCache(String url, ImageView imageView){
        BitmapTask bitmapTask = new BitmapTask();
        bitmapTask.execute(url,imageView);
    }


    class BitmapTask extends AsyncTask<Object,Void,Bitmap>{
        ImageView imageView;
        String url;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Object... params)
        {
            url = (String) params[0];
            imageView = (ImageView) params[1];
            return getNetWork(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            if(bitmap!=null){
                imageView.setImageBitmap(bitmap);
                mMemoryCacheUtils.addBitmapToMemoryCache(url,bitmap);
                mLocalCacheUtils.addBitmapFileCache(url,bitmap);
                Log.e("size",mMemoryCacheUtils.getSize()+"   -----");
            }

        }


    }

    /**
     * 网络请求图片地址
     * @param path
     * @return
     */
    public Bitmap getNetWork(String path){
        try
        {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap = photoCompress(bitmap);
            return bitmap;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            Log.e(TAG,e.getMessage()+" ");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e(TAG,e.getMessage()+" ");
        }
        return null;
    }

    /**
     * 进行质量压缩
     * @param bitmap
     */
    public Bitmap photoCompress(Bitmap bitmap){
        int quality = 100;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,quality,bos);
        Log.e("byt",bos.toByteArray().length+"");
        while (bos.toByteArray().length/1024>1024&&quality>10){
            quality -=10; //每次都减10
            bos.reset();
            Log.e("byt1",bos.toByteArray().length+"");
            //PNG  会忽略质量设置
            bitmap.compress(Bitmap.CompressFormat.PNG,quality,bos);

            Log.e("quality",quality+"");
            Log.e("byt2",bos.toByteArray().length+"");
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        bitmap = BitmapFactory.decodeStream(bis);
        try
        {
            bis.close();
            bos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
}
