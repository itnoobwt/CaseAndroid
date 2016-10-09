package com.example.cameraapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.cameraapp.algorithm.Algorithm;

public class AlgorithmActivity extends AppCompatActivity
{

    @BindView(R.id.btn_zc)
    Button btnZc;
    @BindView(R.id.btn_ks)
    Button btnKs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_zc)
    public void setBtnZc(View view)
    {
        Algorithm.insertSort();
    }
    @OnClick(R.id.btn_ks)
    public void setBtnKs(View view){
        Algorithm algorithm = new Algorithm();
        algorithm.quickSort();
    }
}
