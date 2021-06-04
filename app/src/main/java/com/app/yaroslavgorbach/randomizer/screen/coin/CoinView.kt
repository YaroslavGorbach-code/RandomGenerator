package com.app.yaroslavgorbach.randomizer.screen.coin

import android.widget.ImageView
import com.app.yaroslavgorbach.randomizer.databinding.FragmentCoinBinding

class CoinView(private val binding: FragmentCoinBinding, private val callback: Callback) {
    private var soundIsAllow: Boolean = false
    interface Callback {
        fun onCoin(coin: ImageView, fon: ImageView)
        fun onSoundAllow()
        fun onSoundDisallow()
        fun onBack()
    }

    init {
        binding.toolbar.setOnMenuItemClickListener {
            if (soundIsAllow) callback.onSoundDisallow()
             else callback.onSoundAllow()
            true
        }
        binding.toolbar.setNavigationOnClickListener { callback.onBack() }
        binding.coin.setOnClickListener { callback.onCoin(binding.coin, binding.fon) }
    }

    fun setSoundIsAllow(isAllow: Boolean) {
        soundIsAllow = isAllow
        if (isAllow) binding.toolbar.setIconMusicOn()
        else binding.toolbar.setIconMusicOff()
    }

}