package com.example.tdpbreakout;

import android.graphics.Bitmap;

//@Class Paddle paleta o jugador, el objeto a controlar por parte del usuario
public class Paddle extends Entity{

    public Paddle(Bitmap bitmap){
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    //Retorna verdadero si la paleta colisiona con un powerUp
    public boolean collides(PowerUp p){
        return ((p.getX() + p.getWidth()) >= x)
                && (p.getX() <= (x + width))
                && ((p.getY() + p.getHeight()) >= y)
                && ((p.getY() + p.getHeight()) < (y + height));
    }
}
