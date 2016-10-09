package com.system.caseandroid.one.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.system.caseandroid.R;
import com.system.caseandroid.one.ViewAnimatorActivity;

import java.util.ArrayList;

/**
 * Created by user on 2016/9/23.
 */

public class ViewAnimatorAdapter extends BaseAdapter
{
    private Context context;
    private int screenNo;
    private int screenCount;
    private ArrayList<ViewAnimatorActivity.DataItem> items;
    public ViewAnimatorAdapter(Context context, int screenNo, int screenCount,
                               ArrayList<ViewAnimatorActivity.DataItem> items){
        this.context = context;
        this.screenNo = screenNo;
        this.screenCount = screenCount;
        this.items = items;
    }
    @Override
    public int getCount()
    {
        if(screenNo == screenCount - 1 && items.size()%12!=0){
            return  items.size()%12;
        }
        return 12;
    }

    @Override
    public ViewAnimatorActivity.DataItem getItem(int i)
    {
        return items.get(screenNo * 12 + i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        if(view == null )
            view = LayoutInflater.from(context).inflate(R.layout.labelicon,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView textView = (TextView) view.findViewById(R.id.textview);
        imageView.setImageDrawable(getItem(i).drawable);
        textView.setText(getItem(i).dataName);
        return view;
    }
}
