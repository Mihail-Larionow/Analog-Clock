package com.example.analog_clock;

import java.util.Calendar;
import java.util.TimeZone;
import android.graphics.Canvas;

public abstract class AbstractClock{

    final TimeZone timeZone;
    private Calendar calendar;
    protected int hours, minutes, seconds;

    protected AbstractClock() {
        timeZone = TimeZone.getDefault();
        calendar = Calendar.getInstance(timeZone);
        hours = getHours();
        minutes = getMinutes();
        seconds = getSeconds();
    }

    //Abstract method to show time at the clock
    public abstract void showTime(Canvas canvas, int width, int height);

    public int getHours(){
        return calendar.get(Calendar.HOUR);
    }

    public int getMinutes(){
        return calendar.get(Calendar.MINUTE);
    }

    public int getSeconds(){
        return calendar.get(Calendar.SECOND);
    }

    public abstract int getWidth();

    public abstract int getHeight();

    //Change hours, minutes and seconds of the clock
    protected void tik(){
        calendar = Calendar.getInstance(timeZone);
        hours = getHours();
        minutes = getMinutes();
        seconds = getSeconds();
    }
}
