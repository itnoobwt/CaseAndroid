package com.system.caseandroid.seven;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;

public class SevenActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven);
    }
    public void bitmap(View view){
        startActivity(new Intent(SevenActivity.this,BitmapActivity.class));
    }

    public void draw(View view){
        startActivity(new Intent(SevenActivity.this,DrawActivity.class));
    }
    public void path(View view){
        startActivity(new Intent(SevenActivity.this,PathActivity.class));
    }

    public void demo(View view){ startActivity(new Intent(SevenActivity.this,DemoActivity.class));}
}
