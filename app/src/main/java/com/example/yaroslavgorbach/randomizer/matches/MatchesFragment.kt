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

class MatchesFragment : Fragment() {
    private lateinit var mParent: LinearLayout
    private val mAnimator: MatchesAnimations = MatchesAnimations()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_matches, container, false)

        mParent = view.findViewById(R.id.matchesParent)
        mAnimator.inflateMatches(mParent, 10, 1)

        return view
    }

}