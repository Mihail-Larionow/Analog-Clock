package com.example.analog_clock;
import android.widget.ImageView;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class AnalogClock extends AbstractClock{

    private Date date;
    final Calendar calendar = GregorianCalendar.getInstance();
    final ImageView clock_dial, clock_hand_hour, clock_hand_minute, clock_hand_second;

    public AnalogClock(ImageView clock_dial, ImageView clock_hand_hour, ImageView clock_hand_minute, ImageView clock_hand_second){
        date = new Date();
        calendar.setTime(date);
        //this.clock_layout = clock_layout;
        this.clock_dial = clock_dial;
        this.clock_hand_hour = clock_hand_hour;
        this.clock_hand_minute = clock_hand_minute;
        this.clock_hand_second = clock_hand_second;
        this.start();
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
    public void ChangeTime(){
        rotateHourHand();
        rotateMinuteHand();
        rotateSecondHand();
    }
    private void rotateHourHand(){
        clock_hand_hour.setRotation(360*getHours()/12);
    }
    private void rotateMinuteHand(){
        clock_hand_minute.setRotation(360*getMinutes()/60);
    }
    private void rotateSecondHand(){
        clock_hand_second.setRotation(360*getSeconds()/60);
    }
    public void run(){
        while(true){
            date = new Date();
            calendar.setTime(date);
            ChangeTime();
        }
    }
}
