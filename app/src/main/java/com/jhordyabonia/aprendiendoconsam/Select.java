package com.jhordyabonia.aprendiendoconsam;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class Select extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener{

    private ArrayList<Item> items= new ArrayList();
    private ArrayList<ImageView> displays= new ArrayList();
    private ImageView display;
    private int total_ok=getTotal_ok();
    private int id_selected=0,repeat=0;
    private int n_intent=getN_intent();
    private MediaPlayer resourcePlayer=null,error=null,win=null,ok=null,music=null,fail=null,big_win=null;
    private int win_audio_levels[]={R.raw.ok_nn,R.raw.ok_ae,R.raw.ok_abc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().setFlags(
                WindowManager.LayoutParams.TYPE_APPLICATION_MEDIA,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_select);

        error=MediaPlayer.create(this, R.raw.toc);
        error.setVolume(1.1f,1.1f);
//        error.setOnCompletionListener(this);

        music=MediaPlayer.create(this, R.raw.music);
        music.setVolume(0.3f,0.3f);
        //music.setOnCompletionListener(this);

        win=MediaPlayer.create(this, R.raw.sparkle);
        win.setVolume(1f,1f);
        win.setOnCompletionListener(this);

        ok=MediaPlayer.create(this, R.raw.ok_again);
        ok.setVolume(1f,1f);
        ok.setOnCompletionListener(this);

        fail=MediaPlayer.create(this, R.raw.fail_again);
        fail.setVolume(1f,1f);
        fail.setOnCompletionListener(this);

        int tmp_level=Main.LEVEL>=win_audio_levels.length?2:Main.LEVEL;
        big_win=MediaPlayer.create(this, win_audio_levels[tmp_level]);
        big_win.setVolume(1f,1f);
        big_win.setOnCompletionListener(this);

        display=findViewById(R.id.letter);
        display.setOnClickListener(this);

        displays.add((ImageView)findViewById(R.id.letter1));
        displays.add((ImageView)findViewById(R.id.letter2));
        displays.add((ImageView)findViewById(R.id.letter3));
        displays.add((ImageView)findViewById(R.id.letter4));
        for(ImageView i:displays)
            i.setOnClickListener(this);

        start();
    }
    private int getN_intent(){
        return (4-Main.LEVEL);
    }
    private void start(){

        Random r= new Random();
        int t=Main.levels[Main.LEVEL].length-1;

        items.clear();
        Item last=Main.levels[Main.LEVEL][r.nextInt(t)];
        while (items.size()<displays.size()) {
            if(!items.contains(last))
                items.add(last);
            last = Main.levels[Main.LEVEL][r.nextInt(t)];
        }

        for(int i=0;i<items.size();i++)
            displays.get(i).setImageResource(items.get(i).image);

        id_selected=r.nextInt(items.size());
        last=items.get(id_selected);
        display.setImageResource(R.drawable.question);

        if(resourcePlayer!=null){
            if(resourcePlayer.isPlaying()){
                resourcePlayer.stop();
            }
            resourcePlayer.release();
        }

        resourcePlayer = MediaPlayer.create(this, last.audio);
        resourcePlayer.setVolume(0.9f,0.9f);
        resourcePlayer.setOnCompletionListener(this);
        resourcePlayer.start();
        repeat=2;
    }
    @Override
    public  void  onResume(){
        super.onResume();
        music.start();
    }
    @Override
    public  void onPause(){

        if (resourcePlayer != null) {
            if (resourcePlayer.isPlaying()) {
                resourcePlayer.stop();
            }
        }
        music.pause();
        super.onPause();
    }
    @Override
    public  void onDestroy(){
        music.stop();

        if (resourcePlayer != null) {
            if (resourcePlayer.isPlaying()) {
                resourcePlayer.stop();
            }
            resourcePlayer.release();
        }
        if(error!=null)
            error.release();
        win.release();
        music.release();
        super.onDestroy();
    }
    @Override
    public void onClick(View view) {
        if(!big_win.isPlaying()) {
            if (view.getId() == R.id.letter) {
                if (!resourcePlayer.isPlaying())
                    resourcePlayer.start();
            } else if (view.getId() == displays.get(id_selected).getId()) {
                if (win.isPlaying())
                    win.stop();
                if (resourcePlayer.isPlaying())
                    resourcePlayer.stop();
                win.start();
                if (--total_ok < 1) {
                    big_win.start();
                } else {
                    ok.start();
                }
            } else {
                if (--n_intent < 1) {
                    if (resourcePlayer.isPlaying())
                        resourcePlayer.stop();
                    n_intent = getN_intent();
                    fail.start();
                } else if (error != null) error.start();
            }
        }
    }

    public int getTotal_ok() {
        return Main.levels[Main.LEVEL].length;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(mediaPlayer==resourcePlayer) {
            if (0 < repeat--) {
                if (resourcePlayer != null) {
                    resourcePlayer.start();
                }
            }
        }else if(mediaPlayer==fail||mediaPlayer==ok){
            start();
        }
    }
}
