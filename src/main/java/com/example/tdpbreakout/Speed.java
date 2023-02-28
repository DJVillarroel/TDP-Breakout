package com.example.tdpbreakout;

import android.graphics.Bitmap;

public class Speed extends PowerUp{
    private static final String effect = "speed";

    public Speed(Bitmap bitmap){
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
        y+=8;
    }
}
