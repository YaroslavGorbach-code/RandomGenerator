package com.example.yaroslavgorbach.randomizer.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.widget.Toolbar
import com.example.yaroslavgorbach.randomizer.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ListFragment : Fragment() {
    private lateinit var mLIstAnimator: AnimatorList
    private lateinit var mAnimateAllItems: ExtendedFloatingActionButton
    private lateinit var mGrid: GridLayout
    private lateinit var mToolbar: Toolbar
    private val mListOfItems: MutableList<String> = mutableListOf("item 1", "item 2", "item 3")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        mAnimateAllItems = view.findViewById(R.id.animateAllItems)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mGrid = view.findViewById(R.id.grid)
        mLIstAnimator = AnimatorList()
        mLIstAnimator.inflateItems(mGrid, mListOfItems)

        mAnimateAllItems.setOnClickListener{
            mLIstAnimator.rotateButton(mAnimateAllItems)
            mLIstAnimator.showItem()
        }

        return view
    }

}