package com.example.cameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
{

    @BindView(R.id.came_btn)
    Button cameBtn;
    @BindView(R.id.vieo)
    Button vieo;
    @BindView(R.id.weixin_btn)
    Button weixinBtn;
    @BindView(R.id.retrofit)
    Button retrofit;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_view)
    Button btnView;
    @BindView(R.id.btn_service)
    Button btnService;
    @BindView(R.id.btn_sf)
    Button btnSf;
    @BindView(R.id.btn_pool)
    Button btnPool;
    @BindView(R.id.btn_gb)
    Button btnGb;
    @BindView(R.id.btn_dagger)
    Button btnDagger;
    @BindView(R.id.btn_img)
    Button btnImg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //        ButterKnife.bind(this);
    }

    @OnClick(R.id.came_btn)
    public void submitCamera(View view)
    {
        startActivity(new Intent(MainActivity.this, CameraActivity.class));
    }

    @OnClick(R.id.vieo)
    public void setVieo(View view)
    {
        startActivity(new Intent(MainActivity.this, VieoActivity.class));
    }

    @OnClick(R.id.weixin_btn)
    public void setWeixinBtn(View weixinBtn)
    {
        //仿微信录制视频
        startActivity(new Intent(MainActivity.this, MovieRecorderActivity.class));
    }

    @OnClick(R.id.retrofit)
    public void setRetrofit(View view)
    {
        //Retrofit网络框架用法
        startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
    }

    @OnClick(R.id.btn_ok)
    public void setBtnOk(View btnOk)
    {
        //OKHttp网络请求
        startActivity(new Intent(MainActivity.this, OKHttpActivity.class));
    }

    @OnClick(R.id.btn_view)
    public void setBtnView(View view)
    {
        startActivity(new Intent(MainActivity.this, CustomizeActivity.class));
    }

    @OnClick(R.id.btn_service)
    public void setBtnService(View view)
    {
        startActivity(new Intent(MainActivity.this, ServiceActivity.class));
    }

    @OnClick(R.id.btn_sf)
    public void setBtnSf(View view)
    {
        startActivity(new Intent(MainActivity.this, AlgorithmActivity.class));
    }

    @OnClick(R.id.btn_pool)
    public void setBtnPool(View view)
    {
        startActivity(new Intent(MainActivity.this, ThreadActivity.class));
    }

    @OnClick(R.id.btn_gb)
    public void setBtnGb(View view)
    {
        startActivity(new Intent(MainActivity.this, BroadCastActivity.class));
    }

    @OnClick(R.id.btn_dagger)
    public void setBtnDagger(View view)
    {

    }

    @OnClick(R.id.btn_img)
    public void setBtnImg(View v){
        startActivity(new Intent(MainActivity.this,ImageActivity.class));
    }
}
