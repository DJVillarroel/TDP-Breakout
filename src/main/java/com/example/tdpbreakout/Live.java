package com.example.tdpbreakout;

import android.graphics.Bitmap;

public class Live extends PowerUp{

    private static final String effect = "1up";

    public Live(Bitmap bitmap){
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
        timer = 0;
    }

    public String giveEffect(){
        return effect;
    }

    @Override
    public void fallDown() {
        y+=12;
    }
}
