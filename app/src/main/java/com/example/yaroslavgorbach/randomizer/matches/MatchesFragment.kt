package com.example.yaroslavgorbach.randomizer.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.yaroslavgorbach.randomizer.R
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MatchesFragment : Fragment() {
    private lateinit var mParent: LinearLayout
    private lateinit var mRefreshMatchesButton: ExtendedFloatingActionButton
    private val mAnimator: MatchesAnimations = MatchesAnimations()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matches, container, false)

        mParent = view.findViewById(R.id.matchesParent)
        mRefreshMatchesButton = view.findViewById(R.id.refreshMatches)
        mAnimator.inflateMatches(mParent, 20, 10)

        mRefreshMatchesButton.setOnClickListener {
            mAnimator.refreshMatches()
        }

        return view
    }

}