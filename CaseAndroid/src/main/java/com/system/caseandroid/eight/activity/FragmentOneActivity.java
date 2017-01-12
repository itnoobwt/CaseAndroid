package com.system.caseandroid.eight.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.system.caseandroid.R;
import com.system.caseandroid.eight.fragment.BlankFragment;
import com.system.caseandroid.eight.fragment.ListFragment;

public class FragmentOneActivity extends FragmentActivity implements ListFragment.OnSelectItem
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_one);
    }

    @Override
    public void OnItemLitener(int position)
    {
        BlankFragment blankFragment = new BlankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id",position);
        blankFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.left_fragment_content,blankFragment);
        transaction.commit();
    }

}
