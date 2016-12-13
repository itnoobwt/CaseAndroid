package com.system.caseandroid.inputUtils.utils;

import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2016/12/9.
 */

public class DownloadThread extends Thread
{
    private static final String TAG = "DownloadThread";
    private File saveFile;
    private URL downUrl;
    private int block;

    private int threadId = -1;
    private int downLength;
    private boolean finish = false;
    private boolean stop = false;
    private FileDownloader downloader;

    public DownloadThread(FileDownloader downloader, URL downUrl, File saveFile, int block, int downLength, int
            threadId){
        this.downUrl = downUrl;
        this.saveFile = saveFile;
        this.block = block;
        this.downloader = downloader;
        this.threadId = threadId;      //线程id
        this.downLength = downLength;  //本地保存最后一次记录，文件长度
    }

    @Override
    public void run()
    {
        super.run();

        if(downLength < block) {  //未下载完成
            try
            {
//                block: 538780 threadId: 1 - 11403  downLength: 0
//                线程id：11403  开始：0  结束：538779

//                block: 538780 threadId: 2 - 11404  downLength: 0
//                线程id：11404  开始：538780  结束：1077559

//                block: 538780 threadId: 3 - 11405  downLength: 0
//                线程id：11405  开始：1077560  结束：1616339
                HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
                http.setConnectTimeout(5 * 1000);
                http.setRequestMethod("GET");
                http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
                http.setRequestProperty("Accept-Language", "zh-CN");
                http.setRequestProperty("Charset", "UTF-8");
                int startPos = block * (threadId -1 )+ downLength; //开始位置
                int endPos = block * threadId -1; //结束位置
                Log.e(TAG," block: "+block+" threadId: "+ threadId+ " - "+this.currentThread().getId()+"  downLength:" +
                        " "+ downLength+"  ");
                Log.e(TAG,"线程id："+this.currentThread().getId()+"  开始："+startPos+"  结束："+endPos);
                http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                http.setRequestProperty("Connection", "Keep-Alive");
                http.setRequestProperty("Referer", downUrl.toString());//先前网页的地址，当前请求网页紧随其后,即来路
                http.setRequestProperty("Range", "bytes=" + startPos + "-"+ endPos);//设置获取实体数据的范围
                http.connect();
                InputStream inputStream = http.getInputStream();
                byte[] buffer = new byte[1024];
                int offset = 0;
                RandomAccessFile threadfile = new RandomAccessFile(this.saveFile, "rwd");
                threadfile.seek(startPos);
                while((offset = inputStream.read(buffer))!=-1){
                    threadfile.write(buffer,0,offset);
                    downLength += offset;
                    downloader.update(this.threadId,downLength);
                    downloader.append(offset);
                }
                threadfile.close();
                inputStream.close();
                this.finish = true;
            }catch (Exception e){
                this.downLength = -1;
            }

        }
    }

    /**
     * 下载是否完成
     * @return
     */
    public boolean isFinish(){
        return finish;
    }

    /**
     * 已经下载的内容大小
     * @return 返回值-1，代表下载失败
     */
    public long getDownLength(){
        return downLength;
    }


}
