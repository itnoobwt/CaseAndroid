package com.system.caseandroid.seven;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MediaActivity extends BaseActivity
{

    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.video_pb)
    ProgressBar videoPb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
//        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        String url = "http://192.168.1.144:9128/power/xianchangchaxian/big_buck_bunny.mp4";
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                Log.e("onCompletion", "播放完成");
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener()
        {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra)
            {
                Log.e("onError", what + "  " + extra);
                mp.release();
                return false;
            }
        });
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener()
        {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra)
            {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                {
                    videoPb.setVisibility(View.VISIBLE);
                    Log.e("onError", what + " 正在缓冲 " + extra);
                }
                else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                {
                    videoPb.setVisibility(View.GONE);
                    Log.e("onError", what + " 缓冲结束 " + extra);
                }
                return true;
            }
        });
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                download();
            }
        }).start();

    }
    public static void download(){
        try {
            URL url = new URL("http://192.168.1.144:9128/power/xianchangchaxian/big_buck_bunny.mp4");
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();
            int size = connection.getContentLength();
            String str = Environment.getExternalStorageDirectory().getAbsolutePath()+"/big_buck_bunny.mp4";
            File file = new File(str);
            FileOutputStream fos = new FileOutputStream(file);
            /**
             * model各个参数详解
             * r 代表以只读方式打开指定文件
             * rw 以读写方式打开指定文件
             * rws 读写方式打开，并对内容或元数据都同步写入底层存储设备
             * rwd 读写方式打开，对文件内容的更新同步更新至底层存储设备
             *
             * **/
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
            randomAccessFile.length();
            long l = randomAccessFile.getFilePointer();
            InputStream is = connection.getInputStream();
            int len = 0;
            byte[] bt = new byte[1024];
            while((len = is.read(bt))!=-1){
                fos.write(bt,0,len);
            }
            fos.flush();
            fos.close();
            is.close();
            //加密文件
//            FileEnDecryptManager.getInstance().InitEncrypt(file.toString());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
