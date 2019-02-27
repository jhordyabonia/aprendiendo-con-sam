package com.jhordyabonia.aprendiendoconsam;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import static com.jhordyabonia.aprendiendoconsam.Item.LETTERS;

public class Main extends AppCompatActivity implements View.OnClickListener{

    // Recurso de la aplicación
    public static Item levels[][]= {
            {
                    new Item(LETTERS.uno),new Item(LETTERS.dos),new Item(LETTERS.tres),new Item(LETTERS.cuatro),
                    new Item(LETTERS.cinco),new Item(LETTERS.seis),new Item(LETTERS.siete),new Item(LETTERS.ocho),new Item(LETTERS.nueve)
            },//numeros naturales
            {
                    new Item(LETTERS.a), new Item(LETTERS.e),
                    new Item(LETTERS.i), new Item(LETTERS.o), new Item(LETTERS.u)
            },//vocales
            {
                    new Item(LETTERS.b), new Item(LETTERS.d), new Item(LETTERS.f), new Item(LETTERS.h),
                    new Item(LETTERS.k), new Item(LETTERS.l), new Item(LETTERS.r), new Item(LETTERS.t)
            },//hacia arriba
            {
                    new Item(LETTERS.g), new Item(LETTERS.j), new Item(LETTERS.q), new Item(LETTERS.y)
            },//hacia abajo
            {
                    new Item(LETTERS.m), new Item(LETTERS.n), new Item(LETTERS.s),
                    new Item(LETTERS.v), new Item(LETTERS.w), new Item(LETTERS.x), new Item(LETTERS.z)
            }//otras
        };
    public static int LEVEL=0;
    public static boolean all=false,paused=false,display_off=true;
    private MediaPlayer resourcePlayer,music;
    private ObjectAnimator player;
    private ImageView display;
    private Item letters[];
    private int step=0,counter=27;
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

        setContentView(R.layout.activity_main);

        display=findViewById(R.id.letter);
        if(!all)
            display.setOnClickListener(this);

        findViewById(R.id.play).setOnClickListener(this);

        resourcePlayer =
                MediaPlayer.create(this, R.raw.music);
        music =
                MediaPlayer.create(this, R.raw.music);
        music.setLooping(true);
        music.setVolume(0.3f,0.3f);
        letters=levels[LEVEL];

        player =  ObjectAnimator.ofFloat(display,"alpha",0f,0.35f,0.7f,1f,1f);
        player.setDuration(3500);
        player.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator anim){
                    if (!display_off) {
                        onClick(display);
                        counter -= 1;
                    }
                }
        });
    }
    @Override
    protected  void onDestroy()
    {
        player.end();
        music.stop();
        music.release();
        super.onDestroy();
    }
    @Override
    protected  void onPause()
    {
        music.pause();
        player.end();
        display_off=true;
        super.onPause();
    }
    @Override
    protected  void onResume()
    {
        super.onResume();
        display_off=false;
        music.start();
        player.setRepeatCount(counter);
        player.start();
    }
    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.play) {
            if(!all){
                startActivity(new Intent(this, Select.class));
                finish();
            }else if (paused) {
                paused=false;
                player.start();
                ((ImageView)findViewById(R.id.play))
                        .setImageResource(R.drawable.pause);
            }else {
                paused=true;
                player.end();
                ((ImageView)findViewById(R.id.play))
                        .setImageResource(R.drawable.play);
            }
        }else if (!paused) {
            if (step >= letters.length) {
                step = 0;
                if (all) {
                    if (++LEVEL >= levels.length)
                        LEVEL = 0;
                    letters = levels[LEVEL];
                } else return;
            }
            if (resourcePlayer.isPlaying())
                resourcePlayer.stop();
            resourcePlayer.release();

            resourcePlayer =
                    MediaPlayer.create(this, letters[step].audio);

            resourcePlayer.setVolume(0.9f, 0.9f);

            display.setImageResource(letters[step].image);

            resourcePlayer.start();
            ++step;
        }
    }
}
