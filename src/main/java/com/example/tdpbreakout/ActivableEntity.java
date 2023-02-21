package com.example.tdpbreakout;


//Las entidades activables difieren de las normales en el hecho que pueden ser mostradas o eliminadas en cualquier momento
public abstract class ActivableEntity extends Entity{
    protected boolean isVisible;

    public void setInvisible(){
        isVisible = false;
    }
    public void setVisible(){isVisible = true;}
    public boolean isVisible(){
        return isVisible;
    }
}
