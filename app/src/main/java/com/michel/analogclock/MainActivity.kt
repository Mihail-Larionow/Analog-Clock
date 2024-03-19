package com.michel.analogclock

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.setMargins
import com.michel.analogclock.ui.ClockView

class MainActivity : AppCompatActivity() {

    private val backgroundColors = arrayOf(
        Color.BLACK,
        Color.WHITE,
        Color.CYAN,
        Color.YELLOW,
        Color.TRANSPARENT
    )

    private val dialColors = arrayOf(
        Color.WHITE,
        Color.BLUE,
        Color.BLACK,
        Color.GREEN,
        Color.DKGRAY
    )

    private val labelColors = arrayOf(
        Color.WHITE,
        Color.BLUE,
        Color.BLACK,
        Color.MAGENTA,
        Color.DKGRAY
    )

    private val hoursHandColors = arrayOf(
        Color.WHITE,
        Color.BLACK,
        Color.BLACK,
        Color.RED,
        Color.DKGRAY
    )

    private val minutesHandColors = arrayOf(
        Color.WHITE,
        Color.BLACK,
        Color.BLACK,
        Color.BLUE,
        Color.DKGRAY
    )

    private val secondsHandColors = arrayOf(
        Color.WHITE,
        Color.BLACK,
        Color.BLACK,
        Color.CYAN,
        Color.RED
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )

        val textView = TextView(this).apply{
            id = View.generateViewId()
            layoutParams = textViewParams
            text = "CLICK"
            textSize = 32f
            gravity = Gravity.CENTER
        }

        val clockViewParams = LinearLayout.LayoutParams(400, 400)
        clockViewParams.setMargins(0, 0, 0, 20)

        val clockView = ClockView(this).apply{
            id = View.generateViewId()
            layoutParams = clockViewParams
        }

        var i = 0
        clockView.setOnClickListener {

            clockView.setDialColor(dialColors[i])
            clockView.setBackgroundColor(backgroundColors[i])
            clockView.setLabelColor(labelColors[i])
            clockView.setHoursHandColor(hoursHandColors[i])
            clockView.setMinutesHandColor(minutesHandColors[i])
            clockView.setSecondsHandColor(secondsHandColors[i])
            i = (i + 1) % 5
        }

        findViewById<LinearLayout>(R.id.main).addView(clockView)
        findViewById<LinearLayout>(R.id.main).addView(textView)

    }
}