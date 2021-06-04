package com.app.yaroslavgorbach.randomizer.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View

fun ValueAnimator.disableViewDuringAnimation(view: View) {
    this.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            view.isClickable = false
            view.isActivated = false
        }

        override fun onAnimationEnd(animation: Animator?) {
            view.isClickable = true
            view.isActivated = true
        }
    })
}
