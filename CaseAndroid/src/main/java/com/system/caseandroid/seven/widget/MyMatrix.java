package com.system.caseandroid.seven.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.system.caseandroid.R;

/**
 * Created by user on 2016/11/18.
 */

public class MyMatrix extends View
{
    private Bitmap bitmap;
    private int width,height;
    public boolean isScale = false;
    private Matrix matrix;
    public float sx = 0f;
    public float scale = 1f;

    public MyMatrix(Context context)
    {
        super(context);
        Log.e("MyMatrix","1111111111111111111111111111111");
    }

    public MyMatrix(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        Log.e("MyMatrix","22222222222222222222222222222222222");
        bitmap = ((BitmapDrawable)context.getResources().getDrawable(R.mipmap.xiongmao)).getBitmap();
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        //重置Matrix
        matrix.reset();
        if(!isScale){
            //旋转Matrix
            matrix.setSkew(sx,0);
        }
        else{
            //缩放Matrix
            matrix.setScale(scale,scale);
        }
        //根据原始位图和Matrix创新新图
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
        canvas.drawBitmap(bitmap2,matrix,null);
    }


}
