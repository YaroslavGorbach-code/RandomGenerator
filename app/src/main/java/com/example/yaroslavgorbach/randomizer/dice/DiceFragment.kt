package com.example.yaroslavgorbach.randomizer.dice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.dice.DiceFragmentArgs.fromBundle
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class DiceFragment : Fragment() {
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
        mDiceAnimator.inflateDice(mGrid, fromBundle(requireArguments()).numberOfDice, mAnimateAllDicesBt)

        mAnimateAllDicesBt.setOnClickListener{
            mDiceAnimator.animateAllDice(mAnimateAllDicesBt)
        }

        mDiceAnimator.getSum().observe(viewLifecycleOwner,{
            mToolbar.title ="Total: $it"
        })

        mToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }
}

