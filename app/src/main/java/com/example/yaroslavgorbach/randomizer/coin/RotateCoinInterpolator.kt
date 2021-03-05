package com.example.yaroslavgorbach.randomizer.coin

import android.animation.TimeInterpolator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import java.lang.Math.round
import kotlin.math.roundToInt

class RotateCoinInterpolator: TimeInterpolator{
    override fun getInterpolation(t: Float): Float {
        return if (t<0.8f && t > 0.37f){
            (t/3f) + 0.5f
        }else {
            t + 0.5f
        }
    }
}
