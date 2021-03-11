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
    private val mNumberAnimator: NumberAnimator = NumberAnimator()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_number, container, false)
        mNumberTv = view.findViewById(R.id.number)
        mNumberParent = view.findViewById(R.id.numberParent)
        mNumberAnimator.animateNumber(mNumberTv, mNumberParent)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNumberParent.setOnClickListener {
            mNumberAnimator.animateNumber(mNumberTv, mNumberParent)
        }
    }
}