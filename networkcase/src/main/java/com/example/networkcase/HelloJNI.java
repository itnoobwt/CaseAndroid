package com.example.networkcase;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelloJNI extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);
        String jniStr = printJNI("I am HelloWorld Activity");
    }

    private native String printJNI(String inputStr);
    static {
        System.loadLibrary("HelloWorldJni");
    }


}
