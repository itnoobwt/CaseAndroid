package com.system.caseandroid.six;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 使用原始XML文件
 */
public class OriginalActivity extends BaseActivity
{

    private EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
        ed = (EditText) findViewById(R.id.xml_ed);
    }

    public void getXmlValue(View v){
        //根据XML资源的ID获取解析该资源的解析器
        XmlResourceParser xrp = getResources().getXml(R.xml.books);
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            while (xrp.getEventType() != XmlPullParser.END_DOCUMENT){
                if(xrp.getEventType() == XmlPullParser.START_TAG){
                    String tagName = xrp.getName();
                    if(tagName.equals("book")){
                        //根据属性名来获取属性值
                        String bookName = xrp.getAttributeValue(null,"price");
                        stringBuilder.append("价格：");
                        stringBuilder.append(bookName);
                        //根据属性索引来获取属性值
                        String bookPrice = xrp.getAttributeValue(1);
                        stringBuilder.append("   出版日期："+bookPrice);
                        stringBuilder.append(xrp.nextText());
                    }
                    stringBuilder.append("\n");
                }
                //获取解析器的下一个事件
                xrp.next();
            }
            ed.setText(stringBuilder.toString());
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
