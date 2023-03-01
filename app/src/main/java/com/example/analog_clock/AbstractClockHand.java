package com.example.analog_clock;

import android.widget.ImageView;

import java.util.Calendar;

public abstract class AbstractClockHand {
    public ImageView clockHand;
    public abstract void moveHand(int units, int preUnits);
}
