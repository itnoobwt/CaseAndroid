package com.system.caseandroid.four;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.system.caseandroid.three.model.BookContent;

public class BookListFragment extends ListFragment
{
    private Callbacks callbacks;
    public interface Callbacks{
        public void onItemSelected(Integer id);
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<BookContent.Book>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,BookContent.ITEMS));
    }

    /**
     * API23才调用此方法
     * @param context
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        callbacks = (Callbacks) context;

    }
    /**
     * API23一下才调用此方法
     * @param context
     */
    @Override
    public void onAttach(Activity context)
    {
        super.onAttach(context);
        callbacks = (Callbacks) context;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        callbacks.onItemSelected(position);
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }


}
