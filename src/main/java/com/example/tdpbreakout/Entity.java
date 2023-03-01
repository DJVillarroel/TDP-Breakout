package com.example.tdpbreakout;

import android.graphics.Bitmap;

/*
    @Class Entity, representa todas las entidades que interactuan en la lógica del juego
 */
public abstract class Entity {

    protected Bitmap sprite;
    protected float x, y;
    protected int width, height;

    //Setea la coordenada X de la entidad
    public void setX(float x){
        this.x = x;
    }

    //Setea la coordenada Y de la entidad
    public void setY(float y){
        this.y = y;
    }

    //Retorna la coordenada X de la entidad
    public float getX(){
        return x;
    }

    //Retorna la coordenada Y de la entidad
    public float getY(){
        return y;
    }

    //Retorna el ancho de la entidad
    public int getWidth(){
        return width;
    }

    //Retorna la altura de la entidad
    public int getHeight(){
        return height;
    }

    //Retorna el sprite de la entidad
    public Bitmap getSprite() {
        return sprite;
    }

}
