package com.system.caseandroid.seven;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.MyApplication;
import com.system.caseandroid.R;
import com.system.caseandroid.seven.utils.UploadListener;
import com.system.caseandroid.utils.FileUtils;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends BaseActivity
{

    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    @BindView(R.id.btn_album)
    Button btnAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }
    @OnClick(R.id.btn_album)
    public void xiangche()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");  //选择内容为图片
        intent.putExtra("return-data", false);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED)
        {
            return;
        }
        if (requestCode == 3)
        {
            photo.setImageURI(data.getData());
        }
    }
    @OnClick(R.id.btn_upload)
    public void upload(){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Map<String,String> params = new HashMap<>();
                params.put("name","张三");
                uploadForm(params, new File[]{new File(FileUtils.getInstance().getImageDir()+"/1.jgp")}, "http://192.168.1.144:9128/Upload/ServletUpload",
                        "1.jpg", "电脑",
                        new UploadListener()
                                {
                                    @Override
                                    public void uploadSuccess(String str)
                                    {
                                        if(str.equals("200")){
                                            Looper.prepare();
                                            Toast.makeText(UploadActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                            Looper.loop();
                                        }
                                    }
                                });
            }
        }).start();

    }

    /**
     * 上传文件
     * @param params  参数
     * @param files  多个文件
     * @param urlStr 上传地址
     * @param fileFormName 文件名称
     * @param newFileName  新文件名称
     * @param uploadListener 监听上传是否成功
     */
    public void uploadForm(Map<String,String> params, File[] files, String urlStr, String fileFormName,
                           String newFileName, UploadListener uploadListener){
        long fileLength = 0;
        StringBuilder stringBuilder = new StringBuilder();
        /**
         * 表单数据
         */
        for (String key : params.keySet()){
            stringBuilder.append("Content-Disposition:form-data;name=\""+key+"\"\r\n");
            stringBuilder.append(params.get(key)+"\r\n");
            stringBuilder.append("\r\n");
        }
        /**
         * 上传文件头
         */
        for (File flie : files){
            stringBuilder.append("Content-Disposition:form-data;name=\""+fileFormName+"\";" +
                    "filename=\""+newFileName+"\""+"\r\n");
            stringBuilder.append("Content-Type:image/jpeg"+"\r\n");// 如果服务器端有文件类型的校验，必须明确指定ContentType
            stringBuilder.append("\r\n");
            fileLength+= flie.length();
        }
        try
        {
            byte[] headerInfo = stringBuilder.toString().getBytes("UTF-8");
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg," +
                    " application/x-shockwave-flash, application/xaml+xml," +
                    " application/vnd.ms-xpsdocument, application/x-ms-xbap, " +
                    "application/x-ms-application, application/vnd.ms-excel," +
                    " application/vnd.ms-powerpoint, application/msword, */*");
            connection.setRequestProperty("Content-Type","multipart/form-data");
            connection.setRequestProperty("Content-Length",String.valueOf(headerInfo.length+fileLength));  //文件+参数=总长度
            connection.setRequestProperty("Connection", "Keep-Alive"); //表示是否需要持久连接。（HTTP 1.1默认进行持久连接）
            connection.setRequestProperty("Charset", "UTF-8"); //编码格式
            OutputStream out = connection.getOutputStream();
            out.write(headerInfo);
            for (File flie : files){
                int len = 0;
                byte[] buf = new byte[1024];
                try
                {
                    InputStream inputStream = new FileInputStream(flie);
                    while ((len = inputStream.read(buf))!=-1){
                        out.write(buf,0,len);
                    }
                    inputStream.close();
                    out.close();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
            if(connection.getResponseCode() == 200){
                uploadListener.uploadSuccess(String.valueOf(connection.getResponseCode()));
            }else{
                uploadListener.uploadSuccess(String.valueOf(connection.getResponseCode()));
            }

        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
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


}
