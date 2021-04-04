package com.example.yaroslavgorbach.randomizer.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.list.Database.Repo
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.*
import javax.inject.Inject

class ListFragment : Fragment() {
    private lateinit var mLIstAnimator: AnimatorList
    private lateinit var mAnimateAllItems: ExtendedFloatingActionButton
    private lateinit var mFinalTextView: TextView
    private lateinit var mFinalItem: ScrollView
    private lateinit var mGrid: GridLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mBackground: ConstraintLayout
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
        mBackground = view.findViewById(R.id.background)
        mFinalItem = view.findViewById(R.id.finalItem)
        mFinalTextView = view.findViewById(R.id.finalText)
        mLIstAnimator = AnimatorList(mBackground, mFinalItem, mFinalTextView)

        mToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

       GlobalScope.launch {
            val result = async {
                mRepo.getItemsByTitle(ListFragmentArgs.fromBundle(requireArguments()).listTitle)
            }
            withContext(Dispatchers.Main) {
                mLIstAnimator.inflateItems(mGrid, mAnimateAllItems, listOfItems = result.await())
            }
        }

        mAnimateAllItems.setOnClickListener {
            mLIstAnimator.showResult(mAnimateAllItems)
        }

        mBackground.setOnClickListener {
            mLIstAnimator.hideFinalItem()
        }

        mGrid.setOnClickListener {
            mLIstAnimator.hideFinalItem()
        }
        return view
    }

}
