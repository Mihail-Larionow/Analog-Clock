package com.example.analog_clock;

import android.widget.ImageView;

public class ClockHandSecond extends AbstractClockHand {
    public ClockHandSecond(ImageView clockHand){
        this.clockHand = clockHand;
    }
    @Override
    public void moveHand(int seconds, int milliSeconds){
        clockHand.setRotation(6*seconds + 6*milliSeconds/1000);
    }
}
