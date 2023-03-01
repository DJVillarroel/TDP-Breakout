package com.example.tdpbreakout;

import android.graphics.Bitmap;

/*
    @Class Entity, representa todas las entidades que interactuan en la lógica del juego
 */
public abstract class PowerUp extends ActivableEntity{

    //Retorna un string conteniendo el efecto que el powerUp produce
    public abstract String giveEffect();

    //Setea la velocidad de caida del powerUp
    public abstract void fallDown();

}
