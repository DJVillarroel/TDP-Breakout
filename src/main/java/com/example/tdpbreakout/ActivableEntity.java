package com.example.tdpbreakout;

/*
    @Class ActivableEntity, Las entidades activables difieren de las normales en el hecho que pueden
    ser mostradas u ocultadas en cualquier momento
 */
public abstract class ActivableEntity extends Entity{
    protected boolean isVisible;

    //Invisibiliza la entidad
    public void setInvisible(){
        isVisible = false;
    }

    //Visibiliza la entidad
    public void setVisible(){isVisible = true;}

    //Retorna si la entidad es visible o no
    public boolean isVisible(){
        return isVisible;
    }
}
