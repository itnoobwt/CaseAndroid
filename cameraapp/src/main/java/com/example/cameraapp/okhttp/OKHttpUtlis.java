package com.example.cameraapp.okhttp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.example.cameraapp.MyApplication;
import com.example.cameraapp.fileUtils.FileUtils;
import com.example.cameraapp.logutlis.LogUtlis;
import com.example.cameraapp.modle.Student;
import com.example.cameraapp.modle.User;
import com.example.cameraapp.network.NetWorkUtlis;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.*;
import com.squareup.okhttp.internal.spdy.FrameReader;
import org.json.JSONObject;
import retrofit2.http.POST;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.net.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2016/6/15.
 */
public class OKHttpUtlis
{
    public static Call call = null;

    public static OKHttpUtlis okHttpUtlis;

    public static OkHttpClient okHttpClient;

    public static int cachSize = 50*1024*1024;

    private Handler mDelivery;


    private final        String      HTTP_CACHE_FILENAME               = "HttpCache";
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
//            max-age 指示客户机可以接收生存期不大于指定时间（以秒为单位）的响应。
//            max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可以接收超出超时期指定值之内的响应消息。
                    .header("Cache-Control", String.format("max-age=%d", 100)) //设置缓存
                    .header("Accept-Encoding","gzip, deflate")
                    .header("Accept-Encoding","identity")
//                    .header("Cache-Control","no-cache")  //不缓存，每次都去服务器请求
                    .build();
        }
    };


    public OKHttpUtlis(){
        okHttpClient = new OkHttpClient();

        okHttpClient.setConnectTimeout(360,TimeUnit.SECONDS);  //设置请求超时

        okHttpClient.setWriteTimeout(10,TimeUnit.SECONDS);    //设置写入超时

        okHttpClient.setReadTimeout(10,TimeUnit.SECONDS);     //设置读入超时
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        Cache cache = new Cache(new File(path,"Camerapp/cache"),cachSize);  //设置请求缓存
        okHttpClient.setCache(cache);
        okHttpClient.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        CookieManager manager = new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        okHttpClient.setCookieHandler(manager);
        mDelivery = new Handler(Looper.getMainLooper());


    }



    public static OKHttpUtlis getInstance(){
        if(okHttpUtlis == null){
            synchronized (OKHttpUtlis.class){
                if(okHttpUtlis == null){
                    okHttpUtlis = new OKHttpUtlis();
                }
            }
        }
        return  okHttpUtlis;
    }


    private Response getAsyn(String url){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try
        {
            response =  call.execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return  response;
    }

    /**
     * 同步Get请求
     * @param url
     * @return 字符串
     */
    public String getAsynString(String url){
        Response response = getAsyn(url);
        String mes = null;
        try
        {
             mes = response.body().string();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return mes;
    }


    private Response _post(String url,Param... params){
        Request request = buildPostRequest(url,params);
        Response response = null;
        try
        {
            response = okHttpClient.newCall(request).execute();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return  response;
    }

    /**
     * 同步Post请求
     * @param url
     * @param params
     * @return
     */
    public String postString(String url,Param...params){
        Response response = _post(url,params);
        String mes = null;
        try
        {
            mes = response.body().string();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return  mes;
    }




    private Request buildPostRequest(String url, Param[] params)
    {
        if (params == null)
        {
            params = new Param[0];
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params)
        {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * 同步基于post文件上传
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @return
     * @throws IOException
     */
    public Response post(String url,File[] files,String[] fileKeys,Param... params) throws IOException{
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return okHttpClient.newCall(request).execute();
    }


    private Response post(String url, File file, String fileKey) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return okHttpClient.newCall(request).execute();
    }

    private Response post(String url, File file, String fileKey, Param... params) throws IOException
    {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return okHttpClient.newCall(request).execute();
    }

    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Param[] params)
    {
        params = validateParam(params);
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (Param param:params){
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param + "\""),
                    RequestBody.create(null, param.value));

        }
        if(files != null){
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++)
            {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }

        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private Param[] validateParam(Param[] params)
    {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void sendFailedStringCallback(final Request request, final Exception e, final ResultCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private void sendSuccessResultCallback(final Object object, final ResultCallback callback,final SimpleDraweeView
            simpleDraweeView)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(object);
                }
//                simpleDraweeView.setImageURI(Uri.parse("file://"+object.toString()));
            }
        });
    }
    private void sendSuccessResultCallback(final Object object, final ResultCallback callback)
    {
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                {
                    callback.onResponse(object);
                }
            }
        });
    }













    public void _downloadAsyn(final String url, final String destFileDir, final ResultCallback callback, final
                              SimpleDraweeView simpleDraweeView, final ProgressDialog progressDialog){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                InputStream inputStream = null;
                byte[] buf = new byte[2048];
                int len = 0;
                int tolal=0;
                FileOutputStream fileOutputStream = null;
                try{
                    inputStream = response.body().byteStream();
                    UUID uuid = UUID.randomUUID();
                    File file = new File(destFileDir,getFileName(url));
                    fileOutputStream = new FileOutputStream(file);
                    while ((len = inputStream.read(buf)) != -1){
                        fileOutputStream.write(buf,0,len);
                        tolal+=len;
                        progressDialog.setProgress(tolal);
                    }
                    fileOutputStream.flush();
                    inputStream.close();
                    progressDialog.dismiss();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback,simpleDraweeView);

                }catch (IOException e)
                {
                    sendFailedStringCallback(response.request(), e, callback);
                }finally
                {
                    if(inputStream!=null)
                        inputStream.close();
                    if(fileOutputStream!=null)
                        fileOutputStream.close();
                }

            }
        });
    }


    public void _downloadAsyn1(final String url, final String path , final ResultCallback callback){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                InputStream inputStream = null;
                byte[] buf = new byte[2048];
                int len = 0;
                int tolal=0;
                FileOutputStream fileOutputStream = null;
                File file = new File(path,getFileName(url));
                FileOutputStream fileOutputStream1 = new FileOutputStream(file);
                try{
                    inputStream = response.body().byteStream();
                    while ((len = inputStream.read(buf))!=-1){
                        fileOutputStream1.write(buf,0,len);
                    }
                    fileOutputStream1.flush();
                    inputStream.close();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResultCallback(file.getAbsolutePath(), callback);

                }catch (IOException e)
                {
                    sendFailedStringCallback(response.request(), e, callback);
                }finally
                {
                    if(inputStream!=null)
                        inputStream.close();
                    if(fileOutputStream!=null)
                        fileOutputStream.close();
                }

            }
        });
    }


    private String getFileName(String path)
    {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }


















    public static abstract class ResultCallback<T>
    {
        Type mType;

        public ResultCallback()
        {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass)
        {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class)
            {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);
    }


    public static class Param
    {
        public Param()
        {
        }

        public Param(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }







    public  void okget(){
        //https://api.github.com/repos/square/okhttp/issues
        //https://publicobject.com/helloworld.txt
        //http://www.mocky.io/v2/56c9d8c9110000c62f4e0bb0
        final Request request = new Request.Builder()
                .url("http://www.chundogs.com/ShoppingApi/AdvertApi?otype=alist")
//                .header("Cache-Control","only-if-cached")
//                .cacheControl(CacheControl.FORCE_CACHE) //强制读缓存
                .build();
        call = okHttpClient.newCall(request);

        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                LogUtlis.e(e.getMessage()+"  ----");
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                LogUtlis.e("json",response+":---------response");
                LogUtlis.e("json",response.cacheResponse()+":---------response.cacheResponse()");
                LogUtlis.e("json",response.networkResponse()+":---------response.networkResponse()");

                String str = response.body().string();

                //用流的方式接收
                //InputStream inputStream = response.body().byteStream();
                //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                //String mes = reader.readLine();
                LogUtlis.e("json",str);

            }
        });
    }


    public void post(){
        //okhttp3.0之前的用法
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        formEncodingBuilder.add("index","1");
        Request request = new Request.Builder().url("http://www.chundogs.com/ShoppingApi/GoodsApi?otype=like")
                .post(formEncodingBuilder.build())
                .build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {

            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                LogUtlis.e("json1",response+":---------response");
                LogUtlis.e("json1",response.cacheResponse()+":---------response.cacheResponse()");
                LogUtlis.e("json1",response.networkResponse()+":---------response.networkResponse()");

                //创建文件
                String fileName = FileUtils.createFile("Camerapp");
                //创建文件夹
                File file = new File(fileName,"dog.txt");
                //获取到流
                InputStream inputStream = response.body().byteStream();
                byte[] bt = new byte[2048];
                int len = 0;
                //把流写入到指定的文件夹里
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((len = inputStream.read(bt)) != -1){
                    fileOutputStream.write(bt,0,len);
                }
                //刷新
                fileOutputStream.flush();
                //关闭操作流
                inputStream.close();

//                String str = response.body().string();

            }
        });

    }
    public void postUpload(String url,String vieoPath){
        File file = new File(Environment.getExternalStorageDirectory(),"Camerapp/Vieo/123.png");
        File file3 = new File(Environment.getExternalStorageDirectory(),"Camerapp/Vieo/a.jpg");
        File file1 = new File(Environment.getExternalStorageDirectory(),"Camerapp/Vieo/b.jpg");
        File file2 = new File(Environment.getExternalStorageDirectory(),"Camerapp/Vieo/c.jpg");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        RequestBody fileBody1 = RequestBody.create(MediaType.parse("application/octet-stream"),file1);
        RequestBody fileBody2 = RequestBody.create(MediaType.parse("application/octet-stream"),file2);
        RequestBody fileBody3 = RequestBody.create(MediaType.parse("application/octet-stream"),file3);

        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"location.USER_NAME\""),
                        RequestBody.create(MediaType.parse("text/html;charest=utf-8"),"111"))
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"location.USER_NUMBER\""),
                        RequestBody.create(MediaType.parse("text/html;charest=utf-8"),"222"))
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"location.USER_ADDRESS\""),
                        RequestBody.create(MediaType.parse("text/html;charest=utf-8"),"333"))
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"location.CURRENT_POSITION\""),
                        RequestBody.create(MediaType.parse("text/html;charest=utf-8"),"444"))
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"location.USER_PHONE\""),
                        RequestBody.create(MediaType.parse("text/html;charest=utf-8"),"555"))
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"location.USER_ID\""),
                        RequestBody.create(MediaType.parse("text/html;charest=utf-8"),"777"))
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"file\";filename=\""+file.getName()+"\""),fileBody)
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"file1\";filename=\""+file1.getName()+"\""),fileBody1)
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"file2\";filename=\""+file2.getName()+"\""),fileBody2)
                .addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"file3\";filename=\""+file3.getName()+"\""),fileBody3)
                .build();


        Request request = new Request.Builder().url(url).post(requestBody).build();

        call = okHttpClient.newCall(request);
        LogUtlis.e("time",new Date().getSeconds()+"");
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Request request, IOException e)
            {
                LogUtlis.e("time 超时",new Date().getSeconds()+"");
                LogUtlis.e("error","-----------11111-------"+e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException
            {
                LogUtlis.e("error","------------------");
            }
        });
    }
}
