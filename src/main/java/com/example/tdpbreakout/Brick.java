package com.example.tdpbreakout;

import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

/*
    @Class Brick, los ladrillos/bloques a romper, en esta clase se define su comportamiento
 */
public class Brick extends ActivableEntity{


    public Brick(Bitmap bitmap){
        isVisible = true;
        sprite = bitmap;
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

}
