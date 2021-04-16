package com.example.yaroslavgorbach.randomizer.screen.number

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.component.NumberAnimator
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.databinding.FragmentNumberBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import javax.inject.Inject

class NumberFragment : Fragment(R.layout.fragment_number) {
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var soundPreferences: SoundPreferences

    companion object Args {
        fun argsOf(max: Long, min: Long, results: Long)
        = bundleOf("max" to max, "min" to min, "results" to results)
        private val NumberFragment.max get() = requireArguments()["max"] as Long
        private val NumberFragment.min get() = requireArguments()["min"] as Long
        private val NumberFragment.results get() = requireArguments()["results"] as Long
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(FragmentNumberBinding.bind(view)){
            if (soundPreferences.getState(SoundPreferences.NUMBER_SOUND_KEY)) toolbarNumber.setIconMusicOn()
            else toolbarNumber.setIconMusicOff()

            val animatorNumber = NumberAnimator(soundManager)
            animatorNumber.animateNumber(number, numberParent, animateNumber, min, max, results)

            toolbarNumber.setNavigationOnClickListener {

            }

            animateNumber.setOnClickListener { button ->
                animatorNumber.animateNumber(number, numberParent, button, min, max, results)
            }

            toolbarNumber.setOnMenuItemClickListener {
                if (soundPreferences.getState(SoundPreferences.NUMBER_SOUND_KEY)){
                    toolbarNumber.setIconMusicOff()
                    soundPreferences.disallowSound(SoundPreferences.NUMBER_SOUND_KEY)
                }else{
                    toolbarNumber.setIconMusicOn()
                    soundPreferences.allowSound(SoundPreferences.NUMBER_SOUND_KEY)
                }
                true
            }
        }
    }
}