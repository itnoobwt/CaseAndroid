package com.system.caseandroid.four;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.system.caseandroid.R;
import com.system.caseandroid.three.model.BookContent;


public class BookDetailFragment extends Fragment
{
    public static final String ITEM_ID = "item_id";
    private BookContent.Book book;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments().containsKey(ITEM_ID)){
            book = BookContent.ITEMS.get(getArguments().getInt(ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        if(book!= null){
            ((TextView)view.findViewById(R.id.book_title)).setText(book.title);
            ((TextView)view.findViewById(R.id.book_desc)).setText(book.desc);
        }
        return view;
    }

}
