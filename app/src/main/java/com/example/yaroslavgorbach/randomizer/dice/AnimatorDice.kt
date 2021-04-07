package com.example.yaroslavgorbach.randomizer.dice

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation
import com.example.yaroslavgorbach.randomizer.sounds.SoundManager

class AnimatorDice(context: Context){
    private val mDices = mutableListOf<DiceModel>()
    private val _mSum: MutableLiveData<Int> = MutableLiveData(0)
    private val mSum: LiveData<Int> = _mSum
    private val mSoundManager: SoundManager = SoundManager(context)

    init {
        // i did it to play it for the firs time. Without delay sound does not play
        android.os.Handler().postDelayed({  mSoundManager.rollAllDicesSoundPlay() }, 200)
    }

//    private fun animateDice(dice: DiceModel, rotateAllDice: View) {
//        rotateDice(dice.imageView, rotateAllDice)
//        _mSum.value  = _mSum.value!! - dice.points
//        when ((1..6).random()) {
//            1 -> {
//                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_1)
//                    _mSum.value  = _mSum.value!! +1
//                    dice.points = 1}, 500)
//            }
//            2 -> {
//                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_2)
//                    _mSum.value  = _mSum.value!! +2
//                    dice.points = 2}, 500)
//            }
//            3 -> {
//                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_3)
//                    _mSum.value  = _mSum.value!! +3
//                    dice.points = 3}, 500)
//            }
//            4 -> {
//                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_4)
//                    _mSum.value  = _mSum.value!! +4
//                    dice.points = 4}, 500)
//            }
//            5 -> {
//                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_5)
//                    _mSum.value  = _mSum.value!! +5
//                    dice.points = 5}, 500)
//            }
//            6 -> {
//                android.os.Handler().postDelayed({ dice.imageView.setImageResource(R.drawable.ic_dice_6)
//                    _mSum.value  = _mSum.value!! +6
//                    dice.points = 6}, 500)
//            }
//        }
//    }

    private fun changeDiceSide(dice: DiceModel) {
        _mSum.value = _mSum.value!! - dice.points
        when ((1..6).random()) {
            1 -> {
                dice.imageView.setImageResource(R.drawable.ic_dice_1)
                dice.points = 1
                _mSum.value = _mSum.value!! + 1
            }
            2 -> {
                dice.imageView.setImageResource(R.drawable.ic_dice_2)
                dice.points = 2
                _mSum.value = _mSum.value!! + 2
            }
            3 -> {
                dice.imageView.setImageResource(R.drawable.ic_dice_3)
                dice.points = 3
                _mSum.value = _mSum.value!! + 3
            }
            4 -> {
                dice.imageView.setImageResource(R.drawable.ic_dice_4)
                dice.points = 4
                _mSum.value = _mSum.value!! + 4
            }
            5 -> {
                dice.imageView.setImageResource(R.drawable.ic_dice_5)
                dice.points = 5
                _mSum.value = _mSum.value!! + 5
            }
            6 -> {
                dice.imageView.setImageResource(R.drawable.ic_dice_6)
                dice.points = 6
                _mSum.value = _mSum.value!! + 6
            }
        }
    }
    private fun rotateDice(dice: DiceModel, rotateAllDice: View) {
        mSoundManager.rollAllDicesSoundPlay()
         ValueAnimator.ofFloat(-740f, 0f).apply {

             addUpdateListener {
                 dice.imageView.rotationY = it.animatedValue as Float

                 if ((it.animatedValue as Float).toInt() == -80)
                 changeDiceSide(dice)
             }

            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            mDices.forEach {disableViewDuringAnimation(it.imageView) }
            disableViewDuringAnimation(rotateAllDice)
            start()
        }


    }

    private fun rotateButton(view: View){
        val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, -740f, 0f)
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(view)
        mDices.forEach { rotate.disableViewDuringAnimation(it.imageView) }
        rotate.start()
    }


    fun inflateDice(parent: ViewGroup, number: Int, rotateAllDice: View) {
        val inflater = LayoutInflater.from(parent.context)
        for (i in 1..number) {
            val item: View = inflater.inflate(R.layout.dice_i, parent, false)
            val diceImage: ImageView = item.findViewById(R.id.dice_i)
            val dice = DiceModel(diceImage, 0)
            item.setOnClickListener {rotateDice(dice, rotateAllDice) }
            _mSum.value = _mSum.value!! + dice.points
            mDices.add(dice)
            parent.addView(item)
        }
        animateAllDice(rotateAllDice)
    }

    fun animateAllDice(rotateAllDice: View) {
        for (i in mDices.indices){
            rotateDice(mDices[i], rotateAllDice)
        }
        rotateButton(rotateAllDice)
    }

    fun getSum(): LiveData<Int>{
        return mSum
    }

}





