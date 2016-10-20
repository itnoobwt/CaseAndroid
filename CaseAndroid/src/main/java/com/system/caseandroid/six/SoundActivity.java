package com.system.caseandroid.six;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.system.caseandroid.BaseActivity;
import com.system.caseandroid.R;

import java.io.IOException;

/**
 * 播放声音   (assets 和 raw文件下的声音)
 */
public class SoundActivity extends BaseActivity
{
    MediaPlayer player;
    MediaPlayer player1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);


        Button playAsset = (Button) findViewById(R.id.playAsset);
        //根据声音文件的ID来创建MediaPlay
        player = MediaPlayer.create(this,R.raw.bomb);
        //获取该应用的AssetManger
        AssetManager assets = getAssets();
        try
        {
            AssetFileDescriptor afd = assets.openFd("shot.mp3");
            player1 = new MediaPlayer();
            //使用MediaPlayer加载指定的声音文件
            player1.setDataSource(afd.getFileDescriptor());
            player1.prepare();
        }catch (IOException ex){
            ex.printStackTrace();
        }

        playAsset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                player1.start();
            }
        });


        Button playRaw = (Button) findViewById(R.id.playRaw);
        playRaw.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                player.start();
            }
        });


    }
}
