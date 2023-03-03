package com.example.analog_clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

public abstract class AbstractClockHand {
    public Drawable imageClockHand;
    protected Matrix matrix = new Matrix();
    public AbstractClockHand(Resources res, int imageClockHand){
        this.imageClockHand = ResourcesCompat.getDrawable(res, imageClockHand, null);
    }

    public void changeSize(int size){

    }
    public abstract void moveHand(int units, int preUnits);
}
