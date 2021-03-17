package com.example.yaroslavgorbach.randomizer.matches

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.list.ListItemModel

class MatchesAnimations {

    private val mMatches = mutableListOf<MatchModel>()

   private fun animateMatch(view: View){
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, -200f).apply {
            duration = 1000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 1
            start()
        }
    }

    fun inflateMatches(parent: ViewGroup, numberOfMatches: Int, numberOfBurnt: Int) {
        val inflater = LayoutInflater.from(parent.context)
        for (i in 0..numberOfMatches) {
            val itemV: View = inflater.inflate(R.layout.match_i, parent, false)
            val matchIv: ImageView = itemV.findViewById(R.id.match)
            val matchItem = MatchModel(matchIv, false)

            itemV.setOnClickListener {
                animateMatch(matchIv)
            }
            mMatches.add(matchItem)
            parent.addView(itemV)
        }
    }
}