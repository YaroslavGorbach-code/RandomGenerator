package com.app.yaroslavgorbach.randomizer.screen.number

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.TextView
import androidx.core.animation.doOnStart
import com.app.yaroslavgorbach.randomizer.databinding.FragmentNumberBinding
import com.app.yaroslavgorbach.randomizer.feature.SoundManager

class NumberView(
    private val binding: FragmentNumberBinding,
    private val soundManager: SoundManager,
    private val callback: Callback
) {
    interface Callback {
        fun onSoundDisallow()
        fun onBack()
        fun onSoundAllow()
        fun onGenerateValue()
    }

    private var soundIsAllow: Boolean = false

    init {
        binding.toolbar.setOnMenuItemClickListener {
            if (soundIsAllow) callback.onSoundDisallow()
            else callback.onSoundAllow()
            true
        }
        binding.toolbar.setNavigationOnClickListener { callback.onBack() }
        binding.animate.setOnClickListener { animate() }
        callback.onGenerateValue()
        animate()
    }

    fun setSoundIsAllow(isAllow: Boolean) {
        soundIsAllow = isAllow
        if (isAllow) binding.toolbar.setIconMusicOn()
        else binding.toolbar.setIconMusicOff()
    }

    fun setValue(value: String) {
        binding.number.text = value
        binding.number.setValueTextSize(value.length)
    }

    private fun animate() {
        val hide = ValueAnimator.ofFloat(-binding.root.context.getScreenHeight().toFloat()).apply {
            doOnStart {
                Handler(Looper.getMainLooper()).postDelayed({ soundManager.numberSwipeSoundPlay() }, 100)
            }
            addUpdateListener {
                binding.numberParent.translationX = animatedValue as Float
                if ((animatedValue as Float).toInt() == -binding.root.context.getScreenHeight()) {
                    callback.onGenerateValue()
                }
            }
        }

        val show = ValueAnimator.ofFloat(binding.root.context.getScreenHeight().toFloat(), 0f).apply {
            doOnStart { soundManager.numberSwipeSoundPlay() }
                addUpdateListener {
                    binding.numberParent.translationX = animatedValue as Float
                }
            }

        val scaleX = ObjectAnimator.ofFloat(binding.numberParent, View.SCALE_X, 1f, 0.6f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }
        val scaleY = ObjectAnimator.ofFloat(binding.numberParent, View.SCALE_Y, 1f, 0.6f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }

        val scaleSet = AnimatorSet().apply { playTogether(scaleX, scaleY) }


        AnimatorSet().apply {
            play(hide).before(show).with(scaleSet)
            duration = 500
            interpolator = AnticipateOvershootInterpolator()
            start()
        }
        animateButton()
    }
    private fun animateButton() {
        ObjectAnimator.ofFloat(binding.animate, View.ROTATION, -360f, 0f).apply {
            duration = 1000
            interpolator = AnticipateOvershootInterpolator()
            disableViewDuringAnimation(binding.animate)
            start()
        }
    }

    private fun TextView.setValueTextSize(length: Int) {
        when (length) {
            in 0..5 -> {
                textSize = 100F
            }
            in 5..40 -> {
                textSize = 50F
            }
            in 40..150 -> {
                textSize = 30F
            }
            in 150..Long.MAX_VALUE -> {
                textSize = 22F
            }
        }
    }
}