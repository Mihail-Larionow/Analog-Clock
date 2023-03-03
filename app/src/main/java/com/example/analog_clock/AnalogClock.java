package com.example.analog_clock;

import android.graphics.Bitmap;

public class AnalogClock extends AbstractClock {

    final Bitmap clockDial;
    final AbstractClockHand clockHandHour, clockHandMinute, clockHandSecond;

    public AnalogClock(Bitmap clockDial, Bitmap handHour, Bitmap handMinute, Bitmap handSecond){
        super();
        this.clockDial = clockDial;
        clockHandHour = new ClockHandHour(handHour);
        clockHandMinute = new ClockHandMinute(handMinute);
        clockHandSecond = new ClockHandSecond(handSecond);
    }

    @Override
    public void showTime(){
        clockHandHour.moveHand(hours, minutes);
        clockHandMinute.moveHand(minutes, seconds);
        clockHandSecond.moveHand(seconds, 0);
    }
}
