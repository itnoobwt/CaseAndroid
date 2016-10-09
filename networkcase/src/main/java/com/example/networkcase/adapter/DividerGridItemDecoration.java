package com.example.networkcase.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by user on 2016/7/26.
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration
{
    public static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int mOrientation;
    public DividerGridItemDecoration(Context context,int mOrientation)
    {
        TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        this.mOrientation = mOrientation;
        setOrientation(mOrientation);
    }
    public void setOrientation( int orientation) {
     if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
             throw new IllegalArgumentException( "invalid orientation");
         }
         mOrientation = orientation;
     }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
    {
        super.onDraw(c, parent, state);
        if(mOrientation == VERTICAL_LIST){
            drawVertical(c,parent);
        }

    }

    public void drawVertical(Canvas canvas,RecyclerView recyclerView){
        int letf = recyclerView.getPaddingLeft();
        int right = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0 ;i < childCount; i++){
            View child  = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)child.getLayoutParams();
            int top = child.getBottom()+layoutParams.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(letf,top,right,bottom);
            mDivider.draw(canvas);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
