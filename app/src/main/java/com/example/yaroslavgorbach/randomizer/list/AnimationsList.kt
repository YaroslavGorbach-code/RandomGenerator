package com.example.yaroslavgorbach.randomizer.list

import android.animation.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.RequiresApi
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation

class AnimationsList {
    fun manuallyShowItemRotate(view: View, button: View, listAnimationsManuallyListener: ListAnimationsManuallyListener) {
        val rotateY = ValueAnimator.ofFloat( -740f, 0f).apply {
            disableViewDuringAnimation(view)
            disableViewDuringAnimation(button)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt()){
                    -740 -> listAnimationsManuallyListener.hideItemText()
                    in -150..0 -> listAnimationsManuallyListener.showItemText()
                }
            }
        }

        val rotateX = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 740f, 0f).apply {
            disableViewDuringAnimation(view)
            disableViewDuringAnimation(button)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt() ){
                    in 500..590 -> listAnimationsManuallyListener.hideItemText()
                    in 0..120 -> listAnimationsManuallyListener.showResult()
                }
            }
        }

        AnimatorSet().apply {
            playSequentially(rotateY, rotateX)
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    fun autoShowItemRotate(view: View, button: View, listAnimationsRandom: ListAnimationsRandom) {

        val rotateY = ValueAnimator.ofFloat( -740f, 0f).apply {
            disableViewDuringAnimation(view)
            disableViewDuringAnimation(button)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt()){
                    in -640..-620 -> listAnimationsRandom.hideAllItems()
                    in -150..0 -> listAnimationsRandom.showAllItemsText()

                }
            }
        }

        val rotateX = ObjectAnimator.ofFloat(view, View.ROTATION_Y, 740f, 0f).apply {
            disableViewDuringAnimation(view)
            disableViewDuringAnimation(button)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt() ){
                    in 500..590 -> listAnimationsRandom.hideAllItemsText()
                    in 0..120 -> listAnimationsRandom.showResult()
                }
            }
        }

        AnimatorSet().apply {
            playSequentially(rotateY, rotateX)
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }

    fun hideItem(view: View, onHideItem: () -> Unit) {
        val rotateY = ValueAnimator.ofFloat( -360f, 0f).apply {
            disableViewDuringAnimation(view)
            addUpdateListener { animation ->
                view.rotationY = animation.animatedValue as Float
                when ((animation.animatedValue as Float).toInt()){
                    in -250..-200 -> onHideItem()
                }
            }
        }


        AnimatorSet().apply {
            play(rotateY)
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }



     fun showFinalItemAnimation(view: View, onAnimationStart: () -> Unit) {
         onAnimationStart()
         val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 1f)
         val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 1f)
        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 300
            start()
        }
    }

    fun hideFinalItemAnimation(view: View, onAnimationEnd: () -> Unit){
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

    fun buttonRotateAnimation(view: View){
            val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, -720f, 0f).apply {
                repeatCount = 1
                repeatMode = ObjectAnimator.REVERSE
            }
            rotate.duration = 700
            rotate.interpolator = AccelerateDecelerateInterpolator()
            rotate.disableViewDuringAnimation(view)
            rotate.start()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setDarkForeground(view: View){
            ValueAnimator.ofInt(0, 200).apply {
                addUpdateListener { animator ->
                    view.foreground = (ColorDrawable(Color.argb(animator.animatedValue as Int, 0, 0, 0)))
                }
                duration = 300
                start()
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setLightForeground(view: View){
        ValueAnimator.ofInt(200, 0).apply {
            addUpdateListener { animator ->
                view.foreground = (ColorDrawable(Color.argb(animator.animatedValue as Int, 0, 0, 0)))
            }
            duration = 300
            start()
        }
    }
}


