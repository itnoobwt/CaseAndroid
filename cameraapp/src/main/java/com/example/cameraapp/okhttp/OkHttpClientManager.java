package com.example.cameraapp.okhttp;

import android.os.Handler;
import android.os.Looper;
import com.example.cameraapp.fileUtils.FileUtils;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by user on 2016/6/28.
 */
public class OkHttpClientManager
{
    private OkHttpClient okHttpClient;
    private static OkHttpClientManager mInstance;
    private int CACHESIZE = 10*1024*1024;
    private Handler handler;
    private Gson gson;
    public OkHttpClientManager(){
        File file = FileUtils.createNewFile("network_cache");

        Cache cache = new Cache(file,CACHESIZE);
        okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .cache(cache)
                .cookieJar(cookieJar)
                .addInterceptor(interceptor)
                .build();
        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());
    }
    public CookieJar cookieJar = new CookieJar()
    {
        HashMap<HttpUrl,List<Cookie>> map = new HashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies)
        {
            map.put(url,cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url)
        {
            List<Cookie> cookies = map.get(url);
            return cookies!=null?cookies:new ArrayList<Cookie>();
        }
    };


    public void get(){
        Request request = new Request.Builder()
                .url("http://www.chundogs.com/ShoppingApi/AdvertApi?otype=alist")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                Headers headers = response.headers();
                okHttpClient.cookieJar();
            }
        });
    }

    /**
     * 设置请求头，10秒之后请求网络数据，10秒之内请求缓存数据
     */
    public Interceptor interceptor = new Interceptor()
    {
        @Override
        public Response intercept(Chain chain) throws IOException
        {
            Response response = chain.proceed(chain.request());
            return response.newBuilder()
                    .header("Cache-Control",String.format("max-age=%d",10000))
                    .build();
        }
    };

    public static OkHttpClientManager getInstance(){
        if(mInstance == null){
            synchronized (OkHttpClientManager.class){
                if(mInstance == null){
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }


    public static abstract class ResultCallback <T>{
        Type type;
        public ResultCallback (){
            type = getSuperclassTypeParameter(getClass());
        }

        private  Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if(superclass instanceof Class){
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterizedType = (ParameterizedType)superclass;
            return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
        }
        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }
    public class Param{
        String key;
        String value;
        public Param(String key,String value){
            this.key = key;
            this.value = value;
        }
    }
    /**
     * 异步请求
     * @param url
     * @param resultCallback
     */
    private void getAsyn(String url,ResultCallback resultCallback){
        //创建Request
        Request request = new Request.Builder()
                .url(url).build();
       deliveryResult(resultCallback,request);
    }

    private void _postAsyn(String url,ResultCallback resultCallback,String key,String value)
    {
        FormBody formBody = new FormBody.Builder()
                .add(key,value)
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        deliveryResult(resultCallback,request);
    }

    private void _postAsyn(String url,ResultCallback resultCallback,File[] files,String[] fileKeys)
    {
        Request request = buildMultipartFormRequest(url,files,fileKeys);
    }

    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys)
    {
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Headers.of("Content-Disposition", "form-data; name=uploadFile"),RequestBody.create(null,
                        "aa.txt"))
                .build();
        return  new Request.Builder().post(multipartBody).build();
    }


    private void deliveryResult(final ResultCallback resultCallback, Request request)
    {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String msg = response.body().string();
                if(resultCallback.type == String.class){
                    sendSuccessResultCallback(resultCallback,msg);
                }
                else{
                    Object o = gson.fromJson(msg,resultCallback.type);
                    sendSuccessResultCallback(resultCallback,o);
                }
            }
        });
    }

    /**
     * 转换到主线程UI
     * @param resultCallback
     * @param msg
     */
    private void sendSuccessResultCallback(final ResultCallback resultCallback,final Object msg)
    {
        if(resultCallback!=null){
            handler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    resultCallback.onResponse(msg);
                }
            });
        }
    }

    public InputStream downLoadBitmap(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try
        {
            Response response = call.execute();
            InputStream inputStream = response.body().byteStream();
            return inputStream;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return  null;
    }
}
