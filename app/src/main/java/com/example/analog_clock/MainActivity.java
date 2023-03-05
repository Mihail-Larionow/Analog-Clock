package com.example.analog_clock;

import android.os.Bundle;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.app.AppCompatActivity;
import com.example.analog_clock.adapters.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.pager);
        FragmentStateAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(pagerAdapter);
    }
}