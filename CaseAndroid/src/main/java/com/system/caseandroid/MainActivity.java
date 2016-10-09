package com.system.caseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import com.system.caseandroid.adapter.DividerGridItemDecoration;
import com.system.caseandroid.adapter.MainAdapter;
import com.system.caseandroid.four.FourMainActivity;
import com.system.caseandroid.one.OneMainActivity;
import com.system.caseandroid.one.TableActivity;
import com.system.caseandroid.three.ThreeMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
{
    @BindView(R.id.rv)
    RecyclerView rv;
    public List<Integer> list = new ArrayList<Integer>();
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
        adapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                switch (position){
                    case 0:
                        startActivity(new Intent(MainActivity.this, OneMainActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, ThreeMainActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, FourMainActivity.class));
                        break;
                }
//                if(list.get(position) == 1){
//                    startActivity(new Intent(MainActivity.this, OneMainActivity.class));
//                }
//                Toast.makeText(MainActivity.this, list.get(position)+"单击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position)
            {
                Toast.makeText(MainActivity.this, list.get(position)+"长按", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initData()
    {
        for (int i = 1;i<9;i++){
            list.add(i);
        }
    }

}
