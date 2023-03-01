package com.example.analog_clock.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.analog_clock.R;

public class AnalogClockView extends View{
    private int handHour, handMinute, handSecond;
    private Bitmap resultBm;

    public AnalogClockView(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AnalogClockView,
                0, 0);
        try {
            handHour = a.getInteger(R.styleable.AnalogClockView_hand_hour, 0);
            handMinute = a.getInteger(R.styleable.AnalogClockView_hand_minute, 0);
            handSecond = a.getInteger(R.styleable.AnalogClockView_hand_second, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas){

    }
}