package com.app.yaroslavgorbach.randomizer.screen.list

import android.animation.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi
import com.app.yaroslavgorbach.randomizer.util.disableViewDuringAnimation

class ListAnimator {
    interface Callback {
        fun showItemText()
        fun hideItemText()
        fun showResult()
    }

    fun rotateItem(view: View, callback: Callback) {
        val rotateY = ValueAnimator.ofFloat(-180f, 0f).apply {
            disableViewDuringAnimation(view)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt()) {
                    -135 -> callback.hideItemText()
                    in -90..0 -> callback.showItemText()
                }
            }
        }

        val rotateX = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 360f, 0f).apply {
            disableViewDuringAnimation(view)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt()) {
                    in 265..275 -> callback.hideItemText()
                    in 85..95 -> callback.showResult()
                }
            }
        }

        AnimatorSet().apply {
            playSequentially(rotateY, rotateX)
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    fun hideItem(view: View, onHideItem: () -> Unit) {
        val rotateY = ValueAnimator.ofFloat(-180f, 0f).apply {
            disableViewDuringAnimation(view)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt()) {
                    in -90..0 -> onHideItem()
                }
            }
        }


        AnimatorSet().apply {
            play(rotateY)
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }


    fun showFinalItem(view: View, onAnimationStart: () -> Unit) {
        onAnimationStart()
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 1f)
        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 300
            start()
        }
    }

    fun hideFinalItemAnimation(view: View, onAnimationEnd: () -> Unit) {
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 0f)
        AnimatorSet().apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    onAnimationEnd()
                }
            })
            playTogether(scaleX, scaleY)
            duration = 300
            start()
        }
    }

    fun rotateButton(view: View) {
        ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            disableViewDuringAnimation(view)
            start()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setDarkForeground(view: View) {
        ValueAnimator.ofInt(0, 200).apply {
            addUpdateListener { animator ->
                view.foreground =
                    (ColorDrawable(Color.argb(animator.animatedValue as Int, 0, 0, 0)))
            }
            disableViewDuringAnimation(view)
            duration = 300
            start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setLightForeground(view: View) {
        ValueAnimator.ofInt(200, 0).apply {
            addUpdateListener { animator ->
                view.foreground =
                    (ColorDrawable(Color.argb(animator.animatedValue as Int, 0, 0, 0)))
            }
            disableViewDuringAnimation(view)
            duration = 300
            start()
        }
    }
}

