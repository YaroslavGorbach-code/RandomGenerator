package com.app.yaroslavgorbach.randomizer.screen.coin

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.app.yaroslavgorbach.randomizer.R
import com.app.yaroslavgorbach.randomizer.component.coin.Coin
import com.app.yaroslavgorbach.randomizer.component.coin.CoinImp
import com.app.yaroslavgorbach.randomizer.data.local.Repo
import com.app.yaroslavgorbach.randomizer.di.appComponent
import com.app.yaroslavgorbach.randomizer.feature.SoundManager
import com.app.yaroslavgorbach.randomizer.util.onBackPressed
import com.app.yaroslavgorbach.randomizer.databinding.FragmentCoinBinding
import javax.inject.Inject

class CoinFragment : Fragment(R.layout.fragment_coin) {
    @Inject
    lateinit var soundManager: SoundManager
    @Inject
    lateinit var repo: Repo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init component
        val coinComponent: Coin = CoinImp(soundManager, repo)

        // init view
        val v = CoinView(FragmentCoinBinding.bind(view), soundManager, object : CoinView.Callback {

            override fun onSoundAllow() {
                coinComponent.allowSound()
            }

            override fun onSoundDisallow() {
                coinComponent.disallowSound()
            }

            override fun onBack() {
                onBackPressed()
            }

        })
        coinComponent.getSoundIsAllow().observe(viewLifecycleOwner, v::setSoundIsAllow)

    }
}