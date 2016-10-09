package com.system.caseandroid.three;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.system.caseandroid.R;

/**
 * 疯狂Android讲义第三版第三章
 */
public class ThreeMainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_main);
    }
    public void plane(View v){
        startActivity(new Intent(ThreeMainActivity.this,PlaneActivity.class));
    }
    public void sendSms(View view){
        startActivity(new Intent(ThreeMainActivity.this,SendSmsActivity.class));
    }
    public void config(View v){startActivity(new Intent(ThreeMainActivity.this,ConfigurationActivity.class));}
    public void handler(View v){startActivity(new Intent(ThreeMainActivity.this,HandlerActivity.class));}
    public void asyncTask(View v){startActivity(new Intent(ThreeMainActivity.this,AsyncTaskActivity.class));}
}
