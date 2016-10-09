package com.system.caseandroid.one.widegt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by user on 2016/9/13.
 */
public class DrawView extends View
{
    private Paint mPaint;
    private float currentX = 40;
    private float currentY = 50;
    public DrawView(Context context)
    {
        super(context);

    }
    public DrawView(Context context, AttributeSet set)
    {
        super(context,set);
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(currentX,currentY,15,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        currentX = event.getX();
        currentY = event.getY();
        invalidate();
        return true;
    }
}
