package com.example.analog_clock;

import android.widget.ImageView;

public class ClockHandMinute extends AbstractClockHand {
    public ClockHandMinute(ImageView clockHand){
        this.clockHand = clockHand;
    }
    @Override
    public void moveHand(int minutes, int seconds){
        clockHand.setRotation(6*minutes + 2*(seconds/20));
    }
}
