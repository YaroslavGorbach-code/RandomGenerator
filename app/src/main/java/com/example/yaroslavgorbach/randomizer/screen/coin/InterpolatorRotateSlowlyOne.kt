package com.example.yaroslavgorbach.randomizer.screen.coin

import android.animation.TimeInterpolator

class InterpolatorRotateSlowlyOne: TimeInterpolator {
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
    override fun getInterpolation(t: Float): Float {
        return if (t > 0.20f &&  t < 0.80f) {
            lastInputBeforeSlowed = lastInput
            (t - lastInputBeforeSlowed) * 0.25f + lastInputBeforeSlowed

        } else {
            lastInput = t
            t
        }
    }
}