package com.example.analog_clock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;

import com.example.analog_clock.ui.AnalogClockView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.main_layout);


        // AbstractClock analogClock = new AnalogClock(mainLayout, getLayoutInflater());
        //analogClock.start();
        //textView.setText(analogClock.getHours() + " " + analogClock.getMinutes() + " " + analogClock.getSeconds());

    }
}