package com.example.analog_clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ClockHandMinute extends AbstractClockHand {
    public ClockHandMinute(Resources res, int clockHand){
        super(res, clockHand);
    }
    @Override
    public void moveHand(int minutes, int seconds){
        matrix.preRotate(6*minutes + 2*(seconds/20));

    }
}
