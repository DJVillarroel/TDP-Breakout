package com.example.tdpbreakout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/*
    @Class MainActivity, actividad principal del juego
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void StartGame(View view) {
        GameView GUI = new GameView(this);
        GUI.setBackground(getResources().getDrawable(R.drawable.background));
        setContentView(GUI);
    }
}