package com.example.yaroslavgorbach.randomizer.screen.dice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.screen.list.ListFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import javax.inject.Inject

class DiceFragment : Fragment() {
    private lateinit var mDiceAnimator: AnimatorDice
    private lateinit var mAnimateAllDicesBt: ExtendedFloatingActionButton
    private lateinit var mGrid: GridLayout
    private lateinit var mToolbar: Toolbar
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var soundPreferences: SoundPreferences


    companion object Args {
        fun argsOf(number: Int)
                = bundleOf("number" to number)
        private val DiceFragment.number get() = requireArguments()["number"] as Int
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dices, container, false)
        mAnimateAllDicesBt = view.findViewById(R.id.animateAllDices)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mGrid = view.findViewById(R.id.grid)
        mDiceAnimator = AnimatorDice(soundManager)
        mDiceAnimator.inflateDice(mGrid, number, mAnimateAllDicesBt)

        if (soundPreferences.getState(SoundPreferences.DICE_SOUND_KEY)) mToolbar.setIconMusicOn()
        else mToolbar.setIconMusicOff()

        mAnimateAllDicesBt.setOnClickListener{
            mDiceAnimator.animateAllDice(mAnimateAllDicesBt)
        }

        mDiceAnimator.getSum().observe(viewLifecycleOwner,{
            mToolbar.title = getString(R.string.total) + " $it"
        })

        mToolbar.setNavigationOnClickListener {

        }

        mToolbar.setOnMenuItemClickListener {
            if (soundPreferences.getState(SoundPreferences.DICE_SOUND_KEY)){
                mToolbar.setIconMusicOff()
                soundPreferences.disallowSound(SoundPreferences.DICE_SOUND_KEY)
            }else{
                mToolbar.setIconMusicOn()
                soundPreferences.allowSound(SoundPreferences.DICE_SOUND_KEY)
            }
            true
        }

        return view
    }
}

