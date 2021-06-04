package com.app.yaroslavgorbach.randomizer.screen.matches

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import com.app.yaroslavgorbach.randomizer.R
import com.app.yaroslavgorbach.randomizer.databinding.FragmentMatchesBinding
import com.app.yaroslavgorbach.randomizer.feature.SoundManager
import com.app.yaroslavgorbach.randomizer.util.disableViewDuringAnimation

class MatchesView(
    private val binding: FragmentMatchesBinding,
    numberMatches: Int,
    numberBurnt: Int,
    private val soundManager: SoundManager,
    val callback: Callback
) {
    interface Callback {
        fun onBack()
        fun onSoundDisallow()
        fun onSoundAllow()
    }

    data class MatchModel(val imageView: ImageView, var isBurned: Boolean)
    private val mMatches = mutableListOf<MatchModel>()
    private var soundIsAllow: Boolean = false

    init {
        val inflater = LayoutInflater.from(binding.root.context)
        for (i in 1..numberMatches) {
            val itemV: View = inflater.inflate(R.layout.item_match, binding.matchesParent, false)
            val matchIv: ImageView = itemV.findViewById(R.id.match)
            val matchItem = MatchModel(matchIv, numberBurnt >= i)

            itemV.setOnClickListener {
                animateMatch(matchItem)
            }

            mMatches.add(matchItem)
            binding.matchesParent.addView(itemV)
            mMatches.shuffle()
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (soundIsAllow) callback.onSoundDisallow()
            else callback.onSoundAllow()
            true
        }
        binding.toolbar.setNavigationOnClickListener { callback.onBack() }
        binding.animate.setOnClickListener(this::refreshMatches)
    }

    private fun animateMatch(match: MatchModel) {
        ValueAnimator.ofFloat(0f, -200f).apply {
            disableViewDuringAnimation(match.imageView)
            addUpdateListener {
                if ((match.imageView.translationY).toInt() != -200)
                    match.imageView.translationY = it.animatedValue as Float

                if ((it.animatedValue as Float).toInt() in -200..-150 && match.isBurned) {
                    match.imageView.setImageResource(R.drawable.ic_match_burned)
                    soundManager.matchSoundPlay()
                }
            }
            duration = 500
            start()
        }
    }

    fun setSoundIsAllow(isAllow: Boolean) {
        soundIsAllow = isAllow
        if (isAllow) binding.toolbar.setIconMusicOn()
        else binding.toolbar.setIconMusicOff()
    }

    private fun refreshMatch(matchModel: MatchModel) {
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

    private fun rotateButton(view: View) {
        ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 0f).apply {
            duration = 500
            interpolator = AccelerateDecelerateInterpolator()
            disableViewDuringAnimation(view)
            start()
        }
    }

    private fun refreshMatches(button: View) {
        rotateButton(button)
        for (i in mMatches.indices) {
            refreshMatch(mMatches[i])
        }
        mMatches.shuffle()
    }

    private fun MutableList<MatchModel>.shuffle() {
        val listText = mutableListOf<Boolean>()
        for (i in this.indices) {
            listText.add(mMatches[i].isBurned)
        }
        listText.shuffle()

        for (i in listText.indices) {
            mMatches[i].isBurned = listText[i]
        }
    }

}