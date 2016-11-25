package com.system.caseandroid.seven.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
    public NetCacheUtils(){
        mMemoryCacheUtils = new MemoryCacheUtils();
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
            return BitmapFactory.decodeStream(inputStream);
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
}
