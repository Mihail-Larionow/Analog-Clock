package com.example.analog_clock.ui;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.example.analog_clock.AbstractClock;
import com.example.analog_clock.AnalogClock;
import com.example.analog_clock.R;

public class AnalogClockView extends View{
    private int dial, handHour, handMinute, handSecond;
    private int viewCenterX, viewCenterY;
    Drawable imageDial, imageHandHour, imageHandMinute, imageHandSecond;
    private Bitmap bitmapDial, bitmapHandHour, bitmapHandMinute, bitmapHandSecond;
    private int viewWidth, viewHeight;

    private int MAX_IMAGE_WIDTH = 1024, MAX_IMAGE_HEIGHT = 1024;
    Context context;

    private AbstractClock analogClock;

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
        Resources res = getResources();
        bitmapDial = createBitmap(res, dial, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
        bitmapHandHour = createBitmap(res, handHour, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
        bitmapHandMinute = createBitmap(res, handMinute, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
        bitmapHandSecond = createBitmap(res, handSecond, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);

        analogClock = new AnalogClock(res, dial, handHour, handMinute, handSecond);

        imageDial = ResourcesCompat.getDrawable(res, R.drawable.clock_dial, null);

        MAX_IMAGE_WIDTH = imageDial.getIntrinsicWidth();
        MAX_IMAGE_HEIGHT = imageDial.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = MAX_IMAGE_WIDTH;
        int desiredHeight = MAX_IMAGE_HEIGHT;

        viewWidth = measureDimension(desiredWidth, widthMeasureSpec);
        viewHeight = measureDimension(desiredHeight, heightMeasureSpec);

        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        canvas.save();
        analogClock.showTime(canvas, viewWidth, viewHeight);
        canvas.restore();
    }

    private Bitmap createBitmap(Resources res, int drawable, int width, int height){
        Bitmap newBitmap = BitmapFactory.decodeResource(res, drawable);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(newBitmap, width, height, false);
        return scaledBitmap;
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