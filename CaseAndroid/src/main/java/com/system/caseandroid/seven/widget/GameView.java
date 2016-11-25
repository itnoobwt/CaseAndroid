package com.system.caseandroid.seven.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import java.util.Random;

/**
 * Created by user on 2016/10/20.
 */

public class GameView extends View
{
    public Paint paint;
    // 游戏是否结束的旗标
    private boolean isLose = false;
    public DisplayMetrics displayMetrics;
    //球拍的垂直位置
    public int racketY;
    //定义球拍的高度和宽度
    private final int RACKET_HEIGHT = 30;
    public final int RACKET_WIDTH = 30;

    //小球的大小
    private final int BALL_SIZE = 16;

    //小球纵向的运行速度
    private int ySpeed = 15;

    Random rand = new Random();
    private int ballX = rand.nextInt(200) + 20;
    private int ballY = rand.nextInt(10) + 10;
    public int racketX = rand.nextInt(200);

    public GameView(Context context,Display display)
    {
        super(context);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(isLose){
            paint.setColor(Color.RED);
            paint.setTextSize(40);
            canvas.drawText("游戏已结束",displayMetrics.widthPixels/2-100,200,paint);
        }else{
            //设置颜色，并绘制小球
            paint.setColor(Color.rgb(255,0,0));
            canvas.drawCircle(ballX,ballY,BALL_SIZE,paint);

            //设置颜色，并绘制球拍
            paint.setColor(Color.rgb(80,80,200));
            canvas.drawRect(racketX,racketY,racketX+RACKET_WIDTH,racketY+RACKET_HEIGHT,paint);
        }
    }
}
