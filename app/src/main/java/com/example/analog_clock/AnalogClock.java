package com.example.analog_clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

public class AnalogClock extends AbstractClock {

    Drawable clockDial;
    final AbstractClockHand clockHandHour, clockHandMinute, clockHandSecond;

    public AnalogClock(Resources res, int clockDial, int handHour, int handMinute, int handSecond){
        super();
        this.clockDial = ResourcesCompat.getDrawable(res, clockDial, null);
        MAX_IMAGE_WIDTH = this.clockDial.getIntrinsicWidth();
        MAX_IMAGE_HEIGHT = this.clockDial.getIntrinsicHeight();

        clockHandHour = new ClockHandHour(res, handHour);
        clockHandMinute = new ClockHandMinute(res, handMinute);
        clockHandSecond = new ClockHandSecond(res, handSecond);
    }

    @Override
    public void showTime(Canvas canvas, int width, int height){
        int centerX = width/2;
        int centerY = height/2;

        if(width < MAX_IMAGE_WIDTH || height < MAX_IMAGE_HEIGHT) {
            float scale = Math.min((float) width / MAX_IMAGE_WIDTH, (float) height / MAX_IMAGE_HEIGHT);
            canvas.save();
            canvas.scale(scale, scale, width / 2, height / 2);
        }
        clockDial.setBounds(centerX - (MAX_IMAGE_WIDTH/2), centerY - (MAX_IMAGE_HEIGHT/2),
                centerX + (MAX_IMAGE_WIDTH/2), centerY + (MAX_IMAGE_HEIGHT/2));
        clockDial.draw(canvas);
        canvas.save();
    }
}
