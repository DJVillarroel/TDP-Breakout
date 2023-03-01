package com.example.tdpbreakout;

import android.graphics.Bitmap;

/*
    @Class Live, representa el powerUp vida
 */
public class Live extends PowerUp{

    private static final String effect = "1up";

    public Live(Bitmap bitmap){
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    //Retorna un string conteniendo el efecto que el powerUp produce
    public String giveEffect(){
        return effect;
    }

    //Setea la velocidad de caida del powerUp
    @Override
    public void fallDown() {
        y+=16;
    }
}
