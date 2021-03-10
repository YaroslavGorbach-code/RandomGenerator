package com.example.yaroslavgorbach.randomizer.dice

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation

class AnimatorDice {
    private val mDices = mutableListOf<ImageView>()

    fun inflateDices(parent: ViewGroup, number: Int) {
        val inflater = LayoutInflater.from(parent.context)
        for (i in 1..number) {
            val item: View = inflater.inflate(R.layout.dice_i, parent, false)
            val diceImage: ImageView = item.findViewById(R.id.dice_i)
            when (i) {
                1, 7, 13, 19, 25 -> diceImage.setImageResource(R.drawable.ic_dice_1)
                2, 8, 14, 20, 26 -> diceImage.setImageResource(R.drawable.ic_dice_2)
                3, 9, 15, 21, 27 -> diceImage.setImageResource(R.drawable.ic_dice_3)
                4, 10, 16, 22, 28 -> diceImage.setImageResource(R.drawable.ic_dice_4)
                5, 11, 17, 23, 29 -> diceImage.setImageResource(R.drawable.ic_dice_5)
                6, 12, 18, 24, 30 -> diceImage.setImageResource(R.drawable.ic_dice_6)
            }

            item.setOnClickListener { v: View? -> animateDice(diceImage) }
            mDices.add(diceImage)
            parent.addView(item)
        }
    }

    fun animateAllDices() {
        for (i in mDices.indices)
            when ((1..6).random()) {
                1 -> {
                    rotateDice(mDices[i])
                    android.os.Handler().postDelayed({ mDices[i].setImageResource(R.drawable.ic_dice_1) }, 600)
                }
                2 -> {
                    rotateDice(mDices[i])
                    android.os.Handler().postDelayed({ mDices[i].setImageResource(R.drawable.ic_dice_2) }, 600)
                }
                3 -> {
                    rotateDice(mDices[i])
                    android.os.Handler().postDelayed({ mDices[i].setImageResource(R.drawable.ic_dice_3) }, 600)
                }
                4 -> {
                    rotateDice(mDices[i])
                    android.os.Handler().postDelayed({ mDices[i].setImageResource(R.drawable.ic_dice_4) }, 600)
                }
                5 -> {
                    rotateDice(mDices[i])
                    android.os.Handler().postDelayed({ mDices[i].setImageResource(R.drawable.ic_dice_5) }, 600)
                }
                6 -> {
                    rotateDice(mDices[i])
                    android.os.Handler().postDelayed({ mDices[i].setImageResource(R.drawable.ic_dice_6) }, 600)
                }
            }
    }

    fun rotateButton(view: View){
        val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(view)
        rotate.start()

    }

    private fun rotateDice(dice: ImageView) {
        val rotate = ObjectAnimator.ofFloat(dice, View.ROTATION_Y, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(dice)
        rotate.start()
    }

    private fun animateDice(image: ImageView) {
        rotateDice(image)
        when ((1..6).random()) {
            1 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_1) }, 600)
            }
            2 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_2) }, 600)
            }
            3 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_3) }, 600)
            }
            4 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_4) }, 600)
            }
            5 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_5) }, 600)
            }
            6 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_6) }, 600)
            }
        }
    }

}





