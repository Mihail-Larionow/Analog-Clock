package com.example.analog_clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

public abstract class AbstractClockHand {
    public Drawable imageClockHand;
    protected int MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT;
    protected int centerX, centerY;
    protected float handScale;
    public AbstractClockHand(Resources res, int imageClockHand){
        this.imageClockHand = ResourcesCompat.getDrawable(res, imageClockHand, null);
        MAX_IMAGE_WIDTH = this.imageClockHand.getIntrinsicWidth();
        MAX_IMAGE_HEIGHT = this.imageClockHand.getIntrinsicHeight();
    }

    public abstract void moveHand(Canvas canvas, int centerX, int centerY, int units, int preUnits);
}
