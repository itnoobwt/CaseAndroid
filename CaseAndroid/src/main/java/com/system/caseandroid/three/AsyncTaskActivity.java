package com.system.caseandroid.three;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.system.caseandroid.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 异步（AsyncTask）使用
 */
public class AsyncTaskActivity extends AppCompatActivity
{
    //http://192.168.1.114:8080/Upload/Demo.apk
    private DowTask dowTask;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
    }
    public void dowload(View v){
        dowTask = new DowTask(this);
        try
        {
            dowTask.execute(new URL("http://192.168.1.114:8080/Upload/Demo.apk"));
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }


    class DowTask extends AsyncTask<URL,Integer,String>{
        private Context context;
        ProgressDialog dialog;
        private String success = "ok";
        private String error = "error";
        private int index = 0;
        public DowTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setTitle("正在下载中......");
            dialog.setMessage("请稍等............");
            dialog.setCancelable(true);   //设置对话框不能用“取消”按钮关闭
            dialog.setMax(100);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setIndeterminate(false);      //设置对话框进度条是否显示进度
            dialog.show();
        }

        @Override
        protected String doInBackground(URL... urls)
        {
            try
            {
                URLConnection connection = urls[0].openConnection();
                InputStream is = connection.getInputStream();
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath
                        ()+"/CaseAndroid";
                File file = new File(fileName);
                if(!file.exists())
                    file.mkdirs();
                file = new File(fileName,"demo.apk");
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                int total = connection.getContentLength();
                byte[] by = new byte[1024];
                while ((len = is.read(by)) != -1){
                    fos.write(by,0,len);
                    index += len;
                    int progress = (int)((float)index/total*100);
                    publishProgress(progress);
//                    SystemClock.sleep(1000);
                }
                fos.flush();
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return  error+"    "+e.getMessage();
            }
            return success;
        }

        @Override
        protected void onPostExecute(String str)
        {
            super.onPostExecute(str);
            dialog.dismiss();
            if (str.equals("ok"))
            {
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            try
            {
                Log.e("num",values[0]+"");
                dialog.setProgress(values[0]);
            }catch (Exception e){
                Log.e("error",e.getMessage()+"         onProgressUpdate");
            }

        }

        @Override
        protected void onCancelled()
        {
            super.onCancelled();
            dialog = null;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        dowTask.cancel(true);
        dowTask.onCancelled();
    }
}
