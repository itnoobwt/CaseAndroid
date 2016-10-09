package com.example.cameraapp;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.cameraapp.fileUtils.FileUtils;
import com.example.cameraapp.logutlis.LogUtlis;
import com.example.cameraapp.okhttp.OKHttpUtlis;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;

import java.io.*;

public class OKHttpActivity extends AppCompatActivity
{

    @BindView(R.id.btn_get)
    Button btnGet;
    @BindView(R.id.btn_post)
    Button btnPost;
    @BindView(R.id.btn_download)
    Button btnDownload;
    @BindView(R.id.imageView)
    SimpleDraweeView imageView;
    @BindView(R.id.btn_str)
    Button btnStr;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.data_input)
    Button dataInput;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
        if (savedInstanceState != null)
        {
            String name = savedInstanceState.getString("a");
            Toast.makeText(OKHttpActivity.this, name + "==onCreate", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_get)
    public void setBtnGet(View btnGet)
    {
        OKHttpUtlis.getInstance().okget();
    }

    @OnClick(R.id.btn_post)
    public void setBtnPost(View view)
    {
        OKHttpUtlis.getInstance().post();
        Toast.makeText(OKHttpActivity.this, "写入成功，Camerapp/dog.txt", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_download)
    public void setBtnDownload(View view)
    {
        //http://www.chundogs.com//ShoppingAdmin/upload/apk/2016030218022449845z.apk
        //http://txt.rain8.com/uploads/soft/1412/中国散文鉴赏文库(450篇精品美文)_雨枫轩Rain8.com.rar
        //http://d.5857.com/chongwugoutupian.121109/011.jpg
        //http://www.xiufa.com/BJUI/plugins/kindeditor_4.1.10/php/../attached/image/20160427/20160427020327_69298.png
        //http://vs6.bdstatic.com/browse_static/v3/common/widget/global/admTemplate/advPlayer_6d2b673e.swf
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载中.....");
        //        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        //        OKHttpUtlis.getInstance()._downloadAsyn("http://www.xiufa.com/BJUI/plugins/kindeditor_4.1.10/php/../attached/image/20160427/20160427020327_69298.png",
        //                FileUtils.createFile("appDownload"), null, imageView, progressDialog);

        OKHttpUtlis.getInstance()._downloadAsyn1("http://www.xiufa.com/BJUI/plugins/kindeditor_4.1" + ".10/php/../attached/image/20160427/20160427020327_69298.png", FileUtils.createFile("Camerapp"), new OKHttpUtlis.ResultCallback<String>()
        {
            @Override
            public void onError(Request request, Exception e)
            {

            }

            @Override
            public void onResponse(String str)
            {
                imageView.setImageURI(Uri.parse("file://" + str));
                progressDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.btn_str)
    public void setBtnStr(View view)
    {
        String name = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Camerapp/dog.txt";
        try
        {
            //获取到文件夹，通过FileInputStream把指定的文件夹转换成流
            InputStream inputStream = new FileInputStream(name);

            //            DataInputStream dis = new DataInputStream(inputStream);
            //            try
            //            {
            //                String s = dis.readUTF();
            //                web.loadData(s,"application/json; charset=utf-8","utf-8");
            //            }
            //            catch (IOException e)
            //            {
            //                e.printStackTrace();
            //            }


            //通过InputStreamReader去读取流
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            //通过BufferedReader去读取InputStreamReader
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuffer buffer = new StringBuffer();
            String txt = null;
            try
            {
                while ((txt = reader.readLine()) != null)
                {
                    buffer.append(txt + "/n"); //对字符串追加
                }
                web.loadData(buffer.toString(), "application/json; charset=utf-8", "utf-8");
                LogUtlis.e("wt", buffer.toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("a", "张三");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        String name = savedInstanceState.getString("a");
        Toast.makeText(OKHttpActivity.this, name, Toast.LENGTH_SHORT).show();
    }
}
