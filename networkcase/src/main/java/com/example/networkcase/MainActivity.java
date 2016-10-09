package com.example.networkcase;

import android.os.Bundle;
import android.support.v7.widget.*;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import com.example.networkcase.activity.BaseActivity;
import com.example.networkcase.adapter.DividerGridItemDecoration;
import com.example.networkcase.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
{

    @BindView(R.id.rv)
    RecyclerView rv;
    public List<String> list = new ArrayList<String>();
    public MainAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        adapter = new MainAdapter(MainActivity.this,list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerGridItemDecoration(this,OrientationHelper.VERTICAL));
        rv.setAdapter(adapter);

    }

    private void initData()
    {
        for (int i = 'A';i<'Z';i++){
            list.add(""+(char)i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()){
            case R.id.action_settings:
                break;
            case R.id.action_settings1:
                list.add("Hello RecyclerView");
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
