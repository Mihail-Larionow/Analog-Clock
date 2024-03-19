package com.michel.analogclock.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import com.michel.analogclock.R
import com.michel.analogclock.extensions.dpToPx
import com.michel.analogclock.extensions.radial
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class ClockView : View {

    companion object{
        private const val DEFAULT_SIZE = 200
        private const val DEFAULT_DIAL_COLOR = Color.BLACK
        private const val DEFAULT_BACKGROUND_COLOR = Color.WHITE
        private const val DEFAULT_LABEL_COLOR = Color.BLACK
        private const val DEFAULT_HOURS_HAND_COLOR = Color.BLACK
        private const val DEFAULT_MINUTES_HAND_COLOR = Color.BLACK
        private const val DEFAULT_SECONDS_HAND_COLOR = Color.BLACK
    }

    private var centerX = DEFAULT_SIZE / 2f
    private var centerY = DEFAULT_SIZE / 2f
    private var radius = DEFAULT_SIZE / 2f

    private var hours = 0
    private var minutes = 0
    private var seconds = 0

    private var dialColor = DEFAULT_DIAL_COLOR
    private var backgroundColor = DEFAULT_BACKGROUND_COLOR
    private var labelColor = DEFAULT_LABEL_COLOR
    private var hoursHandColor = DEFAULT_HOURS_HAND_COLOR
    private var minutesHandColor = DEFAULT_MINUTES_HAND_COLOR
    private var secondsHandColor = DEFAULT_SECONDS_HAND_COLOR

    private var backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var labelPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var hoursHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var minutesHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var secondsHandPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // To calculate the local time
    private val timeUpdater: Runnable

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs){
                attributesSetup(attrs = attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr, 0){
                attributesSetup(attrs = attrs)
    }

    init{
        timeUpdater = object: Runnable {
            override fun run() {
                updateTime()
                invalidate()
                this@ClockView.postDelayed(this, 1000)
            }
        }
    }

    // Public methods for setting attributes programmatically
    fun setDialColor(@ColorInt color: Int){
        dialColor = color
        borderPaint.color = dialColor
        dotPaint.color = dialColor
        invalidate()
    }

    fun setLabelColor(@ColorInt color: Int){
        labelColor = color
        labelPaint.color = labelColor
        invalidate()
    }

    fun setHoursHandColor(@ColorInt color: Int){
        hoursHandColor = color
        hoursHandPaint.color = hoursHandColor
        invalidate()
    }

    fun setMinutesHandColor(@ColorInt color: Int){
        minutesHandColor = color
        minutesHandPaint.color = minutesHandColor
        invalidate()
    }

    fun setSecondsHandColor(@ColorInt color: Int){
        secondsHandColor = color
        secondsHandPaint.color = secondsHandColor
        invalidate()
    }

    override fun setBackgroundColor(@ColorInt color: Int){
        backgroundColor = color
        backgroundPaint.color = backgroundColor
        invalidate()
    }

    // Overridden view's lifecycle methods
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        this.post(timeUpdater)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.removeCallbacks(timeUpdater)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = min(w, h) / 2f
        centerX = w / 2f
        centerY = h / 2f

        // Update paint after a dimensional change
        paintSetup()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas = canvas)
        drawBorder(canvas = canvas)
        drawDots(canvas = canvas)
        drawLabels(canvas = canvas)
        drawHoursHand(canvas = canvas)
        drawMinutesHand(canvas = canvas)
        drawSecondsHand(canvas = canvas)
    }

    // View's state preserve
    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("superState", super.onSaveInstanceState())
        bundle.putInt("dialColor", dialColor)
        bundle.putInt("backgroundColor", backgroundColor)
        bundle.putInt("labelColor", labelColor)
        bundle.putInt("hoursHandColor", hoursHandColor)
        bundle.putInt("minutesHandColor", minutesHandColor)
        bundle.putInt("secondsHandColor", secondsHandColor)
        return bundle
    }

    // View's state upload
    @Suppress("DEPRECATION")
    override fun onRestoreInstanceState(state: Parcelable?) {
        var superState: Parcelable? = null
        if(state is Bundle){
            dialColor = state.getInt("dialColor")
            backgroundColor = state.getInt("backgroundColor")
            labelColor = state.getInt("labelColor")
            hoursHandColor = state.getInt("hoursHandColor")
            minutesHandColor = state.getInt("minutesHandColor")
            secondsHandColor = state.getInt("secondsHandColor")
            superState = if(SDK_INT >= 33) state.getParcelable("superState", Parcelable::class.java)
            else state.getParcelable("superState")
        }
        super.onRestoreInstanceState(superState)
    }

    private fun attributesSetup(attrs: AttributeSet?){
        context.withStyledAttributes(attrs, R.styleable.ClockView){
            dialColor = getColor(
                R.styleable.ClockView_dialColor,
                DEFAULT_DIAL_COLOR
            )
            backgroundColor = getColor(
                R.styleable.ClockView_backgroundColor,
                DEFAULT_BACKGROUND_COLOR
            )
            labelColor = getColor(
                R.styleable.ClockView_labelColor,
                DEFAULT_LABEL_COLOR
            )
            hoursHandColor = getColor(
                R.styleable.ClockView_hoursHandColor,
                DEFAULT_HOURS_HAND_COLOR
            )
            minutesHandColor = getColor(
                R.styleable.ClockView_minutesHandColor,
                DEFAULT_MINUTES_HAND_COLOR
            )
            secondsHandColor = getColor(
                R.styleable.ClockView_secondsHandColor,
                DEFAULT_SECONDS_HAND_COLOR
            )
        }
    }

    private fun paintSetup(){
        with(backgroundPaint){
            color = backgroundColor
            style = Paint.Style.FILL
        }

        with(borderPaint){
            color = dialColor
            style = Paint.Style.STROKE
            strokeWidth = radius / 12
        }

        with(dotPaint){
            color = dialColor
            style = Paint.Style.FILL
        }

        with(labelPaint){
            color = labelColor
            style = Paint.Style.STROKE
            strokeWidth = 0f
            textSize = radius / 3
            textAlign = Paint.Align.CENTER
            textScaleX = 0.9f
            letterSpacing = -0.2f
        }

        with(hoursHandPaint){
            color = hoursHandColor
            style = Paint.Style.STROKE
            strokeWidth = radius / 15
        }

        with(minutesHandPaint){
            color = minutesHandColor
            style = Paint.Style.STROKE
            strokeWidth = radius / 30
        }

        with(secondsHandPaint){
            color = secondsHandColor
            style = Paint.Style.STROKE
            strokeWidth = radius / 90
        }
    }

    private fun drawBackground(canvas: Canvas){
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)
    }

    private fun drawBorder(canvas: Canvas){
        val borderRadius = radius - borderPaint.strokeWidth / 2
        canvas.drawCircle(centerX, centerY, borderRadius, borderPaint)
    }

    private fun drawDots(canvas: Canvas){
        val dotsRadius = 21 * radius / 24
        val dotPosition = PointF(0f, 0f)
        for (i in 0 until 60) {
            dotPosition.radial(
                pos = i,
                rad = dotsRadius,
                x0 = centerX,
                y0 = centerY
            )
            val dotRadius = if (i % 5 == 0) radius / 70 else radius / 90
            canvas.drawCircle(dotPosition.x, dotPosition.y, dotRadius, dotPaint)
        }
    }

    private fun drawLabels(canvas: Canvas){
        val labelsRadius = 11 * radius / 16
        val labelPosition = PointF(0f, 0f)
        val labelOffSet = (labelPaint.descent() + labelPaint.ascent()) / 2
        for (i in 1..12) {
            labelPosition.radial(
                pos = i,
                rad = labelsRadius,
                x0 = centerX,
                y0 = centerY,
                offSet = labelOffSet
            )
            canvas.drawText(i.toString(), labelPosition.x, labelPosition.y, labelPaint)
        }
    }

    private fun drawHoursHand(canvas: Canvas){
        val angle = (Math.PI * (hours + minutes / 60f) / 6 - Math.PI / 2).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * radius * 3 / 14,
            centerY - sin(angle) * radius * 3 / 14,
            centerX + cos(angle) * radius * 1 / 2,
            centerY + sin(angle) * radius * 1 / 2,
            hoursHandPaint
        )
    }

    private fun drawMinutesHand(canvas: Canvas){
        val angle = (Math.PI * minutes / 30 - Math.PI / 2).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * radius * 5 / 14,
            centerY - sin(angle) * radius * 5 / 14,
            centerX + cos(angle) * radius * 11 / 14,
            centerY + sin(angle) * radius * 11 / 14,
            minutesHandPaint
        )
    }

    private fun drawSecondsHand(canvas: Canvas){
        val angle = (Math.PI * seconds / 30 - Math.PI / 2).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * radius * 1 / 14,
            centerY - sin(angle) * radius * 1 / 14,
            centerX + cos(angle) * radius * 11 / 14,
            centerY + sin(angle) * radius * 11 / 14,
            secondsHandPaint
        )
        canvas.drawLine(
            centerX - cos(angle) * radius * 5 / 14,
            centerY - sin(angle) * radius * 5 / 14,
            centerX - cos(angle) * radius * 1 / 14,
            centerY - sin(angle) * radius * 1 / 14,
            secondsHandPaint
        )
    }

    private fun resolveDefaultSize(spec: Int): Int{
        return when(MeasureSpec.getMode(spec)){
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun updateTime(){
        val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        hours = time.substring(0, 2).toInt() % 12
        minutes = time.substring(3, 5).toInt()
        seconds = time.substring(6, 8).toInt()
    }

}