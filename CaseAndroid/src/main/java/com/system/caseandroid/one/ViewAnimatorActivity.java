package com.system.caseandroid.one;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ViewSwitcher;
import com.system.caseandroid.R;
import com.system.caseandroid.one.adapter.ViewAnimatorAdapter;

import java.util.ArrayList;

public class ViewAnimatorActivity extends AppCompatActivity
{

    public static final int NUMBER_PER_SCREEN = 12;
    public static class DataItem{
        public String dataName;
        public Drawable drawable;
    }
    private ArrayList<DataItem> items = new ArrayList<DataItem>();
    private int screenNo = 0;
    private int screenCount;
    private ViewSwitcher viewSwitcher;
    private LayoutInflater inflater;
    private ViewAnimatorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animator);
        inflater = LayoutInflater.from(this);
        for (int i = 0;i<40;i++){
            String label = ""+i;
            Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
            DataItem dataItem = new DataItem();
            dataItem.dataName = label;
            dataItem.drawable = drawable;
            items.add(dataItem);
        }
        adapter = new ViewAnimatorAdapter(ViewAnimatorActivity.this,screenNo,screenCount,items);
        screenCount = items.size() % NUMBER_PER_SCREEN == 0? items.size() / NUMBER_PER_SCREEN:items.size()
                /NUMBER_PER_SCREEN+1;
        viewSwitcher = (ViewSwitcher) findViewById(R.id.vs);
        viewSwitcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            @Override
            public View makeView()
            {
                return inflater.inflate(R.layout.slidelistview,null);
            }
        });
        next(null);
    }

    public void prev(View view){
        if(screenNo > 0){
            screenNo--;
            viewSwitcher.setInAnimation(this,R.anim.slide_in_right);
            viewSwitcher.setOutAnimation(this,R.anim.slide_out_left);
            ((GridView)viewSwitcher.getNextView()).setAdapter(adapter);
            try
            {
                viewSwitcher.showPrevious();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
    public void next(View view){
        if(screenNo < screenCount - 1){
            screenNo++;
            viewSwitcher.setInAnimation(this,R.anim.slide_in_right);
            viewSwitcher.setOutAnimation(this,R.anim.slide_out_left);
            ((GridView)viewSwitcher.getNextView()).setAdapter(adapter);
            try
            {
                viewSwitcher.showNext();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
