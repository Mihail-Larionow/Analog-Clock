package com.example.analog_clock;

import android.graphics.Canvas;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;

public class AnalogClock extends AbstractClock {
    final Drawable clockDial;
    final AbstractClockHand clockHandHour, clockHandMinute, clockHandSecond;

    public AnalogClock(Resources res, int clockDial, int handHour, int handMinute, int handSecond){
        super();

        this.clockDial = ResourcesCompat.getDrawable(res, clockDial, null);
        clockHandHour = new ClockHandHour(res, handHour);
        clockHandMinute = new ClockHandMinute(res, handMinute);
        clockHandSecond = new ClockHandSecond(res, handSecond);
    }

    @Override
    public int getWidth(){
        return clockDial.getIntrinsicWidth();
    }

    @Override
    public int getHeight(){
        return clockDial.getIntrinsicHeight();
    }

    //Overriden method showTime
    @Override
    public void showTime(Canvas canvas, int width, int height){
        tik();

        int centerX = width/2;
        int centerY = height/2;

        int imageWidth = getWidth();
        int imageHeight = getHeight();

        boolean isScaled = false;

        if(width < imageWidth || height < imageHeight) {
            isScaled = true;
            float scale = Math.min((float) width / imageWidth, (float) height / imageHeight);
            canvas.save();
            canvas.scale(scale, scale, (float) width / 2, (float) height / 2);
        }

        clockDial.setBounds(centerX - (imageWidth/2), centerY - (imageHeight/2),
                centerX + (imageWidth/2), centerY + (imageHeight/2));
        clockDial.draw(canvas);

        clockHandSecond.showTime(canvas, centerX, centerY, seconds, 0);
        clockHandMinute.showTime(canvas, centerX, centerY, minutes, seconds);
        clockHandHour.showTime(canvas, centerX, centerY, hours, minutes);

        if(isScaled) canvas.restore();
    }
}
