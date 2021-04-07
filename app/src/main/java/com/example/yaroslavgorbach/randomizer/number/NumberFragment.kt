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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class NumberFragment : Fragment() {
    private lateinit var mNumberTv: TextView
    private lateinit var mNumberParent: ConstraintLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mRefreshNumber: ExtendedFloatingActionButton
    private var mMaxValue: Long = 10
    private var mMinValue: Long = 0
    private var mNumberOfResults: Int = 1
    private lateinit var mNumberAnimator: NumberAnimator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_number, container, false)
        mNumberTv = view.findViewById(R.id.number)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mRefreshNumber = view.findViewById(R.id.refreshNumber)
        mNumberParent = view.findViewById(R.id.numberParent)

        mNumberAnimator = NumberAnimator(requireContext())
        mMaxValue = NumberFragmentArgs.fromBundle(requireArguments()).maxValue
        mMinValue = NumberFragmentArgs.fromBundle(requireArguments()).minValue
        mNumberOfResults = NumberFragmentArgs.fromBundle(requireArguments()).numberOfResults
        mNumberAnimator.animateNumber(mNumberTv, mNumberParent, mRefreshNumber, mMinValue, mMaxValue, mNumberOfResults)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        mRefreshNumber.setOnClickListener { button ->
            mNumberAnimator.animateNumber(mNumberTv, mNumberParent, button, mMinValue, mMaxValue, mNumberOfResults)
        }

    }
}