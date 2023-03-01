package com.example.analog_clock;

import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Calendar;

public class AnalogClock extends AbstractClock {

    final ImageView clockDial;
    final AbstractClockHand clockHandHour, clockHandMinute, clockHandSecond;

    public AnalogClock(LinearLayout parentLayout, LayoutInflater inflater){
        super(parentLayout, inflater);
        clockDial = clockView.findViewById(R.id.clock_dial);
        clockHandHour = new ClockHandHour(clockView.findViewById(R.id.clock_hand_hour));
        clockHandMinute = new ClockHandMinute(clockView.findViewById(R.id.clock_hand_minute));
        clockHandSecond = new ClockHandSecond(clockView.findViewById(R.id.clock_hand_second));
    }

    @Override
    public void showClock(LinearLayout parentLayout, LayoutInflater inflater){
        clockView = inflater.inflate(R.layout.analog_clock, null, false);
        parentLayout.addView(clockView);
    }

    @Override
    public void showTime(){
        clockHandHour.moveHand(hours, minutes);
        clockHandMinute.moveHand(minutes, seconds);
        clockHandSecond.moveHand(seconds, 0);
    }
}
