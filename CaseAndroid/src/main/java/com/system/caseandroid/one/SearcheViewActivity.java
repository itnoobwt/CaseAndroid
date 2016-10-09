package com.system.caseandroid.one;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.system.caseandroid.R;

public class SearcheViewActivity extends AppCompatActivity
{
    private ListView lv;
    private SearchView searchView;
    private String[] data = new String[]{"aaaaaaaaa","bbbbbbbbbbbb","ccccccccc"};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searche_view);
        lv = (ListView) findViewById(R.id.lv);
        searchView = (SearchView) findViewById(R.id.sv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);
        lv.setTextFilterEnabled(true);
        searchView.setIconifiedByDefault(false);
//        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("请输入关键字");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                Toast.makeText(SearcheViewActivity.this, s, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {

                if(TextUtils.isEmpty(s))
                    lv.clearTextFilter();
                else
                    lv.setFilterText(s);
                return true;
            }
        });
    }
}
