package com.example.yaroslavgorbach.randomizer.matches

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation

class MatchesAnimations {
    private val mMatches = mutableListOf<MatchModel>()

    private fun animateMatch(matchModel: MatchModel) {
        ValueAnimator.ofFloat(0f, -200f).apply {
            disableViewDuringAnimation(matchModel.imageView)
            addUpdateListener {
                if ((matchModel.imageView.translationY).toInt() != -200)
                matchModel.imageView.translationY = it.animatedValue as Float
                if ((it.animatedValue as Float).toInt() in -200..-150 && matchModel.isBurnt) {
                    matchModel.imageView.setImageResource(R.drawable.ic_match_burnt)
                }
            }
            duration = 500
            start()
        }
    }

    private fun refreshMatchAnimation(matchModel: MatchModel){
        ValueAnimator.ofFloat(matchModel.imageView.translationY, 0f).apply {
            disableViewDuringAnimation(matchModel.imageView)
            addUpdateListener {
                    matchModel.imageView.translationY = it.animatedValue as Float
                if ((it.animatedValue as Float).toInt() in 0..150) {
                    matchModel.imageView.setImageResource(R.drawable.ic_match)
                }
            }
            duration = 500
            start()
        }
    }

     private fun MutableList<MatchModel>.shuffle(){
        val listText = mutableListOf<Boolean>()
        for (i in this.indices){
            listText.add(mMatches[i].isBurnt)
        }
        listText.shuffle()

        for (i in listText.indices){
            mMatches[i].isBurnt = listText[i]
        }
    }


    fun inflateMatches(parent: ViewGroup, numberOfMatches: Int, numberOfBurnt: Int) {
        val inflater = LayoutInflater.from(parent.context)
        for (i in 0..numberOfMatches) {
            val itemV: View = inflater.inflate(R.layout.match_i, parent, false)
            val matchIv: ImageView = itemV.findViewById(R.id.match)
            val matchItem: MatchModel = if (numberOfBurnt > i) MatchModel(matchIv, true)
            else MatchModel(matchIv, false)


            itemV.setOnClickListener {
                animateMatch(matchItem)
            }


            mMatches.add(matchItem)
            parent.addView(itemV)
            mMatches.shuffle()
        }
    }
    fun refreshMatches() {
        for (i in mMatches.indices) {
        refreshMatchAnimation(mMatches[i])
        }
        mMatches.shuffle()
    }


}