package com.system.caseandroid.three;

import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.system.caseandroid.R;

/**
 * Configuration 类简介
 */
public class ConfigurationActivity extends AppCompatActivity
{
    /**
     *
     * @param savedInstanceState
     */


    private EditText et_1,et_2,et_3,et_4;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        et_1 = (EditText) findViewById(R.id.et_1);
        et_2 = (EditText) findViewById(R.id.et_2);
        et_3 = (EditText) findViewById(R.id.et_3);
        et_4 = (EditText) findViewById(R.id.et_4);
    }
    public void btn_config(View view){
        //获取手机设备上的配置信息
        Configuration configuration = getResources().getConfiguration();
        String s = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE ?"横向屏幕":"竖向屏幕";
        //mnc  获取移动信号的网络码、    mcc获取移动信号的国家码
        String mnCode = configuration.mnc + "   mcc"+configuration.mcc;
        //orientation  获取系统屏幕方向
        String naviName = configuration.orientation == Configuration.NAVIGATION_NONAV ? "没有方向控制" : configuration
                .orientation == Configuration.NAVIGATION_WHEEL ? "滚轮控制方向" : configuration.orientation ==
                Configuration.NAVIGATION_DPAD ?"方向控制方向" : "轨迹球控制方向";
        et_1.setText(naviName);
        //touchscreen 获取系统触摸屏的触摸方式
        String touchName = configuration.touchscreen == Configuration.TOUCHSCREEN_NOTOUCH ? "无触摸屏" : "支持触摸屏";
        et_2.setText(s);
        et_3.setText(mnCode);
        et_4.setText(touchName);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        String screen = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ?"横向屏幕":"竖向屏幕";
        Toast.makeText(this, screen, Toast.LENGTH_SHORT).show();

        Message message = handler.obtainMessage(1);
        message.sendToTarget();

        Message m = new Message();
        m.what = 1;
        m.obj = "123";
        handler.sendMessage(m);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what){

            }
        }
    };
}
