package com.system.caseandroid.nine.net;

import android.content.Context;
import com.system.caseandroid.utils.FileUtils;
import com.system.caseandroid.utils.LogUtils;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
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
    public static OKhttpClientManager getInstance(){
        if(okhttpClient == null){
            synchronized (OKhttpClientManager.class){
                if(okhttpClient == null){
                    okhttpClient = new OKhttpClientManager();
                }
            }
        }
        return okhttpClient;
    }

    /**
     * 设置网络请求参数
     */
    public OKhttpClientManager(){
        Cache cache = new Cache(FileUtils.getInstance().getCacheDir(),CACHESIZE);


        okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    public void setCertificates(InputStream inputStream){
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

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null,trustManagerFactory.getTrustManagers(),new SecureRandom());
            okHttpClient.newBuilder().socketFactory(sslContext.getSocketFactory()).build();
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
        catch (KeyManagementException e)
        {
            e.printStackTrace();
        }

    }



    public String OKhttpRequest(String url, InputStream inputStream){
        setCertificates(inputStream);
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
