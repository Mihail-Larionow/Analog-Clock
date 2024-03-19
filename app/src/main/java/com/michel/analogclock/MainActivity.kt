package com.michel.analogclock

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.michel.analogclock.ui.ClockView

class MainActivity : AppCompatActivity() {

    private val viewId = 10

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

        val clockViewParams = LinearLayout.LayoutParams(400, 400)
        clockViewParams.setMargins(0, 0, 0, 100)

        // Add Clock View programmatically
        val clockView = ClockView(this).apply{
            id = viewId
            layoutParams = clockViewParams
        }

        var i = 0
        clockView.setOnClickListener {
            clockView.setDialColor(color = dialColors[i])
            clockView.setBackgroundColor(color = backgroundColors[i])
            clockView.setLabelColor(color = labelColors[i])
            clockView.setHoursHandColor(color = hoursHandColors[i])
            clockView.setMinutesHandColor(color = minutesHandColors[i])
            clockView.setSecondsHandColor(color = secondsHandColors[i])
            i = (i + 1) % 5
        }

        val buttonParams = LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )

        val button = Button(this).apply {
            layoutParams = buttonParams
            text = context.getString(R.string.next)
            textSize = 26f
        }

        button.setOnClickListener {
            WorkActivity.startFrom(this@MainActivity)
        }

        findViewById<LinearLayout>(R.id.main).addView(clockView)
        findViewById<LinearLayout>(R.id.main).addView(button)
    }

    companion object{
        fun startFrom(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

}