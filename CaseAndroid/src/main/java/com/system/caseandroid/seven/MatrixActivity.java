package com.system.caseandroid.seven;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import com.system.caseandroid.seven.widget.MyMatrix;

public class MatrixActivity extends BaseActivity
{
    private MyMatrix my_matrix;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        my_matrix = (MyMatrix) findViewById(R.id.my_matrix);
    }

    public void open(View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        boolean isOpen=imm.isActive();
        Log.e("isOpen",isOpen+"");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode){
            case KeyEvent.KEYCODE_4:
                my_matrix.isScale = false;
                my_matrix.sx += 0.1;
                my_matrix.postInvalidate();
                break;
            case KeyEvent.KEYCODE_6:
                my_matrix.isScale = false;
                my_matrix.sx -= 0.1;
                my_matrix.postInvalidate();
                break;
            case KeyEvent.KEYCODE_8:
                my_matrix.isScale = true;
                if(my_matrix.scale < 2 )
                    my_matrix.scale += 0.1;
                my_matrix.postInvalidate();
                break;
            case KeyEvent.KEYCODE_2:
                my_matrix.isScale = true;
                if(my_matrix.scale > 0.5 )
                    my_matrix.scale -= 0.1;
                my_matrix.postInvalidate();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
