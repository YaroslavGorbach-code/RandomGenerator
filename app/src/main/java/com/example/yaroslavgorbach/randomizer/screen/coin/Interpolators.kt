package com.example.yaroslavgorbach.randomizer.screen.coin

import android.animation.TimeInterpolator


class InterpolatorRotateSlowlyTwo: TimeInterpolator {
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
    override fun getInterpolation(t: Float): Float {
        return if (t > 0.20f && t < 0.80f) {
            lastInputBeforeSlowed = lastInput
            (t - lastInputBeforeSlowed) * 0.2f + lastInputBeforeSlowed

        } else {
            lastInput = t
            t
        }
    }
}

class InterpolatorRotateSlowlyOne: TimeInterpolator {
    private var lastInput = 0f
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

class InterpolatorRotateNormalTwo: TimeInterpolator {
    var lastInput = 0f
    var lastInputBeforeSlowed = 0f
    override fun getInterpolation(t: Float): Float {
        return if (t < 0.79f && t > 0.3) {
            lastInputBeforeSlowed = lastInput
            (t - lastInputBeforeSlowed) * 0.25f + lastInputBeforeSlowed

        } else {
            lastInput = t
            t
        }
    }
}

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

class FonScaleInterpolator: TimeInterpolator {

    private fun a(t: Float): Float {
        return t * t * ((1) * t)
    }

    private fun o(t: Float): Float {
        return t * t * ((1) * t)
    }

    override fun getInterpolation(t: Float): Float {
        return if (t < 0.5f) 0.5f * a(t * 2.0f) else 0.5f * (o(t * 2.0f - 2.0f) + 2.0f)
    }

}