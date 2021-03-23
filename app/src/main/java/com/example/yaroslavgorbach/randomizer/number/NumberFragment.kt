package com.example.yaroslavgorbach.randomizer.number

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.yaroslavgorbach.randomizer.R
import com.google.android.material.appbar.MaterialToolbar

class NumberFragment : Fragment() {
    private lateinit var mNumberTv: TextView
    private lateinit var mNumberParent: FrameLayout
    private lateinit var mPreviousNumber: TextView
    private lateinit var mToolbar: Toolbar
    private var mMaxValue: Long = 10
    private var mMinValue: Long = 0
    private var mNumberOfResults: Int = 1


    private val mNumberAnimator: NumberAnimator = NumberAnimator()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_number, container, false)
        mNumberTv = view.findViewById(R.id.number)
        mNumberParent = view.findViewById(R.id.numberParent)
        mPreviousNumber = view.findViewById(R.id.previousNumberTv)
        mToolbar = view.findViewById(R.id.materialToolbar)
       // mNumberTv.movementMethod = ScrollingMovementMethod()

        mMaxValue = NumberFragmentArgs.fromBundle(requireArguments()).maxValue
        mMinValue = NumberFragmentArgs.fromBundle(requireArguments()).minValue
        mNumberOfResults = NumberFragmentArgs.fromBundle(requireArguments()).numberOfResults
        mNumberAnimator.animateNumber(mNumberTv, mNumberParent, mMinValue, mMaxValue, mNumberOfResults)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        mNumberParent.setOnClickListener {
            mNumberAnimator.animateNumber(mNumberTv, mNumberParent, mMinValue, mMaxValue, mNumberOfResults)
        }

        mNumberAnimator.getPreviousNumber().observe(viewLifecycleOwner, {
            if (mPreviousNumber.text.isEmpty()){
                mPreviousNumber.text = it
            }else{
                mPreviousNumber.text = "$it, ${mPreviousNumber.text}"
            }
        })
    }
}