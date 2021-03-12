package com.example.yaroslavgorbach.randomizer.number

import android.animation.*
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation
import kotlin.math.abs

class NumberAnimator {
    private var mRepeatTimes = 0


    fun animateNumber(view: TextView, parent: View, minValue: Long, maxValue: Long){
        animate(view, parent, minValue, maxValue)
    }

    private fun animate(view: TextView, parent: View, minValue: Long, maxValue: Long){
        val hide = ValueAnimator.ofFloat( -400f).apply {
            addUpdateListener {
                view.translationY = animatedValue as Float
                if((animatedValue as Float).toInt() == -400){
                    changeValue(view, minValue, maxValue)
                }
            }
            disableViewDuringAnimation(parent)
        }
        val show = ValueAnimator.ofFloat(400f, 0f).apply {
            addUpdateListener {
                view.translationY = animatedValue as Float
            }
            disableViewDuringAnimation(parent)
        }

        AnimatorSet().apply {
            play(hide).before(show)
            duration = 250
            addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (mRepeatTimes < 5){
                        mRepeatTimes += 1
                        start()
                    }else{
                        mRepeatTimes = 0
                        finishAnimation(view)
                    }
                }
            })
            interpolator = LinearInterpolator()
            start()
        }
    }

    fun finishAnimation(view: TextView){
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -200f, 0f).apply {
            duration = 2000
            interpolator = OvershootInterpolator(1.1f)
            start()
        }
    }

    private fun changeValue(textView: TextView, minValue: Long, maxValue: Long){
        val value = (minValue..maxValue).random()
        when(abs(value)){
            in 0..100 ->{
                textView.textSize = 100F
            }
            in 100..1000 ->{
                textView.textSize = 90F
            }
            in 1000..10000 ->{
                textView.textSize = 80F
            }
            in 10000..1000000 ->{
                textView.textSize = 70F
            }
            in 1000000..1000000000000 ->{
                textView.textSize = 50F
            }
            in 1000000000000..Long.MAX_VALUE ->{
                textView.textSize = 30F
            }
        }
        textView.text = value.toString()

    }

}