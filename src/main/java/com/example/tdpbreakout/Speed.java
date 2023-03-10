package com.example.tdpbreakout;

import android.graphics.Bitmap;

/*
    @Class Speed, representa el powerUp de velocidad aumentada
 */
public class Speed extends PowerUp{
    private static final String effect = "speed";

    public Speed(Bitmap bitmap){
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
        y+=8;
    }
}
