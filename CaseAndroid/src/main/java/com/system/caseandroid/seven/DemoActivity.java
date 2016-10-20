package com.system.caseandroid.seven;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import com.system.caseandroid.seven.widget.DrawView;

public class DemoActivity extends BaseActivity
{
    EmbossMaskFilter emboss;
    BlurMaskFilter blur;
    DrawView drawView;
    DisplayMetrics displayMetrics;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        displayMetrics = new DisplayMetrics();
        //获取创建的宽度和高度
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        drawView = new DrawView(this,displayMetrics.widthPixels,displayMetrics.heightPixels);
        linearLayout.addView(drawView);
        setContentView(linearLayout);

        emboss = new EmbossMaskFilter(new float[]{15.f,1.5f,1.5f}
        ,0.f,6,4.2f);
        blur = new BlurMaskFilter(8,BlurMaskFilter.Blur.NORMAL);  //线条模糊
    }
    @Override
    // 负责创建选项菜单
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = new MenuInflater(this);
        // 装载R.menu.my_menu对应的菜单，并添加到menu中
        inflator.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    // 菜单项被单击后的回调方法
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        // 判断单击的是哪个菜单项，并有针对性地作出响应
        switch (mi.getItemId())
        {
            case R.id.red:
                drawView.paint.setColor(Color.RED);
                mi.setChecked(true);
                break;
            case R.id.green:
                drawView.paint.setColor(Color.GREEN);
                mi.setChecked(true);
                break;
            case R.id.blue:
                drawView.paint.setColor(Color.BLUE);
                mi.setChecked(true);
                break;
            case R.id.width_1:
                drawView.paint.setStrokeWidth(1);
                break;
            case R.id.width_3:
                drawView.paint.setStrokeWidth(3);
                break;
            case R.id.width_5:
                drawView.paint.setStrokeWidth(5);
                break;
            case R.id.blur:
                drawView.paint.setMaskFilter(blur);
                break;
            case R.id.emboss:
                drawView.paint.setMaskFilter(emboss);
                break;
            case R.id.clear:
                drawView.clearCanvas(displayMetrics.widthPixels,displayMetrics.heightPixels);
                break;
        }
        return true;
    }
}
