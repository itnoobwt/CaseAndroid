package com.system.caseandroid.seven;

import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import com.system.caseandroid.BaseActivity;

public class PathActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(PathActivity.this));
    }

    class MyView extends View{

        private float phase;
        PathEffect[] effects = new PathEffect[7];
        int[] colors;
        private Paint paint;
        private Path path;
        public MyView(Context context )
        {
            super(context);
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            //创建并初始化Path
            path = new Path();
            path.moveTo(0,0);
            for (int i = 1; i<=40; i++){
                path.lineTo(i*20,(float)Math.random()*60);
            }
            //初始化7颜色
            colors = new int[]{Color.BLACK,Color.BLUE,Color.CYAN,Color.GREEN
            ,Color.MAGENTA,Color.RED,Color.YELLOW};
        }


        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            //将北京填充为白色
            canvas.drawColor(Color.LTGRAY);
            //------------- 下面开始初始化7中路径效果   -----------------
            //不适用路径效果
            effects[0] = null;
            //使用CornerPathEffect路径效果
            effects[1] = new CornerPathEffect(10);
            //初始化DiscretePathEffect
            effects[2] = new DiscretePathEffect(3.0f,5.0f);
            //初始化DashPathEffect
            effects[3] = new DashPathEffect(new float[]{20,10,5,10},phase);
            //初始化PathDashPathEffect
            Path p = new Path();
            p.addRect(0,0,8,8, Path.Direction.CCW);
            effects[4] = new PathDashPathEffect(p,12,phase,PathDashPathEffect.Style.ROTATE);
            //初始化ComposePathEffect
            effects[5] = new ComposePathEffect(effects[2],effects[4]);
            effects[6] = new SumPathEffect(effects[4],effects[3]);
            //将画布移动到（8，8）处开始绘制
            canvas.translate(8,8);
            //依次使用7种不同的路径效果、7种不同的颜色来绘制路径
            for (int i = 0; i< effects.length; i++){
                paint.setPathEffect(effects[i]);
                paint.setColor(colors[i]);
                canvas.drawPath(path,paint);
                canvas.translate(0,60);
            }
            //改变phase值，形成动画效果
            phase += 1;
            invalidate();
        }
    }


}
