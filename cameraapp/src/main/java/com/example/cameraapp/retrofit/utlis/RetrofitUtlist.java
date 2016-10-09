package com.example.cameraapp.retrofit.utlis;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import com.example.cameraapp.MyApplication;
import com.example.cameraapp.logutlis.LogUtlis;
import com.example.cameraapp.modle.Student;
import de.greenrobot.event.EventBus;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2016/7/22.
 */
public class RetrofitUtlist
{
    public interface RetorfitBiz{
        @POST("ServletGet")
        Observable<List<Student>> getStudent(@Query("id") String id);
    }
    public static RetrofitUtlist retrofitUtlist;
    public static OkHttpClient okHttpClient;
    public static int cachSize = 50*1024*1024; //缓存大小
    public static Interceptor interceptor;
    public static Cache cache;

    public static RetrofitUtlist getNewRetrofit(){
        if(retrofitUtlist == null){
            synchronized (RetrofitUtlist.class){
                if(retrofitUtlist == null){
                    retrofitUtlist = new RetrofitUtlist();
                    interceptor = new Interceptor()
                    {
                        @Override
                        public Response intercept(Chain chain) throws IOException
                        {
                            Response response = chain.proceed(chain.request());
                            return response
                                    .newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control",String.format("max-age%d",360))
                                    .build();
                        }
                    };

                    cache = new Cache(new File(MyApplication.path,"Camerapp/cache"),cachSize);
                    okHttpClient = new OkHttpClient()
                            .newBuilder()
                            .connectTimeout(360, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)//网络出错尝试重新连接
                            .addInterceptor(interceptor)  //设置请求头信息
                            .cache(cache)
                            .build();
                }
            }
        }
        return  retrofitUtlist;
    }



    public  void getAll(String id, String url){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .client(okHttpClient)
                .build();
        RetorfitBiz retorfitBiz = retrofit.create(RetorfitBiz.class);
        Observable<List<Student>> observable = null;
        try
        {
            observable = retorfitBiz.getStudent(id);
        }catch (Exception e){
            LogUtlis.e("RxJava",e.getMessage());
        }

            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new
                                                                                                                Subscriber<List<Student>>()
            {
                @Override
                public void onCompleted()
                {
                    //开始前准备的工作
                }

                @Override
                public void onError(Throwable e)
                {
                    LogUtlis.e("Retrofit-Error",e.getMessage());
                }

                @Override
                public void onNext(List<Student> students)
                {
                    EventBus.getDefault().post(students);
                }
            });


    }
}
