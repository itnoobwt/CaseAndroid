package com.example.cameraapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.cameraapp.okhttp.OKHttpUtlis;

import java.io.File;
import java.util.UUID;

public class VieoActivity extends AppCompatActivity
{


    @BindView(R.id.btn_vieo)
    Button btnVieo;

    public static int ONE = 1;
    public static int TWO = 2;

    public String vieoPath = MyApplication.videoDir.toString();
    @BindView(R.id.vieo_view)
    VideoView vieoView;
    @BindView(R.id.select_video)
    Button selectVideo;
    public File path;
    @BindView(R.id.vieo_star)
    Button vieoStar;
    @BindView(R.id.btn_upload)
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vieo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_vieo)
    public void setBtnVieo(View view)
    {
        String url = UUID.randomUUID().toString() + ".mp4";
        path = new File(vieoPath, url);
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);//MediaStore.INTENT_ACTION_VIDEO_CAMERA
        startActivityForResult(intent, ONE);
    }

    @OnClick(R.id.select_video)
    public void setSelectVideo(View video)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, TWO);
    }

    public String vieoUrl;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED)
        {
            return;
        }
        if (requestCode == ONE)
        {
            vieoView.setVideoPath(data.getData().toString());
        }
        if (requestCode == TWO)
        {
            vieoView.setVideoPath(data.getData().toString());
        }
        vieoUrl = data.getData().toString();
        vieoView.start();
        //        vieoView.setVideoPath(vieoPath);
    }

    @OnClick(R.id.vieo_star)
    public void setVieoStar()
    {
        vieoView.start();
    }

    public void upload()
    {
        OKHttpUtlis.getInstance().postUpload("http://192.168.1.133:8080/power/locationAction!saveLocation", vieoUrl);
    }
    @OnClick(R.id.btn_upload)
    public void setBtnUpload(){
        upload();
    }
}
