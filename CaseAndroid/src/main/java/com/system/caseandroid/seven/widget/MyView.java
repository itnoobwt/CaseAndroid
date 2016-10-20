package com.system.caseandroid.seven.widget;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 2016/10/14.
 */

public class MyView extends View
{
    public MyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setShadowLayer(25,20,20,Color.GRAY);
        int viewWidth = this.getWidth();
        canvas.drawCircle(viewWidth/10+10,viewWidth/10+10,viewWidth/10,paint);  //圆形
        canvas.drawRect(10,viewWidth/5+20,viewWidth/5+10,viewWidth*2/5+20,paint);//正方形
        canvas.drawRoundRect(10,viewWidth/2+40,10+viewWidth/5,viewWidth*3/5+40,15,15,paint); //绘制圆角矩形
        RectF re11 = new RectF(10, viewWidth * 3 / 5 + 50
                , 10 + viewWidth / 5 ,viewWidth * 7 / 10 + 50);
        canvas.drawOval(re11,paint); //椭圆

        Path path = new Path();
        path.moveTo(10,viewWidth*9/10+60);
        path.lineTo(viewWidth/5+10,viewWidth*9/10+60);
        path.lineTo(viewWidth/10+10,viewWidth*7/10+60);
        path.close();
        canvas.drawPath(path,paint); //绘制三角形
    }
}
