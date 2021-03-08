package com.example.yaroslavgorbach.randomizer.dice

import android.animation.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.GridLayout
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R

class DicesFragment : Fragment() {
    private lateinit var mImageView: ImageView
    private lateinit var mGrid: GridLayout
    private lateinit var mDiceAnimator: AnimateDice

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dices, container, false)
        mGrid = view.findViewById(R.id.grid)
        mDiceAnimator = AnimateDice()
        mDiceAnimator.inflateDices(mGrid, 12)
        return view


    }



    private fun translater() {
        val animator = ObjectAnimator.ofFloat(mImageView, View.TRANSLATION_X,
            -600f)
        animator.repeatCount = 1
        animator.duration = 1000
        animator.interpolator = AnticipateOvershootInterpolator(0.0f, 0.0f)
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }
}

