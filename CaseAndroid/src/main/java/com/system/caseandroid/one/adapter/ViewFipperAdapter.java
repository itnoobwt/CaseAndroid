package com.system.caseandroid.one.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.system.caseandroid.R;

/**
 * Created by user on 2016/9/23.
 */

public class ViewFipperAdapter extends BaseAdapter
{
    private Context context;
    private int[] imageIds = new int[]{R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e};
    public ViewFipperAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount()
    {
        return imageIds.length;
    }

    @Override
    public Object getItem(int i)
    {
        return imageIds[i];
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageIds[i]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }
}
