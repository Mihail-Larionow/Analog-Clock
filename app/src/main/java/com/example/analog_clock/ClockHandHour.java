package com.example.analog_clock;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ClockHandHour extends AbstractClockHand {
    public ClockHandHour(Resources res, int clockHand){
        super(res,clockHand);
    }
    @Override
    public void moveHand(Canvas canvas, int centerX, int centerY, int hours, int minutes){
        canvas.save();
        canvas.rotate( 30*hours + minutes/2, centerX, centerY);
        imageClockHand.setBounds(centerX - (MAX_IMAGE_WIDTH/2), centerY - (MAX_IMAGE_HEIGHT/2),
                centerX + (MAX_IMAGE_WIDTH/2), centerY + (MAX_IMAGE_HEIGHT/2));
        imageClockHand.draw(canvas);
        canvas.restore();
    }

}
