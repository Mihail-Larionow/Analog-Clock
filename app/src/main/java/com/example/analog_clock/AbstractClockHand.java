package com.example.analog_clock;

import android.graphics.Canvas;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;

public abstract class AbstractClockHand {

    public Drawable imageClockHand;
    protected int MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT;

    public AbstractClockHand(Resources res, int imageClockHand){
        this.imageClockHand = ResourcesCompat.getDrawable(res, imageClockHand, null);

        MAX_IMAGE_WIDTH = this.imageClockHand.getIntrinsicWidth();
        MAX_IMAGE_HEIGHT = this.imageClockHand.getIntrinsicHeight();
    }

    //Abstract method to show time by hand
    public abstract void showTime(Canvas canvas, int centerX, int centerY, int units, int preUnits);
}
