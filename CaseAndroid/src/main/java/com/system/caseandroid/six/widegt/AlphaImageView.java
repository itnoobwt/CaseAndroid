package com.system.caseandroid.six.widegt;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.system.caseandroid.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2016/10/12.
 */

public class AlphaImageView extends ImageView
{
    private int alphaDelta = 0;
    private int curAlpha = 0;
    private final int SPEED = 300;
    private int duration=0;
//    public AlphaImageView(Context context)
//    {
//        super(context);
//    }

    public AlphaImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphaImageView);
        duration = typedArray.getInt(R.styleable.AlphaImageView_duration,0);
        alphaDelta = 255*SPEED/duration;
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if(msg.what == 0x123){
                curAlpha += alphaDelta;
                if(curAlpha > 600) curAlpha = 600;
                AlphaImageView.this.setImageAlpha(curAlpha);
            }
        }
    };

    @Override
    protected void onDraw(Canvas canvas)
    {
        AlphaImageView.this.setImageAlpha(curAlpha);
        super.onDraw(canvas);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {

            @Override
            public void run()
            {
                Message msg = new Message();
                msg.what = 0x123;
                if(curAlpha>=255){
                    timer.cancel();
                }
                else
                    handler.sendMessage(msg);
            }
        },0,SPEED);

    }




}
