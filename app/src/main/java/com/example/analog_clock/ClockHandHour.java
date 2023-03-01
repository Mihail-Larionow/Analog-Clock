package com.example.analog_clock;

import android.widget.ImageView;

public class ClockHandHour extends AbstractClockHand {
    public ClockHandHour(ImageView clockHand){
        this.clockHand = clockHand;
    }
    @Override
    public void moveHand(int hours, int minutes){
        clockHand.setRotation(30*hours + minutes/2);
    }
}
