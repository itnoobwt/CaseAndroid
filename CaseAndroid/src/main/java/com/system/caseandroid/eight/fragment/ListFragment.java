package com.system.caseandroid.eight.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.system.caseandroid.R;
import com.system.caseandroid.adapter.DividerGridItemDecoration;
import com.system.caseandroid.eight.adapter.ListFragmentAdapter;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements ListFragmentAdapter.OnItemClickLitener
{
    private List<String> list;
    private ListFragmentAdapter adapter;
    private OnSelectItem onSelectItem;


    public interface OnSelectItem{
        void OnItemLitener(int position);
    }

    public void setOnSelectItem(OnSelectItem onSelectItem){
        this.onSelectItem = onSelectItem;
    }



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        onSelectItem = (OnSelectItem)context;
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_item_list2, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.list);
//        if(view instanceof RecyclerView){
            adapter = new ListFragmentAdapter(getActivity(),list);
            adapter.setOnItemClickLitener(this);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(manager);
            rv.addItemDecoration(new DividerGridItemDecoration(getActivity(), OrientationHelper.VERTICAL));
            rv.setAdapter(adapter);
//        }
        return view;
    }

    private void initData()
    {
        list = new ArrayList<String>();
        for (int i = 1;i<9;i++){
            list.add("案例"+i);
        }
    }

    @Override
    public void onItemClick(View view, int position)
    {
        String name = list.get(position);
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
        onSelectItem.OnItemLitener(position);
    }

    @Override
    public void onItemLongClick(View view, int position)
    {
        String name = list.get(position);
        Toast.makeText(getActivity(), "长按事件："+name, Toast.LENGTH_SHORT).show();
    }
}
