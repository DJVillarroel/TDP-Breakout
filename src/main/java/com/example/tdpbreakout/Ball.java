package com.example.tdpbreakout;

import android.graphics.Bitmap;

public class Ball extends Entity{




    public Ball(Bitmap bitmap){
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public boolean collides(Paddle p){
        return ((x + width) >= p.getX())
                && (x <= (p.getX() + p.getWidth()))
                && ((y + height) >= p.getY())
                && ((y + height) < (p.getY() + p.getHeight()));
    }

    public boolean collidesBrick(Brick b){
        return (x + sprite.getWidth() >= b.getX()*b.getWidth()
                && x <= b.getX()*b.getWidth()+b.getWidth()
                && y <= b.getY()*b.getHeight()+b.getHeight()
                && y >= b.getY()*b.getHeight());
    }


    public boolean collidesWall(float rightWall){
        return (x >= rightWall-sprite.getWidth()) || (x<=0);
    }

    public boolean collidesCeiling(){
        return y <= 0;
    }

    public boolean missesPaddle(Paddle p){
        return y>p.getY()+p.getHeight();
    }
}
