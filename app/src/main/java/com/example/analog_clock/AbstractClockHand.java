package com.example.analog_clock;

import android.graphics.Bitmap;

public abstract class AbstractClockHand {
    public Bitmap clockHand;
    public abstract void moveHand(int units, int preUnits);
}
