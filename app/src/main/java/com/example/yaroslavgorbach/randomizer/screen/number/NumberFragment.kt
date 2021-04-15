package com.example.yaroslavgorbach.randomizer.screen.number

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import javax.inject.Inject

class NumberFragment : Fragment() {
    private lateinit var mNumberTv: TextView
    private lateinit var mNumberParent: ConstraintLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mRefreshNumber: ExtendedFloatingActionButton
    private lateinit var mNumberAnimator: NumberAnimator
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
        (requireActivity().application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_number, container, false)
        mNumberTv = view.findViewById(R.id.number)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mRefreshNumber = view.findViewById(R.id.refreshNumber)
        mNumberParent = view.findViewById(R.id.numberParent)

        if (soundPreferences.getState(SoundPreferences.NUMBER_SOUND_KEY)) mToolbar.setIconMusicOn()
        else mToolbar.setIconMusicOff()

        mNumberAnimator = NumberAnimator(soundManager)
        mNumberAnimator.animateNumber(mNumberTv, mNumberParent, mRefreshNumber, min, max, results)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mToolbar.setNavigationOnClickListener {

        }

        mRefreshNumber.setOnClickListener { button ->
            mNumberAnimator.animateNumber(mNumberTv, mNumberParent, button, min, max, results)
        }

        mToolbar.setOnMenuItemClickListener {
            if (soundPreferences.getState(SoundPreferences.NUMBER_SOUND_KEY)){
                mToolbar.setIconMusicOff()
                soundPreferences.disallowSound(SoundPreferences.NUMBER_SOUND_KEY)
            }else{
                mToolbar.setIconMusicOn()
                soundPreferences.allowSound(SoundPreferences.NUMBER_SOUND_KEY)
            }
            true
        }

    }
}