package com.example.cameraapp;

import android.os.Bundle;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.cameraapp.imageUtils.MyBitmapUtils;

public class ImageActivity extends BaseActivity
{

    @BindView(R.id.imageView2)
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        MyBitmapUtils.getInstance().disPlay(imageView2,"http://scimg.jb51.net/allimg/160815/103-160Q509544OC.jpg");
    }
}
