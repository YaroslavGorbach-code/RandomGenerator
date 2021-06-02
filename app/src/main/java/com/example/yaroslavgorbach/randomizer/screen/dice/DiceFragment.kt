package com.example.yaroslavgorbach.randomizer.screen.dice

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.component.dice.DiceImp
import com.example.yaroslavgorbach.randomizer.data.local.Repo
import com.example.yaroslavgorbach.randomizer.databinding.FragmentDicesBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.util.onBackPressed
import javax.inject.Inject

class DiceFragment : Fragment(R.layout.fragment_dices) {
    @Inject
    lateinit var soundManager: SoundManager
    @Inject
    lateinit var repo: Repo

    companion object Args {
        fun argsOf(number: Int) = bundleOf("number" to number)
        private val DiceFragment.number get() = requireArguments()["number"] as Int
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init component
        val diceComponent = DiceImp(repo)

        // init view
        val v = DiceView(
            FragmentDicesBinding.bind(view),
            number,
            viewLifecycleOwner,
            soundManager,
            object : DiceView.Callback {
                override fun onBack() {
                    onBackPressed()
                }

                override fun onSoundDisallow() {
                    diceComponent.disallowSound()
                }

                override fun onSoundAllow() {
                    diceComponent.allowSound()
                }
            })
        diceComponent.getSoundIsAllow().observe(viewLifecycleOwner, v::setSoundIsAllow)
    }
}

