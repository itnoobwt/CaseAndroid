package com.example.cameraapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.cameraapp.logutlis.LogUtlis;

public class BroadCastActivity extends AppCompatActivity
{

    @BindView(R.id.btn_reg_gb)
    Button btnRegGb;
    @BindView(R.id.btn_cancel_gb)
    Button btnCancelGb;
    public BroadcastReceiver receiver;
    @BindView(R.id.btn_send_gb)
    Button btnSendGb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);
        ButterKnife.bind(this);
    }

    public void registered()
    {
        receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                String msg = intent.getStringExtra("gb");
                LogUtlis.e("BroadCastReceiveByXml","动态注册广播");
                //Toast.makeText(BroadCastActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter intentFilter = new IntentFilter("wt");
        registerReceiver(receiver, intentFilter);
    }

    @OnClick(R.id.btn_reg_gb)
    public void setBtnRegGb(View view)
    {
        registered();
    }

    @OnClick(R.id.btn_cancel_gb)
    public void setBtnCancelGb(View view)
    {

        unregisterReceiver(receiver);

    }
    @OnClick(R.id.btn_send_gb)
    public void setBtnSendGb(View v){
        Intent intent = new Intent("wt");
        intent.putExtra("gb","广播");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }
}
