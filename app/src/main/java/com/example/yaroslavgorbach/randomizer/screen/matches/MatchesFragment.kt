package com.example.yaroslavgorbach.randomizer.screen.matches

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.screen.dice.DiceFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import javax.inject.Inject

class MatchesFragment : Fragment(R.layout.fragment_matches) {
    private lateinit var mToolbar: Toolbar
    private lateinit var mParent: LinearLayout
    private lateinit var mRefreshMatchesButton: ExtendedFloatingActionButton
    private lateinit var mAnimator: MatchesAnimations
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
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mParent = view.findViewById(R.id.matchesParent)
        mRefreshMatchesButton = view.findViewById(R.id.refreshMatches)

        if (soundPreferences.getState(SoundPreferences.MATCHES_SOUND_KEY)) mToolbar.setIconMusicOn()
        else mToolbar.setIconMusicOff()

        mAnimator = MatchesAnimations(soundManager)
        mAnimator.inflateMatches(mParent, number, burned)

        mRefreshMatchesButton.setOnClickListener {
            mAnimator.refreshMatches(mRefreshMatchesButton)
        }

        mToolbar.setNavigationOnClickListener {

        }

        mToolbar.setOnMenuItemClickListener {
            if (soundPreferences.getState(SoundPreferences.MATCHES_SOUND_KEY)){
                mToolbar.setIconMusicOff()
                soundPreferences.disallowSound(SoundPreferences.MATCHES_SOUND_KEY)
            }else{
                mToolbar.setIconMusicOn()
                soundPreferences.allowSound(SoundPreferences.MATCHES_SOUND_KEY)
            }
            true
        }

    }

}