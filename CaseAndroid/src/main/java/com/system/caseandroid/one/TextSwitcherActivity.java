package com.system.caseandroid.one;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.system.caseandroid.R;

public class TextSwitcherActivity extends AppCompatActivity
{

    private TextSwitcher switcher;
    private String[] imageIds = new String[]{"疯狂Java","疯狂Android","疯狂C++"};
    private Button btn;
    int index;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);
        switcher = (TextSwitcher) findViewById(R.id.tsc);
        btn = (Button) findViewById(R.id.btn_txt);
        switcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            @Override
            public View makeView()
            {
                TextView textView = new TextView(TextSwitcherActivity.this);
                return textView;
            }
        });

    }
    public void next(View view){
        switcher.setText(imageIds[index++%imageIds.length]);
    }
}
