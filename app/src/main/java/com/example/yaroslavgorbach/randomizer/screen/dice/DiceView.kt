package com.example.yaroslavgorbach.randomizer.screen.dice

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.component.dice.DiceImp
import com.example.yaroslavgorbach.randomizer.databinding.FragmentDicesBinding
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.util.disableViewDuringAnimation
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn

class DiceView(
    private val binding: FragmentDicesBinding,
    number: Int,
    private val soundManager: SoundManager,
    callback: Callback
) {
    interface Callback {
        fun onBack()
        fun onSoundDisallow()
        fun onSoundAllow()
    }

    data class DiceModel(
        var imageView: ImageView,
        var points: Int,
        var sideIsChanged: Boolean = false
    )

    private val mDices = mutableListOf<DiceModel>()
    private val mSum: MutableLiveData<Int> = MutableLiveData(0)
    private var soundIsAllow: Boolean = false

    init {
        val inflater = LayoutInflater.from(binding.root.context)
        for (i in 1..number) {
            val item: View = inflater.inflate(R.layout.item_dice, binding.grid, false)
            val diceImage: ImageView = item.findViewById(R.id.dice)
            val dice = DiceModel(diceImage, 0)
            item.setOnClickListener { rotateDice(dice)}
            mSum.value = mSum.value!! + dice.points
            mDices.add(dice)
            binding.grid.addView(item)
        }
        binding.toolbar.setOnMenuItemClickListener {
            if (soundIsAllow) callback.onSoundDisallow()
            else callback.onSoundAllow()
            true
        }
        binding.toolbar.setNavigationOnClickListener { callback.onBack() }
        binding.animate.setOnClickListener { animateAll() }
        animateAll()
    }

    private fun animateAll() {
        mDices.forEach { rotateDice(it) }
        rotateButton(binding.animate)
    }

    fun setSoundIsAllow(isAllow: Boolean) {
        soundIsAllow = isAllow
        if (isAllow) binding.toolbar.setIconMusicOn()
        else binding.toolbar.setIconMusicOff()
    }

    private fun rotateDice(dice: DiceModel) {
        soundManager.rollAllDicesSoundPlay()
        ValueAnimator.ofFloat(-740f, 0f).apply {

            addUpdateListener {
                dice.imageView.rotationY = it.animatedValue as Float

                if ((it.animatedValue as Float).toInt() in -80..0) {
                    changeSide(dice)
                }

                doOnEnd {
                    dice.sideIsChanged = false
                }
            }

            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            mDices.forEach { disableViewDuringAnimation(it.imageView) }
            start()
        }
    }

    private fun changeSide(dice: DiceModel) {
        if (!dice.sideIsChanged) {
            mSum.value = mSum.value!! - dice.points
            when ((1..6).random()) {
                1 -> {
                    dice.imageView.setImageResource(R.drawable.ic_dice_1)
                    dice.points = 1
                    mSum.value = mSum.value!! + 1
                    dice.sideIsChanged = true
                }
                2 -> {
                    dice.imageView.setImageResource(R.drawable.ic_dice_2)
                    dice.points = 2
                    mSum.value = mSum.value!! + 2
                    dice.sideIsChanged = true
                }
                3 -> {
                    dice.imageView.setImageResource(R.drawable.ic_dice_3)
                    dice.points = 3
                    mSum.value = mSum.value!! + 3
                    dice.sideIsChanged = true
                }
                4 -> {
                    dice.imageView.setImageResource(R.drawable.ic_dice_4)
                    dice.points = 4
                    mSum.value = mSum.value!! + 4
                    dice.sideIsChanged = true
                }
                5 -> {
                    dice.imageView.setImageResource(R.drawable.ic_dice_5)
                    dice.points = 5
                    mSum.value = mSum.value!! + 5
                    dice.sideIsChanged = true
                }
                6 -> {
                    dice.imageView.setImageResource(R.drawable.ic_dice_6)
                    dice.points = 6
                    mSum.value = mSum.value!! + 6
                    dice.sideIsChanged = true
                }

            }
        }
    }

    private fun rotateButton(view: View) {
        val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(view)
        rotate.start()
    }
}