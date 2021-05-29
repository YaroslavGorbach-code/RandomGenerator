package com.example.yaroslavgorbach.randomizer.screen.dice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.component.AnimatorDice
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.databinding.FragmentDicesBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import javax.inject.Inject

class DiceFragment : Fragment(R.layout.fragment_dices) {
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var soundPreferences: SoundPreferences

    companion object Args {
        fun argsOf(number: Int)
                = bundleOf("number" to number)
        private val DiceFragment.number get() = requireArguments()["number"] as Int
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentDicesBinding.bind(view)){
            val diceAnimator = AnimatorDice(soundManager)
            diceAnimator.inflateDice(grid, number, animateDices)

            if (soundPreferences.getState(SoundPreferences.DICE_SOUND_KEY)) toolbarDice.setIconMusicOn()
            else toolbarDice.setIconMusicOff()

            animateDices.setOnClickListener{
                diceAnimator.animateAllDice(it)
            }

            diceAnimator.getSum().observe(viewLifecycleOwner,{
                toolbarDice.title = getString(R.string.total) + " $it"
            })

            toolbarDice.setNavigationOnClickListener {
            }

            toolbarDice.setOnMenuItemClickListener {
                if (soundPreferences.getState(SoundPreferences.DICE_SOUND_KEY)){
                    toolbarDice.setIconMusicOff()
                    soundPreferences.disallowSound(SoundPreferences.DICE_SOUND_KEY)
                }else{
                    toolbarDice.setIconMusicOn()
                    soundPreferences.allowSound(SoundPreferences.DICE_SOUND_KEY)
                }
                true
            }
        }
    }
}

