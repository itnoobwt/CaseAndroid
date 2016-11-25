package com.system.caseandroid.seven.utils;

/**
 * Created by user on 2016/11/23.
 */

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.system.caseandroid.R;

/**
 * 三级缓存
 */
public class MyBitmapCache
{
    private NetCacheUtils mNetCacheUtils;      //网络缓存
    private LocalCacheUtils mLocalCacheUtils;     //本地缓存
    private MemoryCacheUtils mMemoryCacheUtils;    //内存缓存
    public static MyBitmapCache myBitmapCache;
    public static MyBitmapCache getInstance(){
        if(myBitmapCache == null){
            synchronized (MyBitmapCache.class){
                myBitmapCache = new MyBitmapCache();
            }
        }
        return  myBitmapCache;
    }

    public MyBitmapCache(){
        mMemoryCacheUtils = new MemoryCacheUtils();
        mNetCacheUtils = new NetCacheUtils();
    }

    /**
     *  显示图片
     * @param url 图片地址
     * @param imageView  控件
     */
    public void disPlay(String url, ImageView imageView){
        imageView.setImageResource(R.mipmap.icon_placeholder);
        if(url == null){
            Log.e("MyBitmapCache","url为null");
            return;
        }
        if(!url.isEmpty()){
            Bitmap bitmap = mMemoryCacheUtils.getBitmapFromMemCache(url);
            if(bitmap!=null)
                imageView.setImageBitmap(bitmap);
            else{
                mNetCacheUtils.getBitmapFromNetWorkCache(url,imageView);
                imageView.setImageResource(R.mipmap.icon_placeholder);
            }

        }
        else{
            Log.e("MyBitmapCache","url为空");
        }
    }
}
