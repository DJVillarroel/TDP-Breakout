package com.example.tdpbreakout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
    @Class GameOver, genera una escena de juego terminado
 */
public class GameOver extends AppCompatActivity {

    TextView totalPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        totalPoints = findViewById(R.id.totalPoints);
        int points = getIntent().getExtras().getInt("points");
        totalPoints.setText(""+ points);
    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        finish();
        System.exit(0);
    }
}
