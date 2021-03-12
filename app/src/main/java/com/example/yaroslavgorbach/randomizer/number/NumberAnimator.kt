package com.example.yaroslavgorbach.randomizer.number

import android.animation.*
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation
import kotlin.math.abs

class NumberAnimator {
    private val _mNumber: MutableLiveData<String> = MutableLiveData()
    private val mNumber: LiveData<String> = _mNumber
    private var mRepeatTimes = 0

    fun animateNumber(view: TextView, parent: View, minValue: Long, maxValue: Long, numberOfResults: Int){
        animate(view, parent, minValue, maxValue, numberOfResults)
    }

    fun getPreviousNumber(): LiveData<String>{
        return mNumber
    }

    private fun animate(view: TextView, parent: View, minValue: Long,
                        maxValue: Long, numberOfResults: Int ){
        val hide = ValueAnimator.ofFloat( -900f).apply {
            addUpdateListener {
                view.translationY = animatedValue as Float
                if((animatedValue as Float).toInt() == -900){
                    generateValue(view, minValue, maxValue, numberOfResults)
                }
            }
            disableViewDuringAnimation(parent)
        }
        val show = ValueAnimator.ofFloat(900f, 0f).apply {
            addUpdateListener {
                view.translationY = animatedValue as Float
            }
            disableViewDuringAnimation(parent)
        }

        AnimatorSet().apply {
            play(hide).before(show)
            duration = 300
            addListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (mRepeatTimes < 3){
                        mRepeatTimes += 1
                        start()
                    }else{
                        mRepeatTimes = 0
                        _mNumber.value = view.text.toString()
                        finishAnimation(view)
                    }
                }
            })
            interpolator = LinearInterpolator()
            start()
        }
    }

    private fun finishAnimation(view: TextView){
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -200f, 0f).apply {
            duration = 2000
            interpolator = OvershootInterpolator(1.1f)
            start()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun generateValue(textView: TextView, minValue: Long, maxValue: Long, numberOfResults: Int){
        var value: String = (minValue..maxValue).random().toString()
        for (i in 2..numberOfResults){
            value += ", ${(minValue..maxValue).random()}"
        }
        when(value.length){
            in 0..5 ->{
                textView.textSize = 100F
            }
            in 5..40 ->{
                textView.textSize = 50F
            }
            in 40..150 ->{
                textView.textSize = 30F
            }
            in 150..Long.MAX_VALUE ->{
                textView.textSize = 15F
            }
        }
        textView.text = value
    }
}