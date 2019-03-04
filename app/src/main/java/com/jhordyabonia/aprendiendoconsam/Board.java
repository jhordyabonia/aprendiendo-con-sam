package com.jhordyabonia.aprendiendoconsam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class Board extends AppCompatActivity implements View.OnClickListener{

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
        findViewById(R.id.level0).setOnClickListener(this);
        findViewById(R.id.level1).setOnClickListener(this);
        findViewById(R.id.level2).setOnClickListener(this);
        findViewById(R.id.level3).setOnClickListener(this);
        findViewById(R.id.level4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this, Main.class);
        Main.all=false;
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
        }
        startActivity(intent);
       // Toast.makeText(this,Main.LEVEL)
    }
}
