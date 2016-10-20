package com.system.caseandroid.Fives;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.system.caseandroid.R;

public class FivesMainActivity extends AppCompatActivity
{
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fives_main);
        btn = (Button) findViewById(R.id.btn_intent);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ComponentName comp = new ComponentName(FivesMainActivity.this,SecondActivity.class);
                Intent intent = new Intent();
                intent.setComponent(comp);
                startActivity(intent);
            }
        });
    }
    public void extra(View v){
        startActivity(new Intent(FivesMainActivity.this,ExtraActivity.class));
    }
}
