package com.example.yaroslavgorbach.randomizer.coin

import android.animation.TimeInterpolator

class InterpolatorRotateNormalTwo: TimeInterpolator {
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
    override fun getInterpolation(t: Float): Float {
        return if (t < 0.9f && t > 0.2) {
            lastInputBeforeSlowed = lastInput
            (t - lastInputBeforeSlowed) * 0.2f + lastInputBeforeSlowed

        } else {
            lastInput = t
            t
        }
    }
}