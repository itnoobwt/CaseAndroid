package com.example.networkcase.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.networkcase.R;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.HolderView>
{

    private List<String> list;
    private Context context;
    public MainAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.itme,parent,false);
        HolderView holderView = new HolderView(view);

        return holderView;
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position)
    {
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    class HolderView extends RecyclerView.ViewHolder{
        TextView tv;
        public HolderView(View itemView)
        {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }

    }
}
