package com.example.tdpbreakout;

import android.graphics.Bitmap;

//@Class Paddle paleta o jugador, el objeto a controlar por parte del usuario
public class Paddle extends Entity{


    //El objeto recibe un objeto bitmap como imagen a mostrar para representar graficamente a este
    public Paddle(Bitmap bitmap){
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

}
