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
     fun animateDice(image: ImageView, diceModel: DiceModel){
        rotateDice(image)
        when((1..6).random()){
            1 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_1) }, 600)
                diceModel.image = R.drawable.ic_dice_1
            }
            2 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_2) }, 600)
                diceModel.image = R.drawable.ic_dice_2
            }
            3 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_3) }, 600)
                diceModel.image = R.drawable.ic_dice_3
            }
            4 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_4) }, 600)
                diceModel.image = R.drawable.ic_dice_4
            }
            5 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_5) }, 600)
                diceModel.image = R.drawable.ic_dice_5
            }
            6 -> {
                android.os.Handler().postDelayed({ image.setImageResource(R.drawable.ic_dice_6) }, 600)
                diceModel.image = R.drawable.ic_dice_6
            }
        }
    }

    private fun rotateDice(dice: ImageView){
        val rotate = ObjectAnimator.ofFloat(dice, View.ROTATION_Y, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(dice)
        rotate.start()
    }

}