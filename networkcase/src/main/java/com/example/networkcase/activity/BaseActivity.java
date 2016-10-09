package com.example.networkcase.activity;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class BaseActivity extends AppCompatActivity
{
    public List<Activity> list = new ArrayList<Activity>();
    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}

