package com.example.yaroslavgorbach.randomizer.dice

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R

class AnimateDice {
        fun inflateDices(parent: ViewGroup, number: Int){
            val inflater = LayoutInflater.from(parent.context)
            for (i in 1..number) {
                val item: View = inflater.inflate(R.layout.dice_i, parent, false)
                val diceImage: ImageView = item.findViewById(R.id.dice_i)
                when(i){
                    1,7,13,19,25 -> diceImage.setImageResource(R.drawable.ic_dice_1)
                    2,8,14,20,26 -> diceImage.setImageResource(R.drawable.ic_dice_2)
                    3,9,15,21,27 -> diceImage.setImageResource(R.drawable.ic_dice_3)
                    4,10,16,22,28 -> diceImage.setImageResource(R.drawable.ic_dice_4)
                    5,11,17,23,29 -> diceImage.setImageResource(R.drawable.ic_dice_5)
                    6,12,18,24,30 -> diceImage.setImageResource(R.drawable.ic_dice_6)
                }

                item.setOnClickListener { v: View? -> animateDice(v) }
                parent.addView(item)
        }
    }

    private fun animateDice(view: View?){
        rotateDice(view)
    }

    private fun rotateDice(dice: View?){
        val rotate = ObjectAnimator.ofFloat(dice, View.ROTATION_Y, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator  = LinearInterpolator()
        rotate.start()

    }
}