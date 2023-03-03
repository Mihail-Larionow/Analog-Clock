package com.example.analog_clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ClockHandSecond extends AbstractClockHand {
    public ClockHandSecond(Resources res, int clockHand){
        super(res, clockHand);
    }
    @Override
    public void moveHand(int seconds, int milliSeconds){
        matrix.preRotate(6*seconds + 6*milliSeconds/1000);
    }
}
