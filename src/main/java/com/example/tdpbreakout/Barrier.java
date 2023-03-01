package com.example.tdpbreakout;

import android.graphics.Bitmap;

/*
    @Class Barrier, representa el powerUp barrera
 */
public class Barrier extends PowerUp{
    private static final String effect = "barrier";

    public Barrier(Bitmap bitmap){
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
        y+=12;
    }
}
