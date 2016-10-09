package com.example.cameraapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.cameraapp.logutlis.LogUtlis;
import com.example.cameraapp.service.LocalService;

public class ServiceActivity extends AppCompatActivity
{
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.btn_unbind)
    Button btnUnbind;
    @BindView(R.id.textView)
    TextView textView;
    private ServiceConnection connection;
    private boolean isBind=true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);

        connection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder)
            {
                LocalService.SimpleBinder binder = (LocalService.SimpleBinder) iBinder;
                LogUtlis.e(binder.add(3, 5) + "");
                textView.setText("3+5="+binder.add(3,5));
                LogUtlis.e(binder.getLocalService().toString());
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName)
            {
                LogUtlis.e("onServiceDisconnected");
            }
        };
    }
    @OnClick(R.id.btn_bind)
    public void setBtnBind(View view)
    {
//        startService(new Intent(ServiceActivity.this, LocalService.class));

        bindService(new Intent(ServiceActivity.this, LocalService.class), connection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.btn_unbind)
    public void setBtnUnbind(View view)
    {
        if (isBind)
        {
            LogUtlis.e(isBind+"");
//            stopService(new Intent(ServiceActivity.this,LocalService.class));
            unbindService(connection);
            isBind = false;
            Toast.makeText(ServiceActivity.this, "ss", Toast.LENGTH_SHORT).show();
        }
    }

}
