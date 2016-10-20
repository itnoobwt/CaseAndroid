package com.system.caseandroid.six;

import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.system.caseandroid.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 讲解Resoures资源使用
 * StateListDrawable 资源使用
 * LayerDrawable 资源使用
 * ShapeDrawable 资源使用
 * ClipDrawable  对象使用
 */
public class ResouresActivity extends AppCompatActivity
{
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resoures);




        imageView = (ImageView) findViewById(R.id.image);
        final ClipDrawable drawable = (ClipDrawable)imageView.getDrawable();
        Log.e("level",drawable.getLevel()+"       =");
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                if(msg.what == 0x12333)
                    drawable.setLevel(drawable.getLevel() + 200);
            }
        };

        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {
                    Message msg = new Message();
                    msg.what = 0x12333;
                    handler.sendMessage(msg);

                    if(drawable.getLevel() >=10000)
                        timer.cancel();
                }catch (Exception e){
                    Log.e("error",e.getMessage());
                }
            }
        },0,300);
    }

}
