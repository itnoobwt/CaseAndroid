package com.system.caseandroid.inputUtils;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.MyApplication;
import com.system.caseandroid.R;
import com.system.caseandroid.inputUtils.utils.DBOpenHelper;
import com.system.caseandroid.inputUtils.utils.DownloadProgressListener;
import com.system.caseandroid.inputUtils.utils.FileDownloader;
import com.system.caseandroid.utils.FileUtils;

import java.io.File;

public class IOActivity extends BaseActivity
{

    @BindView(R.id.path)
    EditText downloadpathText;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.downloadbar)
    ProgressBar progressBar;
    @BindView(R.id.resultView)
    TextView resultView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);
    }


    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    progressBar.setProgress(msg.getData().getInt("size"));
                    float num = progressBar.getProgress() / (float) progressBar.getMax();
                    int result = (int) (num * 100);
                    resultView.setText(result + "%");
                    if (progressBar.getProgress() == progressBar.getMax())
                    {
                        Toast.makeText(IOActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    Toast.makeText(IOActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @OnClick(R.id.button)
    public void netdownload()
    {
        String path = downloadpathText.getText().toString().trim();
        Log.e("sd卡类型", Environment.getExternalStorageState() + "-------" + Environment.MEDIA_MOUNTED);
        download(path, FileUtils.getInstance().getDuandianDir());
    }

    FileDownloader loader;
    private void download(final String path, final File savedir)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                loader = new FileDownloader(IOActivity.this, path, savedir, 3);
                progressBar.setMax(loader.getFileSize());
                try
                {
                    loader.download(new DownloadProgressListener()
                    {
                        @Override
                        public void onDownloadSize(int size)
                        {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.getData().putInt("size", size);
                            handler.sendMessage(msg);
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    handler.obtainMessage(-1).sendToTarget();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        DBOpenHelper dbOpenHelper = loader.getDBOpenHelper();
        if(dbOpenHelper!=null){
            dbOpenHelper.close();
            Log.e("IOActivity","关闭db");
        }
    }
}
