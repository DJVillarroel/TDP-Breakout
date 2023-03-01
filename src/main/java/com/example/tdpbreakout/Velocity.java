package com.example.tdpbreakout;

/*
    @Class Velocity, genera un objeto de velocidad para la bola en sus respectivas coordenadas
 */
public class Velocity {
    private int x,y;

    public Velocity(int x, int y){
        this.x = x;
        this.y = y;
    }

    //Setea la velocidad en la coordenada X
    public void setX(int x){
        this.x = x;
    }

    //Setea la velocidad en la coordenada Y
    public void setY(int y){
        this.y = y;
    }

    //Retorna la velocidad en la coordenada X
    public int getX(){
        return x;
    }

    //Retorna la velocidad en la coordenada Y
    public int getY(){
        return y;
    }
}
