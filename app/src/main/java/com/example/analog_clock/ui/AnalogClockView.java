package com.example.analog_clock.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.example.analog_clock.R;

public class AnalogClockView extends View{
    private int dial, handHour, handMinute, handSecond;
    private Bitmap bitmapDial, bitmapHandHour, bitmapHandMinute, bitmapHandSecond;
    private int bitmapWidth, bitmapHeight;
    private int viewWidth, viewHeight;
    Context context;

    public AnalogClockView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
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
    }

    @Override
    protected void onDraw(Canvas canvas){
        Resources res = getResources();
        Bitmap bm = BitmapFactory.decodeResource(res, dial);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        bitmapWidth = Math.min(displayMetrics.widthPixels, viewWidth);
        bitmapHeight = Math.min(displayMetrics.widthPixels, viewHeight);
        if(bitmapHeight>bitmapWidth) bitmapHeight = bitmapWidth;
        else bitmapWidth = bitmapHeight;
        bitmapDial = Bitmap.createScaledBitmap(bm, bitmapWidth, bitmapHeight, false);

        bm = BitmapFactory.decodeResource(res, handHour);
        bitmapHandHour = Bitmap.createScaledBitmap(bm, bitmapWidth, bitmapHeight, false);

        bm = BitmapFactory.decodeResource(res, handMinute);
        bitmapHandMinute = Bitmap.createScaledBitmap(bm, bitmapWidth, bitmapHeight, false);

        bm = BitmapFactory.decodeResource(res, handSecond);
        bitmapHandSecond = Bitmap.createScaledBitmap(bm, bitmapWidth, bitmapHeight, false);

        canvas.drawBitmap(bitmapDial,0,0, null);
        canvas.drawBitmap(bitmapHandHour,0,0, null);
        canvas.drawBitmap(bitmapHandMinute,0,0, null);
        canvas.drawBitmap(bitmapHandSecond,0,0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 1024;
        int desiredHeight = 1024;

        viewWidth = measureDimension(desiredWidth, widthMeasureSpec);
        viewHeight = measureDimension(desiredHeight, heightMeasureSpec);

        setMeasuredDimension(viewWidth, viewHeight);
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
        if (result < desiredSize){

        }
        return result;
    }
}