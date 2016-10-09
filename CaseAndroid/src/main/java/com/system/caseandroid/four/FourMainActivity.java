package com.system.caseandroid.four;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.system.caseandroid.R;

/**
 * 疯狂Android讲义第三版第四章
 */
public class FourMainActivity extends AppCompatActivity
    implements BookListFragment.Callbacks
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_main);
    }


    @Override
    public void onItemSelected(Integer id)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(BookDetailFragment.ITEM_ID,id);
        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        bookDetailFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.book_detail_container,bookDetailFragment).commit();
    }
}
