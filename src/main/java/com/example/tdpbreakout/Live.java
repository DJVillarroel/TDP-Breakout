package com.example.tdpbreakout;

import android.graphics.Bitmap;

public class Live extends PowerUp{

    public Live(Bitmap bitmap){
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
        timer = 0;
    }

    public void activateEffect(){
    }
}
