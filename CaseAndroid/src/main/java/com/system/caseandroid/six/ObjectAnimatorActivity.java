package com.system.caseandroid.six;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.system.caseandroid.R;

public class ObjectAnimatorActivity extends AppCompatActivity
{
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        linearLayout = (LinearLayout) findViewById(R.id.container);
//        linearLayout.addView(new MyAnimationView(this));
        ObjectAnimator colorAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.color_anim);
        colorAnim.setTarget(linearLayout);
        colorAnim.start();
    }

    public class  MyAnimationView extends View{
        public MyAnimationView(Context context)
        {
            super(context);
            ObjectAnimator colorAnim = (ObjectAnimator) AnimatorInflater.loadAnimator(context,R.animator.color_anim);
            colorAnim.setTarget(this);
            colorAnim.start();
        }
    }
}
