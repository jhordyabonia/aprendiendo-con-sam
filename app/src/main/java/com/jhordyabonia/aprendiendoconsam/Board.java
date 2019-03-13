package com.jhordyabonia.aprendiendoconsam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class Board extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().setFlags(
                WindowManager.LayoutParams.TYPE_APPLICATION_MEDIA,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_board);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.audio).setOnClickListener(this);
        findViewById(R.id.level0).setOnClickListener(this);
        findViewById(R.id.level1).setOnClickListener(this);
        findViewById(R.id.level2).setOnClickListener(this);
        findViewById(R.id.level3).setOnClickListener(this);
        findViewById(R.id.level4).setOnClickListener(this);

        music = MediaPlayer.create(this, R.raw.intro);
        music.setLooping(true);
        music.setVolume(0.3f,0.3f);

        SharedPreferences t = getSharedPreferences("settings", 0);
        t.getBoolean("audio",Main.audio);

        ImageView image = findViewById(R.id.audio);
        if (Main.audio) {
            image.setImageResource(R.drawable.volume);
        } else {
            image.setImageResource(R.drawable.no_volume);
        }
    }

    @Override
    protected  void onDestroy()
    {
        music.stop();
        music.release();
        SharedPreferences t = getSharedPreferences("settings", 0);
        SharedPreferences.Editor edit = t.edit();
        edit.putBoolean("audio",Main.audio);
        edit.commit();
        super.onDestroy();
    }
    @Override
    protected  void onPause()
    {
        if(Main.audio)
            music.pause();
        super.onPause();
    }
    @Override
    protected  void onResume()
    {
        super.onResume();

        ImageView image = findViewById(R.id.audio);
        if (Main.audio) {
            music.start();
            image.setImageResource(R.drawable.no_volume);
        } else {
            image.setImageResource(R.drawable.volume);
        }
    }
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this, Main.class);
        Main.all=false;
        boolean start_activity = true;
        switch (view.getId()){
            case R.id.play:
                Main.all=true;
                intent=new Intent(this, Main.class);
                break;
            case R.id.level0:
                Main.LEVEL=0;
                intent=new Intent(this, Main.class);
                break;
            case R.id.level1:
                Main.LEVEL=1;
                intent=new Intent(this, Main.class);
                break;
            case R.id.level2:
                Main.LEVEL=2;
                intent=new Intent(this, Main.class);
                break;
            case R.id.level3:
                Main.LEVEL=3;
                intent=new Intent(this, Main.class);
                break;
            case R.id.level4:
                Main.LEVEL=4;
                intent=new Intent(this, Main.class);
                break;
            case R.id.audio:
                start_activity = false;
                ImageView image=findViewById(R.id.audio);
                if(music.isPlaying()) {
                    Main.audio=false;
                    music.pause();
                    image.setImageResource(R.drawable.volume);
                }else{
                    Main.audio=true;
                    music.start();
                    image.setImageResource(R.drawable.no_volume);
                }
                break;
        }
        if(start_activity)
            startActivity(intent);
    }
}
