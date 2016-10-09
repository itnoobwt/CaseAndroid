package com.example.cameraapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.cameraapp.R;
import com.example.cameraapp.logutlis.LogUtlis;
import com.example.cameraapp.modle.Student;

import java.util.List;

/**
 * Created by user on 2016/8/3.
 */
public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MyViewHolder>
{
    private List<Student> datalist;
    private Context context;
    public RetrofitAdapter(Context context, List<Student> datalist){
        LogUtlis.e("onCreateViewHolder","初始化");
        this.datalist = datalist;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LogUtlis.e("onCreateViewHolder","加载布局");
        View  view = LayoutInflater.from(context).inflate(R.layout.item_stu,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        LogUtlis.e("onCreateViewHolder","onBindViewHolder");
        Student student = datalist.get(position);
        holder.tv_name.setText(student.name);
        holder.tv_age.setText(student.age+"");
        holder.tv_type.setText(student.type);
    }

    @Override
    public int getItemCount()
    {
        return datalist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        TextView tv_age;
        TextView tv_type;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_age = (TextView) itemView.findViewById(R.id.tv_age);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }

}
