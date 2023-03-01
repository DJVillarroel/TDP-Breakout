package com.example.tdpbreakout;

import android.graphics.Bitmap;

/*
    @Class Ball, entidad de la bola
 */
public class Ball extends Entity{

    public Ball(Bitmap bitmap){
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    //Retorna verdadero si la bola colisiona con la paleta
    public boolean collides(Paddle p){
        return ((x + width) >= p.getX())
                && (x <= (p.getX() + p.getWidth()))
                && ((y + height) >= p.getY())
                && ((y + height) < (p.getY() + p.getHeight()));
    }

    //Retorna verdadero si la bola colisiona con el ladrillo pasado por parametro
    public boolean collidesBrick(Brick b){
        return (x + sprite.getWidth() >= b.getX()*b.getWidth()
                && x <= b.getX()*b.getWidth()+b.getWidth()
                && y <= b.getY()*b.getHeight()+b.getHeight()
                && y >= b.getY()*b.getHeight());
    }

    //Retorna verdadero si la bola colisiona con el muro pasado por parametro (0 = izquierdo, scrwWidth = derecho)
    public boolean collidesWall(float rightWall){
        return (x >= rightWall-sprite.getWidth()) || (x<=0);
    }

    //Retorna verdadero si la bola colisiona con el techo
    public boolean collidesCeiling(){
        return y <= 0;
    }

    //Retorna verdadero si la pelota esquiva la paleta pasada por parametro
    public boolean missesPaddle(Paddle p){
        return y>p.getY()+p.getHeight();
    }
}
