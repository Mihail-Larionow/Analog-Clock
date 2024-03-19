package com.michel.analogclock.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.core.content.withStyledAttributes
import com.michel.analogclock.R
import com.michel.analogclock.extensions.dpToPx
import com.michel.analogclock.extensions.radial
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object{
        private const val DEFAULT_SIZE = 40
        private const val DEFAULT_DIAL_COLOR = Color.BLACK
        private const val DEFAULT_BACKGROUND_COLOR = Color.WHITE
        private const val DEFAULT_HOURS_HAND_COLOR = Color.BLACK
        private const val DEFAULT_MINUTES_HAND_COLOR = Color.BLACK
        private const val DEFAULT_SECONDS_HAND_COLOR = Color.BLACK
    }

    private var centerX = 0.0f
    private var centerY = 0.0f
    private var radius = 0.0f
    private var size = 0

    private var hours = 0
    private var minutes = 0
    private var seconds = 0

    private var dialColor = DEFAULT_DIAL_COLOR
    private var backgroundColor = DEFAULT_BACKGROUND_COLOR
    private var labelColor = DEFAULT_DIAL_COLOR
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

    init{
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
                dialColor
            )
            hoursHandColor = getColor(
                R.styleable.ClockView_hoursHandColor,
                hoursHandColor
            )
            minutesHandColor = getColor(
                R.styleable.ClockView_minutesHandColor,
                minutesHandColor
            )
            secondsHandColor = getColor(
                R.styleable.ClockView_secondsHandColor,
                secondsHandColor
            )
        }

        val calendar: Calendar = Calendar.getInstance()
        hours = calendar.get(Calendar.HOUR_OF_DAY) % 12
        minutes = calendar.get(Calendar.MINUTE)
        seconds = calendar.get(Calendar.SECOND)
    }

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(
            max(initSize, size),
            max(initSize, size)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDial(canvas = canvas)
        drawHoursHand(canvas = canvas)
        drawMinutesHand(canvas = canvas)
        drawSecondsHand(canvas = canvas)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val savedState = SavedState(super.onSaveInstanceState())
        savedState.dialColor = dialColor
        savedState.backgroundColor = backgroundColor
        savedState.labelColor = labelColor
        savedState.hoursHandColor = hoursHandColor
        savedState.minutesHandColor = minutesHandColor
        savedState.secondsHandColor = secondsHandColor
        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if(state is SavedState){
            super.onRestoreInstanceState(state)
            dialColor = state.dialColor
            backgroundColor = state.backgroundColor
            labelColor = state.labelColor
            hoursHandColor = state.hoursHandColor
            minutesHandColor = state.minutesHandColor
            secondsHandColor = state.secondsHandColor

        }else{
            super.onRestoreInstanceState(state)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        radius = min(w, h) / 2f
        centerX = width / 2f
        centerY = height / 2f

        setup()
    }

    private fun setup(){
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
            style = Paint.Style.STROKE
        }

        with(labelPaint){
            color = labelColor
            style = Paint.Style.STROKE
            strokeWidth = 0f
            textSize = radius / 4
            textAlign = Paint.Align.CENTER
            textScaleX = 0.9f
            letterSpacing = -0.15f
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

    private fun drawDial(canvas: Canvas){
        drawBackground(canvas = canvas)
        drawBorder(canvas = canvas)
        drawDots(canvas = canvas)
        drawLabels(canvas = canvas)
    }

    private fun drawBackground(canvas: Canvas){
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)
    }

    private fun drawBorder(canvas: Canvas){
        val borderRadius = radius - borderPaint.strokeWidth / 2
        canvas.drawCircle(centerX, centerY, borderRadius, borderPaint)
    }

    private fun drawDots(canvas: Canvas){
        val dotsRadius = 11 * radius / 12
        val dotPosition = PointF(0f, 0f)
        for (i in 0 until 60) {
            dotPosition.radial(
                pos = i,
                rad = dotsRadius,
                x0 = centerX,
                y0 = centerY
            )
            val dotRadius = if (i % 5 == 0) radius / 96 else radius / 128
            canvas.drawCircle(dotPosition.x, dotPosition.y, dotRadius, dotPaint)
        }
    }

    private fun drawLabels(canvas: Canvas){
        val labelsRadius = 3 * radius / 4
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
        val angle = (Math.PI * (hours + minutes) / 360f - Math.PI / 2).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * radius * 3 / 14,
            centerY - sin(angle) * radius * 3 / 14,
            centerX + cos(angle) * radius * 7 / 14,
            centerY + sin(angle) * radius * 7 / 14,
            hoursHandPaint
        )
    }

    private fun drawMinutesHand(canvas: Canvas){
        val angle = (Math.PI * minutes / 30 - Math.PI / 2).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * radius * 2 / 7,
            centerY - sin(angle) * radius * 2 / 7,
            centerX + cos(angle) * radius * 5 / 7,
            centerY + sin(angle) * radius * 5 / 7,
            minutesHandPaint
        )
    }

    private fun drawSecondsHand(canvas: Canvas){
        val angle = (Math.PI * seconds / 30 - Math.PI / 2).toFloat()
        canvas.drawLine(
            centerX - cos(angle) * radius * 1 / 14,
            centerY - sin(angle) * radius * 1 / 14,
            centerX + cos(angle) * radius * 5 / 7,
            centerY + sin(angle) * radius * 5 / 7,
            secondsHandPaint
        )
        canvas.drawLine(
            centerX - cos(angle) * radius * 2 / 7,
            centerY - sin(angle) * radius * 2 / 7,
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

    private class SavedState: BaseSavedState, Parcelable {
        var dialColor = DEFAULT_DIAL_COLOR
        var backgroundColor = DEFAULT_BACKGROUND_COLOR
        var labelColor = DEFAULT_DIAL_COLOR
        var hoursHandColor = DEFAULT_HOURS_HAND_COLOR
        var minutesHandColor = DEFAULT_MINUTES_HAND_COLOR
        var secondsHandColor = DEFAULT_SECONDS_HAND_COLOR

        constructor(superState: Parcelable?): super(superState)

        constructor(src: Parcel): super(src){
            dialColor = src.readInt()
            backgroundColor = src.readInt()
            labelColor = src.readInt()
            hoursHandColor = src.readInt()
            minutesHandColor = src.readInt()
            secondsHandColor = src.readInt()
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(dialColor)
            out.writeInt(backgroundColor)
            out.writeInt(labelColor)
            out.writeInt(hoursHandColor)
            out.writeInt(minutesHandColor)
            out.writeInt(secondsHandColor)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR: Parcelable.Creator<SavedState> {
            override fun createFromParcel(p0: Parcel): SavedState {
                return SavedState(p0)
            }

            override fun newArray(p0: Int): Array<SavedState?> {
                return arrayOfNulls<SavedState?>(p0)
            }

        }
    }

}