package com.example.yaroslavgorbach.randomizer.dice

import android.animation.*
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class DicesFragment : Fragment() {
    private lateinit var mDiceAnimator: AnimatorDice
    private lateinit var mAnimateAllDicesBt: ExtendedFloatingActionButton
    private lateinit var mGrid: GridLayout
    private lateinit var mToolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dices, container, false)
        mAnimateAllDicesBt = view.findViewById(R.id.animateAllDices)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mGrid = view.findViewById(R.id.grid)
        mDiceAnimator = AnimatorDice()
        var numberOfDices = 6
        mDiceAnimator.inflateDices(mGrid, numberOfDices)

        mAnimateAllDicesBt.setOnClickListener{
            mDiceAnimator.animateAllDices()
            mDiceAnimator.rotateButton(it)
        }

        mDiceAnimator.getSum().observe(viewLifecycleOwner,{
            mToolbar.title = it.toString()
        })

        return view
    }
}

