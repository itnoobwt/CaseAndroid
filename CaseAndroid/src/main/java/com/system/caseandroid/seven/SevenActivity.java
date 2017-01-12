package com.system.caseandroid.seven;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import com.system.caseandroid.inputUtils.IOActivity;

public class SevenActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven);
        //ddd
    }

    public void bitmap(View view)
    {
        startActivity(new Intent(SevenActivity.this, BitmapActivity.class));
    }

    public void draw(View view)
    {
        startActivity(new Intent(SevenActivity.this, DrawActivity.class));
    }

    public void path(View view)
    {
        startActivity(new Intent(SevenActivity.this, PathActivity.class));
    }

    public void demo(View view)
    {
        startActivity(new Intent(SevenActivity.this, DemoActivity.class));
    }

    public void minGame(View v)
    {
        startActivity(new Intent(SevenActivity.this, BallActivity.class));
    }

    public void matrix(View v)
    {
        startActivity(new Intent(SevenActivity.this, MatrixActivity.class));
    }

    public void media(View view){
        startActivity(new Intent(SevenActivity.this, MediaActivity.class));
    }

    public void io(View view){
        startActivity(new Intent(SevenActivity.this, IOActivity.class));
    }

    public void upload(View v){
        startActivity(new Intent(SevenActivity.this, UploadActivity.class));
    }
}
