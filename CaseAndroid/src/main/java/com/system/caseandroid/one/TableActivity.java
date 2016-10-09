package com.system.caseandroid.one;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.system.caseandroid.R;

public class TableActivity extends AppCompatActivity
{
    private ToggleButton toggleButton;  //开关
    private Switch sASwitch;   //开关
    private Chronometer chronometer;  //计时器
    private QuickContactBadge quickContactBadge;  //联系人
    private AutoCompleteTextView autoCompleteTextView;  //自动补充
    private MultiAutoCompleteTextView multiAutoCompleteTextView; //自动补充
    private String[] books = new String[]{"疯狂Java讲义","疯狂Ajax","疯狂English"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        toggleButton = (ToggleButton) findViewById(R.id.tb);
        sASwitch = (Switch) findViewById(R.id.sh);
        chronometer = (Chronometer) findViewById(R.id.cm);
        chronometer.start();
        quickContactBadge = (QuickContactBadge) findViewById(R.id.qcb);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.act);
        multiAutoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.mact);
        toggleButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (toggleButton.isChecked())
                {
                    Toast.makeText(TableActivity.this, "选中", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(TableActivity.this, "未选中", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sASwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (sASwitch.isChecked())
                {
                    Toast.makeText(TableActivity.this, "开启", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(TableActivity.this, "关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        quickContactBadge.assignContactFromPhone("13611349442",true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout
                .support_simple_spinner_dropdown_item,books);
        autoCompleteTextView.setAdapter(arrayAdapter);
        multiAutoCompleteTextView.setAdapter(arrayAdapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
