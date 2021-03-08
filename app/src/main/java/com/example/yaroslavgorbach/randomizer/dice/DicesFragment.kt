package com.example.yaroslavgorbach.randomizer.dice

import android.animation.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.yaroslavgorbach.randomizer.R

class DicesFragment : Fragment() {
    private lateinit var mImageView: ImageView
    private lateinit var mRecyclerOfDices: RecyclerView
    private lateinit var mDiceAnimator: AnimatorDice


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dices, container, false)
        mRecyclerOfDices = view.findViewById(R.id.listOfDices)
        mDiceAnimator = AnimatorDice()
        var numberOfDices = 30
        val adapter = DicesAdapter { diceImage ->
            mDiceAnimator.animateDice(diceImage)
        }
        //mDiceAnimator.inflateDices(mGrid, 30)
        adapter.setNumberOfDices(numberOfDices)
        mRecyclerOfDices.apply {
            this.adapter = adapter
            layoutManager = StaggeredGridLayoutManager(if (numberOfDices > 1) 2 else 1 , StaggeredGridLayoutManager.VERTICAL)
            return view


        }
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

