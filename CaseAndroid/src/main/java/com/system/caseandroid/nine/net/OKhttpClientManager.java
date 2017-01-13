package com.system.caseandroid.nine.net;

import android.content.Context;
import com.system.caseandroid.utils.FileUtils;
import com.system.caseandroid.utils.LogUtils;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 2017/1/12.
 */

public class OKhttpClientManager
{
    private static OKhttpClientManager okhttpClient;
    private int CACHESIZE = 10*1024*1024; //缓存文件夹大小
    private OkHttpClient okHttpClient;
    private String result;
    private String ERROR = "error";
    private Context context;
    private InputStream is;

    public static OKhttpClientManager getInstance(Context context){
        if(okhttpClient == null){
            synchronized (OKhttpClientManager.class){
                if(okhttpClient == null){
                    okhttpClient = new OKhttpClientManager(context);
                }
            }
        }
        return okhttpClient;
    }

    /**
     * 设置网络请求参数
     */
    public OKhttpClientManager(Context context){
        Cache cache = new Cache(FileUtils.getInstance().getCacheDir(),CACHESIZE);
        try
        {
            is = context.getAssets().open("selfcert.cer");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        SSLSocketFactory socketFactory  = setCertificates(is);

        okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .socketFactory(socketFactory)
                .cache(cache)
                .build();
    }
//    private static class UnSafeTrustManager implements X509TrustManager
//    {
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType)
//                throws CertificateException
//        {
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType)
//                throws CertificateException
//        {
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers()
//        {
//            return new java.security.cert.X509Certificate[]{};
//        }
//    }
//    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers)
//    {
//        for (TrustManager trustManager : trustManagers)
//        {
//            if (trustManager instanceof X509TrustManager)
//            {
//                return (X509TrustManager) trustManager;
//            }
//        }
//        return null;
//    }
//
//    private static class MyTrustManager implements X509TrustManager
//    {
//        private X509TrustManager defaultTrustManager;
//        private X509TrustManager localTrustManager;
//
//        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException
//        {
//            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            var4.init((KeyStore) null);
//            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
//            this.localTrustManager = localTrustManager;
//        }
//
//
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
//        {
//
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
//        {
//            try
//            {
//                defaultTrustManager.checkServerTrusted(chain, authType);
//            } catch (CertificateException ce)
//            {
//                localTrustManager.checkServerTrusted(chain, authType);
//            }
//        }
//
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers()
//        {
//            return new X509Certificate[0];
//        }
//    }


    public SSLSocketFactory setCertificates(InputStream inputStream){
        try
        {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            String certificateAlias = Integer.toBinaryString(index++);
            keyStore.setCertificateEntry(certificateAlias,factory.generateCertificate(inputStream));
            try
            {
                inputStream.close();
            }catch (IOException e){

            }


            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            try
            {
                sslContext.init(null,trustManagerFactory.getTrustManagers(),null);
            }
            catch (KeyManagementException e)
            {
                e.printStackTrace();
            }
            return sslContext.getSocketFactory();
        }
        catch (CertificateException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (KeyStoreException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public String OKhttpRequest(String url){
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        try
        {
            Response response = call.execute();
            return result = response.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            LogUtils.e("OKHTTP",e.getMessage()+"");
        }
        return ERROR;
    }
}
