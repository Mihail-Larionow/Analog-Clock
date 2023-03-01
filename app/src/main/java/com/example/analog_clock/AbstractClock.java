package com.example.analog_clock;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Calendar;
import java.util.TimeZone;

public abstract class AbstractClock extends Thread{
    Calendar calendar;
    final TimeZone timeZone;
    public View clockView;

    public int hours, minutes, seconds;
    public AbstractClock(LinearLayout parentLayout, LayoutInflater inflater) {
        timeZone = TimeZone.getDefault();
        this.showClock(parentLayout, inflater);
    }
    public abstract void showClock(LinearLayout parentLayout, LayoutInflater inflater);
    public abstract void showTime();

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
                showTime();
            }
        }
        catch (Exception e){

        }
    }
}
