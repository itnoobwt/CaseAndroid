package com.system.caseandroid.seven;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;

import java.io.IOException;
import java.io.InputStream;

public class BitmapActivity extends BaseActivity
{
    String[] images = null;
    AssetManager assets = null;
    int currentImg = 0;
    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        imageview = (ImageView)findViewById(R.id.image_view);

        assets = getAssets();
        try
        {
            //在assets/img文件下找图片
            images = assets.list("img");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void btnBitmap(View view){
        if(currentImg >= images.length){
            currentImg = 0;
        }
        while (!images[currentImg].endsWith(".png")&&!images[currentImg].endsWith(".jpg")&&!images[currentImg]
                .endsWith(".gif")){
            currentImg++;
            if(currentImg >= images.length){
                currentImg = 0;
            }
        }
        InputStream assetFile = null;
        try
        {
            assetFile = assets.open("img/"+images[currentImg++]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageview.getDrawable();
        // 如果图片还未回收，先强制回收该图片
        if(bitmapDrawable!= null && !bitmapDrawable.getBitmap().isRecycled()){
            bitmapDrawable.getBitmap().recycle();
        }
        // 改变ImageView显示的图片
        imageview.setImageBitmap(BitmapFactory.decodeStream(assetFile));
    }
}
