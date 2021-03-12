package com.example.yaroslavgorbach.randomizer.number

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.example.yaroslavgorbach.randomizer.R

class NumberFragment : Fragment() {
    private lateinit var mNumberTv: TextView
    private lateinit var mNumberParent:FrameLayout
    private var mMaxValue: Long = 10
    private var mMinValue: Long = 0


    private val mNumberAnimator: NumberAnimator = NumberAnimator()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_number, container, false)
        mNumberTv = view.findViewById(R.id.number)
        mNumberParent = view.findViewById(R.id.numberParent)

        mMaxValue = NumberFragmentArgs.fromBundle(requireArguments()).maxValue
        mMinValue = NumberFragmentArgs.fromBundle(requireArguments()).minValue
        mNumberAnimator.animateNumber(mNumberTv, mNumberParent, mMinValue, mMaxValue)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNumberParent.setOnClickListener {
            mNumberAnimator.animateNumber(mNumberTv, mNumberParent, mMinValue, mMaxValue)
        }
    }
}