package com.example.yaroslavgorbach.randomizer.component.dice

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.util.disableViewDuringAnimation
import com.example.yaroslavgorbach.randomizer.feature.SoundManager

class DiceImp(private val soundManager: SoundManager, private val repo: Repo) : Dice {
    data class DiceModel(
        var imageView: ImageView,
        var points: Int,
        var sideIsChanged: Boolean = false
    )

    private val mDices = mutableListOf<DiceModel>()
    private val mSum: MutableLiveData<Int> = MutableLiveData(0)
    private val diceSound = MutableLiveData(repo.getDiceSoundIsAllow())

    override val sum: LiveData<Int> = mSum
    override fun animateAll() {
        mDices.forEach { rotateDice(it) }
    }
    override fun animate(dice: DiceModel) {
        rotateDice(dice)
    }

    override fun animateButton(view: View) {
        rotateButton(view)
    }

    override fun onDiceInflated(dice: DiceModel) {
        mSum.value = mSum.value!! + dice.points
        mDices.add(dice)
    }

    override fun getSoundIsAllow(): LiveData<Boolean> = diceSound

    override fun disallowSound() {
        repo.setDiceSoundIsAllow(false)
        diceSound.value = false
    }

    override fun allowSound() {
        repo.setDiceSoundIsAllow(true)
        diceSound.value = true
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
            disableViewDuringAnimation(dice.imageView)
            start()
        }
    }

    private fun rotateButton(view: View) {
        val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(view)
        mDices.forEach { rotate.disableViewDuringAnimation(it.imageView) }
        rotate.start()
    }
}