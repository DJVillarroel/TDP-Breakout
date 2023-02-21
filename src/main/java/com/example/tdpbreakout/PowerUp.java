package com.example.tdpbreakout;

import android.graphics.Bitmap;

public abstract class PowerUp extends ActivableEntity{

    protected int timer;

    public abstract void activateEffect();

}
