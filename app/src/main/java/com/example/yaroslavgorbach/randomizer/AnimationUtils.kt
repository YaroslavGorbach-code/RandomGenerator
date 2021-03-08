package com.example.yaroslavgorbach.randomizer

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View

fun ValueAnimator.disableViewDuringAnimation(view: View) {
    this.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            view.isClickable = false
        }

        override fun onAnimationEnd(animation: Animator?) {
            view.isClickable = true
        }
    })
}