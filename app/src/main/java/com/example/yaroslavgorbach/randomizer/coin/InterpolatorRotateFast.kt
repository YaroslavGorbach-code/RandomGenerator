package com.example.yaroslavgorbach.randomizer.coin

import android.animation.TimeInterpolator

class InterpolatorRotateFast: TimeInterpolator {
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
    override fun getInterpolation(t: Float): Float {
        return if (t > 0.30f &&  t < 0.70f) {
            lastInputBeforeSlowed = lastInput
            (t - lastInputBeforeSlowed) * 0.30f + lastInputBeforeSlowed

        } else {
            lastInput = t
            t
        }
    }
}