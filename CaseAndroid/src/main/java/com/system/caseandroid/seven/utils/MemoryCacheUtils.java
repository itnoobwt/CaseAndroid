package com.system.caseandroid.seven.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;
import com.system.caseandroid.MyApplication;

/** 内存缓存
 * Created by user on 2016/11/25.
 */

public class MemoryCacheUtils
{
    private LruCache<String,Bitmap> mMemorCache;

    public MemoryCacheUtils(){
        //得到手机最大允许内存的1/8,即超过指定内存,则开始回收(MyApplication.maxMemory)
        long maxMemory = Runtime.getRuntime().maxMemory()/8;
        mMemorCache = new LruCache<String,Bitmap>(MyApplication.maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
//                    return value.getAllocationByteCount();
//                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) { //API 12
//                    return value.getByteCount();
//                } else {
//                    return value.getRowBytes() * value.getHeight();
//                }
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
        if(getBitmapFromMemCache(key) == null)
            mMemorCache.put(key,bitmap);

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

    public int getSize(){
        return mMemorCache.size();
    }
}
