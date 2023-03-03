package com.example.analog_clock;

import android.graphics.Bitmap;

public class ClockHandHour extends AbstractClockHand {
    public ClockHandHour(Bitmap clockHand){
        this.clockHand = clockHand;
    }
    @Override
    public void moveHand(int hours, int minutes){
        //clockHand.setRotation(30*hours + minutes/2);
    }
}
