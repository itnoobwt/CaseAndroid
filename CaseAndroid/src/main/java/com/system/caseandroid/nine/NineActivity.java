package com.system.caseandroid.nine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import com.system.caseandroid.nine.net.OKhttpClientManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * 这一章节主要练习okhttp
 */
public class NineActivity extends BaseActivity
{

    @BindView(R.id.ok_select)
    Button okSelect;
    private InputStream is;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine);
    }


    @OnClick(R.id.ok_select)
    public void getHttps(){
        try
        {
            is = getAssets().open("SRCA.crt");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        HttpsNetWork httpsNetWork = new HttpsNetWork();
        httpsNetWork.execute("https://kyfw.12306.cn/otn/");

    }

    class HttpsNetWork extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Toast.makeText(NineActivity.this, "正在发起请求......", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params)
        {
            return OKhttpClientManager.getInstance().OKhttpRequest("https://kyfw.12306.cn/otn/",is);
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Toast.makeText(NineActivity.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(String s)
        {
            super.onCancelled(s);
        }
    }
}
