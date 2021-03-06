package com.example.yaroslavgorbach.randomizer.coin

import android.animation.TimeInterpolator

class InterpolatorRotateFast: TimeInterpolator {
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
    override fun getInterpolation(t: Float): Float {
        return if (t < 0.8f && t > 0.2) {
            lastInputBeforeSlowed = lastInput
            (t - lastInputBeforeSlowed) * 0.4f + lastInputBeforeSlowed

        } else {
            lastInput = t
            t
        }
    }
}