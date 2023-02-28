package com.example.analog_clock;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AnalogClock {

    final Date date;
    final Calendar calendar = GregorianCalendar.getInstance();

    public AnalogClock(){
        date = new Date();
        calendar.setTime(date);
    }

    public int getHours(){
        return calendar.get(Calendar.HOUR);
    }
    public int getMinutes(){
        return calendar.get(Calendar.MINUTE);
    }
    public int getSeconds(){
        return calendar.get(Calendar.SECOND);
    }
}
