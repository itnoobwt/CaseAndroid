package com.system.caseandroid.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import com.system.caseandroid.R;
import com.system.caseandroid.one.adapter.ViewFipperAdapter;

public class AdapterViewFlipperActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btn_left,btn_center,btn_right;
    private AdapterViewFlipper viewFlipper;
    private ViewFipperAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_view_flipper);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_center = (Button) findViewById(R.id.btn_center);
        btn_right = (Button) findViewById(R.id.btn_right);
        viewFlipper = (AdapterViewFlipper) findViewById(R.id.avf);
        adapter = new ViewFipperAdapter(this);
        viewFlipper.setAdapter(adapter);
        btn_left.setOnClickListener(this);
        btn_center.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_left:
                viewFlipper.showPrevious();
                viewFlipper.stopFlipping();
                break;
            case R.id.btn_center:
                viewFlipper.startFlipping();
                break;
            case R.id.btn_right:
                viewFlipper.showNext();
                viewFlipper.stopFlipping();
                break;
        }
    }
}
