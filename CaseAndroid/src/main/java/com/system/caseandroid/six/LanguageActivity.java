package com.system.caseandroid.six;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;

import java.util.Locale;

public class LanguageActivity extends BaseActivity
{
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        txt = (TextView) findViewById(R.id.language_txt);
        language();
    }

    public  void language(){
        Locale[] locales = Locale.getAvailableLocales();
        StringBuilder str = new StringBuilder();
        for ( int i = 0; i<locales.length; i++){
            str.append(locales[i].getDisplayCountry()+" = "+ locales[i].getCountry()+
            "  "+locales[i].getDisplayLanguage()+"  = "+locales[i].getLanguage()+"\n");
        }
        txt.setText(str.toString());
    }
}
