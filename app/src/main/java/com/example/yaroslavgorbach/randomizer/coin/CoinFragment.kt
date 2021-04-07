package com.example.yaroslavgorbach.randomizer.coin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yaroslavgorbach.randomizer.*
import com.example.yaroslavgorbach.randomizer.sounds.SoundManager
import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferences
import com.example.yaroslavgorbach.randomizer.sounds.SoundPreferencesStorage
import javax.inject.Inject


class CoinFragment : Fragment() {
    private lateinit var mToolbar: Toolbar
    private lateinit var mCoinImage: ImageView
    private lateinit var mDeckFon: ImageView
    private lateinit var mCoinAnimatorAnimation: CoinAnimator
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var soundPreferences: SoundPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
       (requireActivity().application as MyApplication).appComponent.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_coin, container, false)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mCoinImage = view.findViewById(R.id.coin)
        mDeckFon = view.findViewById(R.id.wood)
        mCoinAnimatorAnimation = CoinAnimator(mCoinImage, mDeckFon, soundManager)
        if (soundPreferences.getState(SoundPreferences.COIN_SOUND_KEY)) mToolbar.setIconMusicOn()
        else mToolbar.setIconMusicOff()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar.setOnMenuItemClickListener {
            if (soundPreferences.getState(SoundPreferences.COIN_SOUND_KEY)){
                mToolbar.setIconMusicOff()
                soundPreferences.disallowSound(SoundPreferences.COIN_SOUND_KEY)
            }else{
                mToolbar.setIconMusicOn()
                soundPreferences.allowSound(SoundPreferences.COIN_SOUND_KEY)
            }
            true
        }

        mCoinImage.setOnClickListener {
            mCoinAnimatorAnimation.animate()
        }

        mToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}