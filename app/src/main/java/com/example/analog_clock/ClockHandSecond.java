package com.example.analog_clock;

import android.graphics.Bitmap;

public class ClockHandSecond extends AbstractClockHand {
    public ClockHandSecond(Bitmap clockHand){
        this.clockHand = clockHand;
    }
    @Override
    public void moveHand(int seconds, int milliSeconds){
        //clockHand.setRotation(6*seconds + 6*milliSeconds/1000);
    }
}
