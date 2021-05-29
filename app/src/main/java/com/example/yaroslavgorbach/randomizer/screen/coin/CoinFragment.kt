package com.example.yaroslavgorbach.randomizer.screen.coin

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.yaroslavgorbach.randomizer.*
import com.example.yaroslavgorbach.randomizer.component.CoinComponent
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
            val animatorCoin = CoinComponent(coin, fon, soundManager)

            if (soundPreferences.getState(SoundPreferences.COIN_SOUND_KEY)) toolbar.setIconMusicOn()
            else toolbar.setIconMusicOff()

            toolbar.setOnMenuItemClickListener {
                if (soundPreferences.getState(SoundPreferences.COIN_SOUND_KEY)){
                    toolbar.setIconMusicOff()
                    soundPreferences.disallowSound(SoundPreferences.COIN_SOUND_KEY)
                }else{
                    toolbar.setIconMusicOn()
                    soundPreferences.allowSound(SoundPreferences.COIN_SOUND_KEY)
                }
                true
            }

            toolbar.setNavigationOnClickListener {

            }

            coin.setOnClickListener {
                animatorCoin.animate()
            }
        }
    }

}