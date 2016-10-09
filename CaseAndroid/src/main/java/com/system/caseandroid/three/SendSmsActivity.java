package com.system.caseandroid.three;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.system.caseandroid.R;
import com.system.caseandroid.one.adapter.ViewFipperAdapter;

public class SendSmsActivity extends AppCompatActivity
{
    private EditText et,editText;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        et = (EditText) findViewById(R.id.address);
        editText = (EditText) findViewById(R.id.content);
        btn = (Button) findViewById(R.id.btn_send);
        btn.setOnLongClickListener(new SendSmsListener(this,et,editText));
    }


}
