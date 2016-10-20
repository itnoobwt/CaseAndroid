package com.system.caseandroid.six;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.system.caseandroid.R;

public class SixMainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_main);
    }
    public void res(View v){
        startActivity(new Intent(SixMainActivity.this,ResouresActivity.class));
    }
    public void animation(View v){startActivity(new Intent(SixMainActivity.this,AnimationActivity.class));}

    public void objectanim(View view){ startActivity(new Intent(SixMainActivity.this,ObjectAnimatorActivity.class));}

    public void xml(View v){ startActivity(new Intent(SixMainActivity.this,OriginalActivity.class));}

    public void menu(View v){ startActivity(new Intent(SixMainActivity.this,CustomActivity.class));}

    public void sound(View v){ startActivity(new Intent(SixMainActivity.this,SoundActivity.class));}

    public void language(View v){ startActivity(new Intent(SixMainActivity.this,LanguageActivity.class));}
}
