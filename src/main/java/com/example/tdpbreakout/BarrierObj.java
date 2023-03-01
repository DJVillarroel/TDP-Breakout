package com.example.tdpbreakout;

import android.graphics.Bitmap;

/*
    @Class BarrierObj, representa el objeto de la barrera, no confundir con el objeto powerUp Barrier
 */
public class BarrierObj extends ActivableEntity{

    public BarrierObj(Bitmap bitmap){
        sprite = bitmap;
        isVisible=false;
    }
}
