package com.example.analog_clock;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Calendar;
import java.util.TimeZone;

public abstract class AbstractClock extends Thread{
    private Calendar calendar;
    final TimeZone timeZone;
    protected int hours, minutes, seconds;
    protected int MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT;
    protected AbstractClock() {
        timeZone = TimeZone.getDefault();
        calendar = Calendar.getInstance(timeZone);
        hours = getHours();
        minutes = getMinutes();
        seconds = getSeconds();
    }
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

    public void tik(){
        calendar = Calendar.getInstance(timeZone);
        hours = getHours();
        minutes = getMinutes();
        seconds = getSeconds();
    }

    @Override
    public void run(){
        try {
            while (true) {
                tik();
                //showTime();
            }
        }
        catch (Exception e){

        }
    }
}
