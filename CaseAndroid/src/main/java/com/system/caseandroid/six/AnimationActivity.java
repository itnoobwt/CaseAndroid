package com.system.caseandroid.six;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import com.system.caseandroid.R;

/**
 * 动画
 * AnimationDrawable 动画
 */
public class AnimationActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }
    public void startAnim(View v){
        v.startAnimation(AnimationUtils.loadAnimation(this,R.anim.my_anim));
    }
}
