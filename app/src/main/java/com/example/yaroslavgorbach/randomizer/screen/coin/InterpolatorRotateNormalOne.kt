package com.example.yaroslavgorbach.randomizer.screen.coin

import android.animation.TimeInterpolator

class InterpolatorRotateNormalOne: TimeInterpolator{
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
        override fun getInterpolation(t: Float): Float {
            return if (t > 0.40f &&  t < 1f) {
                lastInputBeforeSlowed = lastInput
                (t - lastInputBeforeSlowed) * 0.20f + lastInputBeforeSlowed

            } else {
                lastInput = t
                t
            }
        }
}
