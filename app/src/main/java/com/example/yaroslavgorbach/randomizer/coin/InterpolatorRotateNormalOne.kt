package com.example.yaroslavgorbach.randomizer.coin

import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import java.lang.Math.round
import kotlin.math.roundToInt

class InterpolatorRotateNormalOne: TimeInterpolator{
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
        override fun getInterpolation(t: Float): Float {
            return if (t < 0.8f && t > 0.2) {
                lastInputBeforeSlowed = lastInput
                (t - lastInputBeforeSlowed) * 0.35f + lastInputBeforeSlowed

            } else {
                lastInput = t
                t
            }
        }
}
