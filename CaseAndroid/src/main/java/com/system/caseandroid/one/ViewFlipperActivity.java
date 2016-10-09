package com.system.caseandroid.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;
import com.system.caseandroid.R;

public class ViewFlipperActivity extends AppCompatActivity implements View.OnClickListener
{
    private ViewFlipper flipper;
    private Button btn_left,btn_center,btn_right;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        flipper = (ViewFlipper) findViewById(R.id.vf);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_center = (Button) findViewById(R.id.btn_center);
        btn_right = (Button) findViewById(R.id.btn_right);
        btn_left.setOnClickListener(this);
        btn_center.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_left:
                flipper.showPrevious();
                flipper.stopFlipping();
                break;
            case R.id.btn_center:
                flipper.startFlipping();
                break;
            case R.id.btn_right:
                flipper.showNext();
                flipper.stopFlipping();
                break;
        }
    }
}
