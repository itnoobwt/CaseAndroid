package com.system.caseandroid.inputUtils.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2016/12/8.
 */

public class FileDownloader
{
    private static final String TAG = "FileDownloader";
    private Context context;
    private FileServie fileServie;


    /*已下载文件长度 */
    private int downloadSize = 0;
    /* 原始文件长度 */
    private int fileSize = 0;

    /* 线程数 */
    private DownloadThread[] threads;

    /* 本地保存文件 */
    private File saveFile;

    /* 缓存各线程下载的长度*/
    private Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();

    /* 每条线程下载的长度 */
    private int block;

    /* 下载路径  */
    private String downloadUrl;

    /**
     * 获取线程数
     * @return
     */
    public int getThreadSize(){
        return  threads.length;
    }

    /**
     * 获取文件大小
     * @return
     */
    public int getFileSize(){
        return fileSize;
    }

    /**
     * 累计已下载大小
     * @param size
     */
    protected  synchronized void append(int size){
        downloadSize += size;
    }

    /**
     * 更新指定线程最后下载的位置
     * @param threadId   线程id
     * @param pos        最后下载的位置
     */
    protected synchronized void update(int threadId,int pos){
        this.data.put(threadId,pos);
        this.fileServie.update(downloadUrl,this.data);
    }

    public FileDownloader(Context context, String downloadUrl, File fileSavaDir, int threadNum){
        this.context = context;
        this.downloadUrl = downloadUrl;
        //创建数据库，以及相关表
        fileServie = new FileServie(this.context);
        try
        {
            URL url = new URL(this.downloadUrl);
            if(!fileSavaDir.exists()) fileSavaDir.mkdirs();
            this.threads = new DownloadThread[threadNum];
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5*10000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, " +
                    "application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, " +
                    "application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Referer", downloadUrl);  //先前网页的地址，当前请求网页紧随其后,即来路
            conn.setRequestProperty("Charset", "UTF-8");
            //User-Agent的内容包含发出请求的用户信息
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322;" +
                    " .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            conn.setRequestProperty("Connection", "Keep-Alive"); //表示是否需要持久连接。（HTTP 1.1默认进行持久连接）
            conn.connect();

            if(conn.getResponseCode() == 200){
                this.fileSize = conn.getContentLength();
                //获取到文件大小判断是否有文件
                if(this.fileSize<=0){
                    throw new RuntimeException("Unkown file size");
                }
                //获取文件名称
                String filename = getFileName(conn);
                //构建保存文件
                this.saveFile = new File(fileSavaDir,filename);
                //获取下载记录
                Map<Integer,Integer> logdata = fileServie.getData(downloadUrl);
                //如果存在下载记录
                if(logdata.size()>0){
                    for (Map.Entry<Integer,Integer> entry : logdata.entrySet()){
                        //key为线程id，value为对应的线程下载过的文件长度
                        data.put(entry.getKey(),entry.getValue());//把各条线程已经下载的数据长度放入data中
                        Log.e(TAG,entry.getKey()+"         "+entry.getValue());
                    }
                }
                //获取到每个线程下载的文件长度，累计加起来就是文件的大小
                if(this.data.size() == this.threads.length){
                    for (int i = 0 ; i < this.threads.length; i++){
                        this.downloadSize += this.data.get(i+1);
                    }
                    Log.e(TAG,"已经下载的长度："+downloadSize);
                }
                //计算每条线程下载的数据长度
                this.block = (this.fileSize % this.threads.length) ==0?
                        this.fileSize / this.threads.length : this
                        .fileSize/this.threads.length+1;
            }else{
                throw  new RuntimeException("server no response");
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件名
     * @param connection
     * @return
     */
    public String getFileName(HttpURLConnection connection){
        String filename = this.downloadUrl.substring(this.downloadUrl.lastIndexOf("/")+1);
        if(filename == null || "".equals(filename.trim())){
            for (int i = 0;;i++){
                String mine = connection.getHeaderField(i);
                if(mine == null) break;
                if("content-disposition".equals(connection.getHeaderFieldKey(i).toLowerCase())){
                    Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
                    if(m.find()) return m.group(1);
                }
            }
            filename = UUID.randomUUID()+".tmp"; //默认取一个文件名
        }
        return filename;
    }
    /**
     *  开始下载文件
     * @param listener 监听下载数量的变化,如果不需要了解实时下载的数量,可以设置为null
     * @return 已下载文件大小
     * @throws Exception
     */
    public int download(DownloadProgressListener listener) throws Exception{
        try
        {
            RandomAccessFile randOut = new RandomAccessFile(this.saveFile,"rw");
            if(this.fileSize>0) randOut.setLength(this.fileSize);
            randOut.close();
            URL url = new URL(this.downloadUrl);
            if(this.data.size()!= this.threads.length){
                this.data.clear();
                for (int i = 0; i<this.threads.length; i++){
                    this.data.put(i+1,0);          //初始化每条线程已经下载的数据长度为0
                }
            }
            for (int i = 0; i < this.threads.length; i++){
                int downLength = this.data.get(i+1);
                //判断线程是否已经完成下载,否则继续下载
                if(downLength < this.block && this.downloadSize < this.fileSize){
                    this.threads[i] = new DownloadThread(this,url,this.saveFile,this.block,this.data.get(i+1),i+1);
                    this.threads[i].setPriority(7);
                    this.threads[i].start();
                }else{
                    this.threads[i] = null;
                }
            }

            this.fileServie.save(this.downloadUrl,this.data);
            boolean notFinish = true;

            while (notFinish){     //循环判断所有线程是否完成下载
                Thread.sleep(900);
                notFinish = false;   //假定全部线程下载完成

                for (int i = 0; i < this.threads.length; i++){

                    if(this.threads[i] != null && !this.threads[i].isFinish()){  //如果发现线程未完成下载
                        notFinish = true;    //设置标志未下载没有完成

                        if(this.threads[i].getDownLength() == -1){ //如果下载失败，再重新下载
                            this.threads[i] = new DownloadThread(this,url,this.saveFile,this.block,this.data.get(i+1),i+1);
                            this.threads[i].setPriority(7);
                            this.threads[i].start();
                        }
                    }
                }
                if(listener != null) listener.onDownloadSize(this.downloadSize);  //通知目前已经下载完成的数据长度
            }

            fileServie.delete(this.downloadUrl);
        } catch (Exception e){
            throw new Exception("file download fail");
        }
        return this.downloadSize;
    }

    /**
     * 获取http响应头字段
     * @param http
     * @return
     */
    public static Map<String,String> getHttpResponseHeader(HttpURLConnection http){
        Map<String,String> header = new LinkedHashMap<String,String>();
        for (int i = 0;;i++){
            String mine = http.getHeaderField(i);
            if(mine == null) break;
            header.put(http.getHeaderFieldKey(i),mine);
        }
        return header;
    }

    public static void printResponseHeader(HttpURLConnection http){
        Map<String,String> header = getHttpResponseHeader(http);
        for (Map.Entry<String,String> entry: header.entrySet()){
            String key = entry.getKey()!=null ? entry.getKey()+":":"";
            Log.e("printResponseHeader",key+entry.getValue());
        }
    }

    /**
     * 返回db
     * @return
     */
    public DBOpenHelper getDBOpenHelper(){
        return fileServie.getDbOpenHelper();
    }


}
