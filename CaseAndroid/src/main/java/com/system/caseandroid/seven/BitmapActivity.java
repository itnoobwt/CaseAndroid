package com.system.caseandroid.seven;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;

public class BitmapActivity extends BaseActivity
{

    @BindView(R.id.btn_bitmap)
    Button btnBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
    }

    @OnClick(R.id.btn_bitmap)
    public void start(){
        startActivity(new Intent(BitmapActivity.this, BitmapCaseActivity.class));
    }

    public void btnBitmap(View view)
    {
        startActivity(new Intent(BitmapActivity.this, ActivityBitmapAssetsActivity.class));
    }
}
