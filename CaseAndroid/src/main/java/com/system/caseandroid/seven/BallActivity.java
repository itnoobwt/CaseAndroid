package com.system.caseandroid.seven;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import com.system.caseandroid.seven.widget.GameView;

/**
 * 弹球小游戏
 */
public class BallActivity extends BaseActivity
{
    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Display display = getWindowManager().getDefaultDisplay();
        gameView = new GameView(this,display);
        setContentView(gameView);
        gameView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                switch (event.getKeyCode()){
                    case KeyEvent.KEYCODE_A:
                        if(gameView.racketX > 0)
                            gameView.racketX -=10;
                        break;
                    case KeyEvent.KEYCODE_D:
                        if(gameView.racketX < gameView.displayMetrics.widthPixels - gameView.RACKET_WIDTH)
                            gameView.racketX += 10;
                        break;
                }
                gameView.invalidate();
                return true;
            }
        });

        gameView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(gameView.racketX > 0)
                            gameView.racketX -=10;
                        else
                            gameView.racketX +=10;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(gameView.racketX > 0)
                            gameView.racketX -=10;
                        else
                            gameView.racketX +=10;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(gameView.racketX > 0)
                            gameView.racketX -=10;
                        else
                            gameView.racketX +=10;
                        break;
                }
                gameView.invalidate();
                return true;
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x123)
                gameView.invalidate();
        }
    };

}
