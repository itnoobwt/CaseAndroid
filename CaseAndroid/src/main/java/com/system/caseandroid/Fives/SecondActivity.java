package com.system.caseandroid.Fives;

import android.app.Activity;
import android.content.ComponentName;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.system.caseandroid.R;

/**
 * 讲解Intent用法、action、data、typ等等......
 */
public class SecondActivity extends AppCompatActivity
{
    private Button btn;
    private final int PICK_CONTACT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //        ComponentName componentName = getIntent().getComponent();
        //        Toast.makeText(this, componentName.getPackageName()+"    "+componentName.getClassName(),
        //                Toast.LENGTH_SHORT).show();
        btn = (Button) findViewById(R.id.bn);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("vnd.android.cursor.item/phone");
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK)
                {
                    Uri contactData = data.getData();
                    CursorLoader cursorLoader = new CursorLoader(this, contactData, null, null, null, null);
                    Cursor cursor = cursorLoader.loadInBackground();
                    if (cursor.moveToFirst())
                    {
                        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        //获取联系人的名字
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNumber = "次联系人暂未输入电话号码";
                        try
                        {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            if (phones.moveToFirst())
                            {
                                phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            phones.close();
                        }catch (Exception e){
                            Log.e("error",e.getMessage());
                        }


                        EditText show = (EditText) findViewById(R.id.show);
                        //显示联系人的名称
                        show.setText(name);
                        EditText phone = (EditText) findViewById(R.id.phone);
                        phone.setText(phoneNumber);
                    }
                    cursor.close();
                }
                break;
        }
    }

    public void home(View v){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /**
     * 同一个Intent对象使用type、data第二者会覆盖第一者value值
     * @param v
     */
    public void dataOrtype(View v){
        Intent intent = new Intent();
        intent.setType("abc/xyz");
        intent.setData(Uri.parse("lee://www.fkjava.org:8888/test"));
        Toast.makeText(this, intent.toString(), Toast.LENGTH_SHORT).show();
    }
    public void dataOrtype1(View v){
        Intent intent = new Intent();
        intent.setData(Uri.parse("lee://www.fkjava.org:8888/test"));
        intent.setType("abc/xyz");
        Toast.makeText(this, intent.toString(), Toast.LENGTH_SHORT).show();
    }
    /**
     * 同一个Intent对象使用DataAndType并存value值
     *
     * @param v
     */
    public void dataAndtype(View v){
        Intent intent = new Intent();
        intent.setDataAndType(Uri.parse("lee://www.fkjava.org:8888/test"),"abc/xyz");
        Toast.makeText(this, intent.toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 使用Action、Data属性启动系统Activity
     * @param v
     */
    public void actionAndData(View v){
        Intent intent = new Intent();
        String data = "http://www.crazyit.org";
        //根据指定字符串解析Uri对象
        Uri uri = Uri.parse(data);
        //为Intent设置Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //设置Data属性
        intent.setData(uri);
        startActivity(intent);
    }
    public void edit(View v){
        try
        {
            Intent intent = new Intent();
            String data = "content://com.android.contacts/contacts/1";
            //根据指定字符串解析Uri对象
            Uri uri = Uri.parse(data);
            //为Intent设置Action属性（动作为：编辑）
            intent.setAction(Intent.ACTION_EDIT);
            //设置Data属性
            intent.setData(uri);
            startActivity(intent);
        }catch (Exception e){
            Log.e("error",e.getMessage());
        }

    }
    public void call(View v){
        try
        {
            Intent intent = new Intent();
            String data = "tel:136888888";
            //根据指定字符串解析Uri对象
            Uri uri = Uri.parse(data);
            //为Intent设置Action属性（动作为：拨号）
            intent.setAction(Intent.ACTION_CALL);
            //设置Data属性
            intent.setData(uri);
            startActivity(intent);
        }catch (Exception e){
            Log.e("error",e.getMessage());
        }

    }
}