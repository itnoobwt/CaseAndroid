package com.system.caseandroid.three.widegt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.system.caseandroid.R;

/**
 * Created by user on 2016/9/27.
 */

public class PlaneView extends View
{
    private Bitmap plane;
    public float currentX;
    public float currentY;
    public PlaneView(Context context)
    {
        super(context);
        plane = BitmapFactory.decodeResource(context.getResources(), R.mipmap.plane);
        currentX = plane.getWidth();
        currentY = plane.getHeight();
//        setFocusable(true);
    }

    public PlaneView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        canvas.drawBitmap(plane,currentX,currentY,paint);
    }
}
