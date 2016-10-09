package com.example.cameraapp.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.example.cameraapp.okhttp.OkHttpClientManager;
import okhttp3.OkHttpClient;

import java.io.InputStream;

/**
 * Created by user on 2016/9/8.
 * 网络缓存
 */
public class NetCacheUtils
{
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromLocal(ImageView imageView, String url){
        new BitmapTask().execute(imageView,url);
    }

    class BitmapTask extends AsyncTask<Object,Void,Bitmap>{
        private ImageView ivPic;
        private String url;

        /**
         * 后台耗时操作,存在于子线程中
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object[] params)
        {
            ivPic = (ImageView) params[0];
            url = (String) params[1];
            return downLoadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            if(bitmap!=null)
                ivPic.setImageBitmap(bitmap);
            //从网络获取图片后,保存至本地缓存
            mLocalCacheUtils.setBitmapToLocal(url,bitmap);
            //保存至内存中
            mMemoryCacheUtils.setBitmapToMemory(url,bitmap);
        }
    }

    /**
     * 网络下载图片
     * @param url
     * @return
     */
    private Bitmap downLoadBitmap(String url) {
        InputStream is = OkHttpClientManager.getInstance().downLoadBitmap(url);
        return BitmapFactory.decodeStream(is);
    }
}
