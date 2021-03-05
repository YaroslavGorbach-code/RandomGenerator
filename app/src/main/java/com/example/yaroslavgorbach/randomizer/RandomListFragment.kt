package com.example.yaroslavgorbach.randomizer

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class RandomListFragment : Fragment() {
    lateinit var mStartDice_bt: MaterialButton
    lateinit var mStartCoin_bt: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_random_list, container, false)
        mStartDice_bt = view.findViewById(R.id.dice)
        mStartCoin_bt = view.findViewById(R.id.coin)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStartDice_bt.setOnClickListener {
            findNavController().navigate(RandomListFragmentDirections.actionRandomListFragmentToDicesFragment())
        }
        mStartCoin_bt.setOnClickListener{
            findNavController().navigate(RandomListFragmentDirections.actionRandomListFragmentToCoinFragment())
        }

    }

}