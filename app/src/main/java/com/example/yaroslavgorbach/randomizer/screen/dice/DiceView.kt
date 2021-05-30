package com.example.yaroslavgorbach.randomizer.screen.dice

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.component.dice.DiceImp
import com.example.yaroslavgorbach.randomizer.databinding.FragmentDicesBinding
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn

class DiceView(private val binding: FragmentDicesBinding, number: Int, callback: Callback) {
    private var soundIsAllow: Boolean = false

    interface Callback{
        fun onBack()
        fun onDiceClick(dice: DiceImp.DiceModel)
        fun onDiceInflated(dice: DiceImp.DiceModel, button: View)
        fun onButtonAnimate(button: View)
        fun onSoundDisallow()
        fun onSoundAllow()
    }
    init {
        val inflater = LayoutInflater.from(binding.root.context)
        for (i in 1..number) {
            val item: View = inflater.inflate(R.layout.item_dice, binding.grid, false)
            val diceImage: ImageView = item.findViewById(R.id.dice)
            val dice = DiceImp.DiceModel(diceImage, 0)
            item.setOnClickListener { callback.onDiceClick(dice) }
            callback.onDiceInflated(dice, binding.animate)
            binding.grid.addView(item)
        }
        binding.animate.setOnClickListener (callback::onButtonAnimate)
        binding.toolbar.setOnMenuItemClickListener {
            if (soundIsAllow) callback.onSoundDisallow()
            else callback.onSoundAllow()
            true
        }
        binding.toolbar.setNavigationOnClickListener { callback.onBack() }
    }
    fun setSoundIsAllow(isAllow: Boolean) {
        soundIsAllow = isAllow
        if (isAllow) binding.toolbar.setIconMusicOn()
        else binding.toolbar.setIconMusicOff()
    }

    fun setSum(sum: Int){
        binding.toolbar.title = sum.toString()
    }
}