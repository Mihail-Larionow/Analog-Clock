package com.example.analog_clock.ui;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.example.analog_clock.R;
import android.content.res.Resources;
import android.content.res.TypedArray;
import com.example.analog_clock.AnalogClock;
import com.example.analog_clock.AbstractClock;

public class AnalogClockView extends View{

    final int dial, handHour, handMinute, handSecond;
    final int MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT;
    private int viewWidth, viewHeight;
    final AbstractClock analogClock;

    public AnalogClockView(Context context, AttributeSet attrs){
        super(context, attrs);

        Resources res = getResources();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AnalogClockView,
                0, 0);
        try {
            dial = a.getResourceId(R.styleable.AnalogClockView_dial, R.drawable.clock_dial);
            handHour = a.getResourceId(R.styleable.AnalogClockView_hand_hour, R.drawable.clock_hand_hour);
            handMinute = a.getResourceId(R.styleable.AnalogClockView_hand_minute, R.drawable.clock_hand_minute);
            handSecond = a.getResourceId(R.styleable.AnalogClockView_hand_second, R.drawable.clock_hand_second);
        } finally {
            a.recycle();
        }

        analogClock = new AnalogClock(res, dial, handHour, handMinute, handSecond);

        MAX_IMAGE_WIDTH = analogClock.getWidth();
        MAX_IMAGE_HEIGHT = analogClock.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth = measureDimension(MAX_IMAGE_WIDTH, widthMeasureSpec);
        viewHeight = measureDimension(MAX_IMAGE_HEIGHT, heightMeasureSpec);

        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        analogClock.showTime(canvas, viewWidth, viewHeight);

        postInvalidateDelayed(500);
        invalidate();
    }

    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}