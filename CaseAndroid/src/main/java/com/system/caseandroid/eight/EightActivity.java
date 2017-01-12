package com.system.caseandroid.eight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import com.system.caseandroid.eight.activity.FragmentOneActivity;

/**
 * 学习片段
 */
public class EightActivity extends BaseActivity
{

    @BindView(R.id.btn_1)
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);
    }
    @OnClick(R.id.btn_1)
    public void fragment(){
        startActivity(new Intent(EightActivity.this, FragmentOneActivity.class));
    }
}
