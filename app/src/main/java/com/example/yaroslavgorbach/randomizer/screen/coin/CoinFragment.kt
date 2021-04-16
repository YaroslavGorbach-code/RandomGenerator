package com.example.yaroslavgorbach.randomizer.screen.coin

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.yaroslavgorbach.randomizer.*
import com.example.yaroslavgorbach.randomizer.component.CoinAnimator
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.databinding.FragmentCoinBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn
import javax.inject.Inject


class CoinFragment : Fragment(R.layout.fragment_coin) {
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var soundPreferences: SoundPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentCoinBinding.bind(view)){
            val animatorCoin = CoinAnimator(coin, fon, soundManager)

            if (soundPreferences.getState(SoundPreferences.COIN_SOUND_KEY)) toolbarCoin.setIconMusicOn()
            else toolbarCoin.setIconMusicOff()

            toolbarCoin.setOnMenuItemClickListener {
                if (soundPreferences.getState(SoundPreferences.COIN_SOUND_KEY)){
                    toolbarCoin.setIconMusicOff()
                    soundPreferences.disallowSound(SoundPreferences.COIN_SOUND_KEY)
                }else{
                    toolbarCoin.setIconMusicOn()
                    soundPreferences.allowSound(SoundPreferences.COIN_SOUND_KEY)
                }
                true
            }

            toolbarCoin.setNavigationOnClickListener {

            }

            coin.setOnClickListener {
                animatorCoin.animate()
            }
        }
    }

}