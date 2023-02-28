package com.example.analog_clock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView clock_dial, clock_hand_hour, clock_hand_minute, clock_hand_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        clock_dial = findViewById(R.id.clock_dial);
        clock_hand_hour = findViewById(R.id.clock_hand_hour);
        clock_hand_minute = findViewById(R.id.clock_hand_minute);
        clock_hand_second = findViewById(R.id.clock_hand_second);
        AnalogClock analogClock = new AnalogClock(clock_dial, clock_hand_hour, clock_hand_minute, clock_hand_second);

    }
}