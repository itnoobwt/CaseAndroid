package com.example.cameraapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.example.cameraapp.logutlis.LogUtlis;

/**
 * Created by user on 2016/6/20.
 */
public class LocalService extends Service
{

    public LocalService localService ;

    public SimpleBinder simpleBinder;
    public class SimpleBinder extends Binder{

        public LocalService getLocalService(){
            return LocalService.this;
        }

        public int add(int a,int b){
            return  a+b;
        }
    }


    /**

     * onBind 是 Service 的虚方法，因此我们不得不实现它。

     * 返回 null，表示客服端不能建立到此服务的连接。

     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return simpleBinder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        simpleBinder = new SimpleBinder();
        LogUtlis.e("onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        LogUtlis.e("onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        LogUtlis.e("onDestroy()");
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        LogUtlis.e("unbindService ++++++  onDestroy()");
        return super.onUnbind(intent);
    }
}
