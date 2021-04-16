package com.example.yaroslavgorbach.randomizer.screen.matches

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.databinding.FragmentMatchesBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import javax.inject.Inject

class MatchesFragment : Fragment(R.layout.fragment_matches) {
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var soundPreferences: SoundPreferences

    companion object Args {
        fun argsOf(number: Int, burned: Int) =
            bundleOf("number" to number, "burned" to burned)
        private val MatchesFragment.number get() = requireArguments()["number"] as Int
        private val MatchesFragment.burned get() = requireArguments()["burned"] as Int
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(FragmentMatchesBinding.bind(view)){
            if (soundPreferences.getState(SoundPreferences.MATCHES_SOUND_KEY)) toolbarMatches.setIconMusicOn()
            else toolbarMatches.setIconMusicOff()

            val animatorMatches = MatchesAnimations(soundManager)
            animatorMatches.inflateMatches(matchesParent, number, burned)

            animateMatches.setOnClickListener {
                animatorMatches.refreshMatches(it)
            }

            toolbarMatches.setNavigationOnClickListener {

            }

            toolbarMatches.setOnMenuItemClickListener {
                if (soundPreferences.getState(SoundPreferences.MATCHES_SOUND_KEY)){
                    toolbarMatches.setIconMusicOff()
                    soundPreferences.disallowSound(SoundPreferences.MATCHES_SOUND_KEY)
                }else{
                    toolbarMatches.setIconMusicOn()
                    soundPreferences.allowSound(SoundPreferences.MATCHES_SOUND_KEY)
                }
                true
            }
        }
    }
}