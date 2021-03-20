package com.example.yaroslavgorbach.randomizer.dice

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation

class AnimatorDice{
    private val mDices = mutableListOf<DiceModel>()
    private val _mSum: MutableLiveData<Int> = MutableLiveData(0)
    private val mSum: LiveData<Int> = _mSum

    private fun animateDice(dice: DiceModel, rotateAllDice: View) {
        rotateDice(dice.imageView, rotateAllDice)
        _mSum.value  = _mSum.value!! - dice.points
        when ((1..6).random()) {
            1 -> {
                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_1)
                    _mSum.value  = _mSum.value!! +1
                    dice.points = 1}, 500)
            }
            2 -> {
                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_2)
                    _mSum.value  = _mSum.value!! +2
                    dice.points = 2}, 500)
            }
            3 -> {
                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_3)
                    _mSum.value  = _mSum.value!! +3
                    dice.points = 3}, 500)
            }
            4 -> {
                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_4)
                    _mSum.value  = _mSum.value!! +4
                    dice.points = 4}, 500)
            }
            5 -> {
                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_5)
                    _mSum.value  = _mSum.value!! +5
                    dice.points = 5}, 500)
            }
            6 -> {
                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_6)
                    _mSum.value  = _mSum.value!! +6
                    dice.points = 6}, 500)
            }
        }
    }

    private fun rotateDice(dice: ImageView, rotateAllDice: View) {
        val rotate = ObjectAnimator.ofFloat(dice, View.ROTATION_Y, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(dice)
        rotate.disableViewDuringAnimation(rotateAllDice)
        rotate.start()
    }

    private fun rotateButton(view: View){
        val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(view)
        rotate.start()
    }

    private fun rotateAllDice(rotateAllDice: View){
        for (i in mDices.indices){
            rotateDice(mDices[i].imageView, rotateAllDice)
        }
    }

    fun inflateDice(parent: ViewGroup, number: Int, rotateAllDice: View) {
        val inflater = LayoutInflater.from(parent.context)
        for (i in 1..number) {
            val item: View = inflater.inflate(R.layout.dice_i, parent, false)
            val diceImage: ImageView = item.findViewById(R.id.dice_i)
            var dice = DiceModel(diceImage,0)
            when (i) {
                1, 7, 13, 19, 25, 31, 37, 43, 49 -> {
                    diceImage.setImageResource(R.drawable.ic_dice_1)
                    dice = DiceModel(diceImage, 1)
                }
                2, 8, 14, 20, 26, 32, 38, 44, 50 -> {
                    diceImage.setImageResource(R.drawable.ic_dice_2)
                    dice = DiceModel(diceImage, 2)
                }
                3, 9, 15, 21, 27, 33, 39, 45 -> {
                    diceImage.setImageResource(R.drawable.ic_dice_3)
                    dice = DiceModel(diceImage, 3)
                }
                4, 10, 16, 22, 28, 34, 40, 46 -> {
                    diceImage.setImageResource(R.drawable.ic_dice_4)
                    dice = DiceModel(diceImage, 4)
                }
                5, 11, 17, 23, 29, 35, 41, 47 -> {
                    diceImage.setImageResource(R.drawable.ic_dice_5)
                    dice = DiceModel(diceImage, 5)
                }
                6, 12, 18, 24, 30, 36, 42, 48 -> {
                    diceImage.setImageResource(R.drawable.ic_dice_6)
                    dice = DiceModel(diceImage, 6)
                }
            }

            item.setOnClickListener { v: View? -> animateDice(dice, rotateAllDice) }
            _mSum.value = _mSum.value!! + dice.points
            mDices.add(dice)
            parent.addView(item)
        }
        rotateAllDice(rotateAllDice)
    }

    fun animateAllDice(rotateAllDice: View) {
        for (i in mDices.indices){
            animateDice(mDices[i], rotateAllDice)
        }
        rotateButton(rotateAllDice)
    }

    fun getSum(): LiveData<Int>{
        return mSum
    }

}





