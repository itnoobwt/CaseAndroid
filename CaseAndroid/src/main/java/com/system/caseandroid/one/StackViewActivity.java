package com.system.caseandroid.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.StackView;
import com.system.caseandroid.R;
import com.system.caseandroid.one.adapter.StackViewAdapter;
import com.system.caseandroid.one.adapter.ViewFipperAdapter;

public class StackViewActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btn_left,btn_right;
    private StackView stackView;
    private StackViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack_view);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);
        stackView = (StackView) findViewById(R.id.sv);
        adapter = new StackViewAdapter(this);
        stackView.setAdapter(adapter);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.btn_left:
                stackView.showPrevious();
                break;

            case R.id.btn_right:
                stackView.showNext();
                break;
        }
    }
}
