package com.example.yaroslavgorbach.randomizer.number

import android.animation.*
import android.view.TextureView
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation

class NumberAnimator {
    private var mRepeatTimes = 0

    fun animateNumber(view: TextView, parent: View){
        animate(view, parent)
    }

    private fun animate(view: TextView, parent: View){
        val hide = ValueAnimator.ofFloat( -400f).apply {
            addUpdateListener {
                view.translationY = animatedValue as Float
                if((animatedValue as Float).toInt() == -400){
                    view.text = (0..100).random().toString()
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
            duration = 200
            addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (mRepeatTimes < 5){
                        mRepeatTimes += 1
                        start()
                    }else{
                        mRepeatTimes = 0
                    }
                }
            })
            interpolator = LinearInterpolator()
            start()
        }
    }
}