package com.example.cameraapp.widegt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by user on 2016/7/6.
 */
public class FirstView extends ImageView
{
    public Paint paint;
    public FirstView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
//        drawArc 绘制弧
//        drawBitmap 绘制位图
//        drawCircle 绘制圆形
//        drawLine 绘制线
//        drawOval 绘制椭圆
//        drawPath 绘制路径
//        drawPoint 绘制一个点
//        drawPoints 绘制多个点
//        drawRect 绘制矩形
//        drawRoundRect 绘制圆角矩形
//        drawText 绘制字符串
//        drawTextOnPath 沿着路径绘制字符串
//        setARGB/setColor 设置颜色
//        setAlpha 设置透明度
//        setAntiAlias 设置是否抗锯齿
//        setShader 设置画笔的填充效果
//        setShadowLayer 设置阴影
//        setStyle 设置画笔风格
//        setStrokeWidth 设置空心边框的宽度
//        setTextSize 设置绘制文本时文字的大小


//        paint.setAntiAlias(true); //设置锯齿
//        paint.setColor(Color.YELLOW);
//        canvas.drawCircle(200,200,100,paint);

        paint.setStyle(Paint.Style.STROKE);//设置空心圆
        paint.setStrokeWidth(10);
        canvas.drawCircle(200,200,100,paint);


    }
}
