package com.example.yaroslavgorbach.randomizer.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.list.Database.Database
import com.example.yaroslavgorbach.randomizer.list.Database.Repo
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import javax.inject.Inject

class ListFragment : Fragment() {
    private lateinit var mLIstAnimator: AnimatorList
    private lateinit var mAnimateAllItems: ExtendedFloatingActionButton
    private lateinit var mGrid: GridLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mParent: ConstraintLayout
    @Inject lateinit var mRepo: Repo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        mAnimateAllItems = view.findViewById(R.id.animateAllItems)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mGrid = view.findViewById(R.id.grid)
        mParent = view.findViewById(R.id.parentList)
        mLIstAnimator = AnimatorList(mParent, view.findViewById(R.id.finalItem))

        mToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        mRepo.getItemsByTitle(ListFragmentArgs.fromBundle(requireArguments()).listTitle).also {
            mLIstAnimator.inflateItems(mGrid, mAnimateAllItems, listOfItems = it)
        }

        mAnimateAllItems.setOnClickListener {
            mLIstAnimator.showResult(mAnimateAllItems)
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
