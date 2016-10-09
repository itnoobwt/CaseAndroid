package com.system.caseandroid.one;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.system.caseandroid.R;

public class OneMainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_main);
        btn1 = (Button) findViewById(R.id.kj);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn10 = (Button) findViewById(R.id.btn10);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.kj:
                startActivity(new Intent(OneMainActivity.this,TableActivity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(OneMainActivity.this,AdapterViewFlipperActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(OneMainActivity.this,StackViewActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(OneMainActivity.this,ProgressBarActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(OneMainActivity.this,ViewAnimatorActivity.class));
                break;
            case R.id.btn6:
                startActivity(new Intent(OneMainActivity.this,TextSwitcherActivity.class));
                break;
            case R.id.btn7:
                startActivity(new Intent(OneMainActivity.this,ViewFlipperActivity.class));
                break;
            case R.id.btn8:
                startActivity(new Intent(OneMainActivity.this,CalendarViewActivity.class));
                break;
            case R.id.btn9:
                startActivity(new Intent(OneMainActivity.this,SearcheViewActivity.class));
                break;
            case R.id.btn10:
                startActivity(new Intent(OneMainActivity.this,NotificationActivity.class));
                break;
        }
    }
}
