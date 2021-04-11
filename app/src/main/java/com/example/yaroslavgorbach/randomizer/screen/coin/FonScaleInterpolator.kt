package com.example.yaroslavgorbach.randomizer.screen.coin

import android.animation.TimeInterpolator

class FonScaleInterpolator: TimeInterpolator {
    private val mTension = 0.0f

    private fun a(t: Float, s: Float): Float {
        return t * t * ((s + 1) * t - s)
    }

    private fun o(t: Float, s: Float): Float {
        return t * t * ((s + 1) * t + s)
    }

    override fun getInterpolation(t: Float): Float {
        return if (t < 0.5f) 0.5f * a(t * 2.0f, mTension) else 0.5f * (o(t * 2.0f - 2.0f, mTension) + 2.0f)
    }

}