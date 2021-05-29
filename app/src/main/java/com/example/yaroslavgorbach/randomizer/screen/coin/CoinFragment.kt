package com.example.yaroslavgorbach.randomizer.screen.coin
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.yaroslavgorbach.randomizer.*
import com.example.yaroslavgorbach.randomizer.component.coin.Coin
import com.example.yaroslavgorbach.randomizer.component.coin.CoinImp
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.databinding.FragmentCoinBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn
import javax.inject.Inject

class CoinFragment : Fragment(R.layout.fragment_coin) {
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var repo: Repo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init component
        val coinComponent: Coin = CoinImp(soundManager, repo)

        // init view
        val v = CoinView(FragmentCoinBinding.bind(view), object :CoinView.Callback{
            override fun onCoin(coin: ImageView, fon: ImageView) {
                coinComponent.animate(coin, fon)
            }

            override fun onSoundAllow() {
                coinComponent.allowSound()
            }

            override fun onSoundDisallow() {
                coinComponent.disallowSound()
            }

            override fun onBack() {
            }

        })
        coinComponent.getSoundIsAllow().observe(viewLifecycleOwner, v::setSoundIsAllow)

        }
    }