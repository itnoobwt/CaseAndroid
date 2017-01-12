package com.system.caseandroid.seven.utils;

/**
 * Created by user on 2016/11/23.
 */

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.system.caseandroid.MyApplication;
import com.system.caseandroid.R;
import com.system.caseandroid.utils.FileUtils;

import java.io.File;

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
                if(myBitmapCache == null)
                    myBitmapCache = new MyBitmapCache();
            }
        }
        return  myBitmapCache;
    }

    public MyBitmapCache(){
        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mNetCacheUtils = new NetCacheUtils(mMemoryCacheUtils,mLocalCacheUtils);
    }

    /**
     *  显示图片
     * @param url 图片地址
     * @param imageView  控件
     */
    public void disPlay(String url, ImageView imageView){
        imageView.setImageResource(R.mipmap.icon_placeholder);
        if(url == null){
            Log.e("MyBitmapCache","url is null");
            return;
        }
        if(!url.isEmpty()){
            Bitmap bitmap = mMemoryCacheUtils.getBitmapFromMemCache(url);
            if(bitmap!=null)
            {
                imageView.setImageBitmap(bitmap);
                Log.e("mMemoryCacheUtils","使用缓存里的bitmap");
                return;
            }
            bitmap = mLocalCacheUtils.getBitmapFileCache(FileUtils.getInstance().getImageDir().toString(),url);
            if(bitmap != null){
                imageView.setImageBitmap(mLocalCacheUtils.getBitmapFileCache(FileUtils.getInstance().getImageDir().toString(),
                        url));
                Log.e("mLocalCacheUtils","使用本地文件图片");
                return;
            }
            if(bitmap == null){
                mNetCacheUtils.getBitmapFromNetWorkCache(url,imageView);
                imageView.setImageResource(R.mipmap.icon_placeholder);
                Log.e("mMemoryCacheUtils","使用网络请求");
            }

        }
        else{
            Log.e("MyBitmapCache","url is empty");
        }
    }
}
