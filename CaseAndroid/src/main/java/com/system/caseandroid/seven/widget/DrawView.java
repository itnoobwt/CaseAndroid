package com.system.caseandroid.seven.widget;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;

/**绘制，双缓冲技术
 * Created by user on 2016/10/17.
 */

public class DrawView extends View
{
    float preX;
    float preY;
    private Path path;
    public Paint paint;
    //定义内存中的一个图片，该图片将作为缓冲区
    Bitmap cacheBitmap = null;
    //定义cacheBitmap上的Canvas对象
    private Canvas cacheCanvas = null;
    private Canvas c;
    public DrawView(Context context, int width,int height)
    {
        super(context);
        //创建一个该View具有项目大小的缓冲区
        cacheBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        path = new Path();
        cacheCanvas.setBitmap(cacheBitmap);
        //设置画笔的颜色
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setColor(Color.RED);
        // 设置画笔风格
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        //反锯齿
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(preX,preY,x,y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_UP:
                //讲cacheBitmap绘制到该View组件上
                cacheCanvas.drawPath(path,paint);
                path.reset();  //清除掉path里的线条和曲线
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        this.c = canvas;
        Paint bmpPaint = new Paint();
        canvas.drawBitmap(cacheBitmap,0,0,bmpPaint);
        canvas.drawPath(path,paint);
    }

    public void clearCanvas(int width,int height){
        c.restore();
//        float f = paint.getStrokeWidth();
//        paint = new Paint();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        cacheCanvas.drawPaint(paint);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
//
//
//        cacheBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
//        cacheCanvas = new Canvas();
//        paint = new Paint(Paint.DITHER_FLAG);
//        cacheCanvas.setBitmap(cacheBitmap);
//        // 设置画笔风格
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setAntiAlias(true);
//        paint.setDither(true);
//        paint.setStrokeWidth(f);
            invalidate();
//        cacheBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
//        cacheCanvas = new Canvas();
//        path = new Path();
//        cacheCanvas.setBitmap(cacheBitmap);
//        //设置画笔的颜色
//        paint = new Paint(Paint.DITHER_FLAG);
//        paint.setColor(Color.RED);
//        // 设置画笔风格
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(1);
//        //反锯齿
//        paint.setAntiAlias(true);
//        paint.setDither(true);
    }
}
