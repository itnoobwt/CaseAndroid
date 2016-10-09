package com.system.caseandroid.three;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import com.system.caseandroid.three.widegt.PlaneView;

public class PlaneActivity extends AppCompatActivity
{
    private float speedX = 0;
    private float speedY = 0;
    private float x;
    private float y;
    private PlaneView planeView;
    DisplayMetrics displayMetrics;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
        ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        planeView = new PlaneView(this);
        setContentView(planeView);

//        planeView.setBackgroundColor(Color.BLACK);
        Display display = getWindowManager().getDefaultDisplay();
        displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        speedX = planeView.currentX;
        speedY = planeView.currentY;
        planeView.currentX = planeView.currentX / 2;
        planeView.currentX = displayMetrics.widthPixels / 2 - planeView.currentX;
        planeView.currentY =  displayMetrics.heightPixels - planeView.currentY;
        planeView.invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if((event.getX()-speedX) <= 0)
            x = event.getX() ;
        else
            x = event.getX()-speedX;
        if((event.getY()- speedY) <= 0)
            y = event.getY() ;
        else
            y = event.getY() - speedY;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                planeView.currentX = x;
                planeView.currentY = y;
                Log.e("ACTION_DOWN",planeView.currentX+"    "+  planeView.currentY);
                break;
            case MotionEvent.ACTION_MOVE:
                planeView.currentX = x;
                planeView.currentY = y;
                if(planeView.currentY > displayMetrics.heightPixels)
                    planeView.currentY = displayMetrics.heightPixels - speedY;
                Log.e("ACTION_MOVE",planeView.currentX+ "    "+  planeView.currentY);
                break;
            case MotionEvent.ACTION_UP:
                planeView.currentX = x;
                planeView.currentY = y;
                if(planeView.currentY > displayMetrics.heightPixels)
                    planeView.currentY = displayMetrics.heightPixels - speedY;
                Log.e("ACTION_UP",planeView.currentX+"    "+  planeView.currentY);
                break;
        }
        planeView.invalidate();
        return true;
    }
}
