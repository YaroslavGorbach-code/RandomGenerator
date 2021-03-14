package com.example.yaroslavgorbach.randomizer.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yaroslavgorbach.randomizer.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ListFragment : Fragment() {
    private lateinit var mLIstAnimator: AnimatorList
    private lateinit var mAnimateAllItems: ExtendedFloatingActionButton
    private lateinit var mGrid: GridLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mParent: ConstraintLayout
    private val mListOfItems: MutableList<String> = mutableListOf("item 1", "item 2", "item 3",
            "item 1", "item 2", "item 3", "item 1", "item 2", "item 3")

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        mAnimateAllItems = view.findViewById(R.id.animateAllItems)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mGrid = view.findViewById(R.id.grid)
        mParent = view.findViewById(R.id.parentList)
        mLIstAnimator = AnimatorList(mParent, view.findViewById(R.id.finalItem))
        mLIstAnimator.inflateItems(mGrid, mListOfItems)

        mAnimateAllItems.setOnClickListener {
            mLIstAnimator.rotateButton(mAnimateAllItems)
            mLIstAnimator.showItem()
        }

        mParent.setOnClickListener {
            mLIstAnimator.hideFinalItem()
        }

        mGrid.setOnClickListener {
            mLIstAnimator.hideFinalItem()
        }
        return view
    }
}
