package com.example.cameraapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.cameraapp.adapter.DividerGridItemDecoration;
import com.example.cameraapp.adapter.RetrofitAdapter;
import com.example.cameraapp.logutlis.LogUtlis;
import com.example.cameraapp.modle.Student;
import com.example.cameraapp.retrofit.utlis.RetrofitUtlist;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Retrofit提供常用方法
 * 用到的包 'com.squareup.retrofit2:retrofit:2.0.2'
 * •一般的get、post请求
 * •动态url，动态参数设置，各种注解的使用
 * •上传文件（单文件，多文件上传等）
 * •下载文件等（这个不推荐retrofit去做，具体看下文）
 */
public class RetrofitActivity extends AppCompatActivity
{


    public static String host = "http://192.168.1.145:8080/Upload/";
    @BindView(R.id.tv_get)
    TextView tvGet;
    @BindView(R.id.tv_post)
    TextView tvPost;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private RetrofitAdapter adapter;
    private List<Student> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        datalist = new ArrayList<Student>();
        adapter = new RetrofitAdapter(RetrofitActivity.this, datalist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerGridItemDecoration(this,OrientationHelper.VERTICAL));
        rv.setAdapter(adapter);
    }

    @OnClick({R.id.tv_get, R.id.tv_post})
    public void setClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_get:
                progressBar.setVisibility(View.VISIBLE);
                RetrofitUtlist.getNewRetrofit().getAll("1", host);
                break;
            case R.id.tv_post:

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void setData(List<Student> list)
    {
        LogUtlis.e("setData","--------------------------------");
        progressBar.setVisibility(View.GONE);
        datalist.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        EventBus.getDefault().register(this);
    }
}
