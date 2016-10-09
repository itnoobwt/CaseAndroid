package com.example.cameraapp.imageUtils;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.example.cameraapp.R;
import com.example.cameraapp.logutlis.LogUtlis;

/**
 * Created by user on 2016/9/8.
 */
public class MyBitmapUtils
{
    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;
    private static MyBitmapUtils myBitmapUtils;
    public static MyBitmapUtils getInstance()
    {
        if(myBitmapUtils == null){
            synchronized (MyBitmapUtils.class){
                if(myBitmapUtils == null)
                    myBitmapUtils = new MyBitmapUtils();
            }
        }
        return  myBitmapUtils;
    }

    public MyBitmapUtils(){
        mMemoryCacheUtils=new MemoryCacheUtils();
        mLocalCacheUtils=new LocalCacheUtils();
        mNetCacheUtils=new NetCacheUtils(mLocalCacheUtils,mMemoryCacheUtils);
    }

    public void disPlay(ImageView imageView, String url){
        imageView.setImageResource(R.drawable.icon_placeholder);
        Bitmap bitmap;
        //内存缓存
        bitmap=mMemoryCacheUtils.getBitmapFromMemory(url);
        if(bitmap!= null){
            imageView.setImageBitmap(bitmap);
            LogUtlis.e("内存缓存","从内存中获取图片");
        }

        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if(bitmap!= null){
            imageView.setImageBitmap(bitmap);
            LogUtlis.e("本地缓存","从本地中获取图片");
        }
        //网络缓存
        if(bitmap == null)
            mNetCacheUtils.getBitmapFromLocal(imageView,url);
    }
}
