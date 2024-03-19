package com.michel.analogclock.extensions

import android.graphics.PointF
import kotlin.math.cos
import kotlin.math.sin

fun PointF.radial(pos: Int, rad: Float, x0: Float, y0: Float){
    val angle = (pos * (Math.PI / 30)).toFloat()
    x = rad * cos(angle) + x0
    y = rad * sin(angle) + y0
}

fun PointF.radial(pos: Int, rad: Float, x0: Float, y0: Float, baseline: Float){
    val angle = (pos * (Math.PI / 6) - Math.PI / 2).toFloat()
    x = x0 + rad * cos(angle)
    y = y0 + rad * sin(angle) - baseline
}