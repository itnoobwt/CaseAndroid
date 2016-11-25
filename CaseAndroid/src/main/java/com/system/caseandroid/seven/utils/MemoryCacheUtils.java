package com.system.caseandroid.seven.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import com.system.caseandroid.MyApplication;

/** 内存缓存
 * Created by user on 2016/11/25.
 */

public class MemoryCacheUtils
{
    private LruCache<String,Bitmap> mMemorCache;

    public MemoryCacheUtils(){
        //得到手机最大允许内存的1/8,即超过指定内存,则开始回收(MyApplication.maxMemory)
        mMemorCache = new LruCache<String,Bitmap>(MyApplication.maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return value.getByteCount();
            }
        };
    }

    /**
     * 添加缓存
     * @param key
     * @param bitmap
     */
    public void addBitmapToMemoryCache(String key,Bitmap bitmap){
        //判断如果以后缓存就不缓存了
        if(getBitmapFromMemCache(key) == null){
            Log.e("size",mMemorCache.size()+"        ---------------------------   ");
            mMemorCache.put(key,bitmap);
            Log.e("size",mMemorCache.size()+"        ---------------------------   ");
        }

    }

    /**
     * 根据key获取bitmap
     * @param key
     * @return
     */
    public Bitmap getBitmapFromMemCache(String key){
        Bitmap bitmap = mMemorCache.get(key);
        return bitmap;
    }
}
