package com.example.yaroslavgorbach.randomizer.number

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.TextView
import androidx.core.animation.doOnStart
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation
import com.example.yaroslavgorbach.randomizer.sounds.SoundManager
import kotlinx.coroutines.delay


class NumberAnimator(soundManager: SoundManager) {
    private val mSoundManager = soundManager

    fun animateNumber(view: TextView, parent: ViewGroup, button: View, minValue: Long, maxValue: Long, numberOfResults: Int){
        animate(view, parent, minValue, maxValue, numberOfResults)
        animateButton(button)
    }

    private fun animateButton(button: View) {
        ObjectAnimator.ofFloat(button, View.ROTATION, -360f, 0f).apply {
            duration = 1000
            interpolator = AnticipateOvershootInterpolator()
            disableViewDuringAnimation(button)
            start()
        }
    }


    private fun animate(view: TextView, parent: ViewGroup, minValue: Long,
                        maxValue: Long, numberOfResults: Int ){
        val hide = ValueAnimator.ofFloat( -NumberUtils.getScreenHeight(view.context).toFloat()).apply {
            doOnStart {
            Handler(Looper.getMainLooper()).postDelayed({ mSoundManager.numberSwipeSoundPlay() }, 100)
            }
            addUpdateListener {
                parent.translationX = animatedValue as Float
                if((animatedValue as Float).toInt() == -NumberUtils.getScreenHeight(view.context)){
                    generateValue(view, minValue, maxValue, numberOfResults)
                }
            }
        }

        val show = ValueAnimator.ofFloat(NumberUtils.getScreenHeight(view.context).toFloat(), 0f).apply {
            doOnStart { mSoundManager.numberSwipeSoundPlay() }
            addUpdateListener {
                parent.translationX = animatedValue as Float
            }
        }

        val scaleX = ObjectAnimator.ofFloat(parent, View.SCALE_X, 1f, 0.6f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }
        val scaleY = ObjectAnimator.ofFloat(parent, View.SCALE_Y, 1f, 0.6f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleSet = AnimatorSet().apply {playTogether(scaleX, scaleY)}


        AnimatorSet().apply {
            play(hide).before(show).with(scaleSet)
            duration = 500
            interpolator = AnticipateOvershootInterpolator()
            start()
        }
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
                textView.textSize = 22F
            }
        }
        textView.text = value
    }
