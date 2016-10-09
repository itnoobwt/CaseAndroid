package com.system.caseandroid.one.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.system.caseandroid.R;

/**
 * Created by user on 2016/9/23.
 */

public class StackViewAdapter extends BaseAdapter
{
    private Context context;
    private int[] imageIds = new int[]{R.mipmap.a,R.mipmap.a,R.mipmap.a,R.mipmap.a,R.mipmap.a};
    public StackViewAdapter(Context context){
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
        ViewHolder holder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.call,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.imageView.setImageResource(imageIds[i]);

        return view;
    }
    class ViewHolder{
        ImageView imageView;
    }
}
