package com.app.yaroslavgorbach.randomizer.screen.matches

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.app.yaroslavgorbach.randomizer.R
import com.app.yaroslavgorbach.randomizer.component.matches.Matches
import com.app.yaroslavgorbach.randomizer.component.matches.MatchesImp
import com.app.yaroslavgorbach.randomizer.data.local.Repo
import com.app.yaroslavgorbach.randomizer.di.appComponent
import com.app.yaroslavgorbach.randomizer.feature.SoundManager
import com.app.yaroslavgorbach.randomizer.util.onBackPressed
import com.app.yaroslavgorbach.randomizer.databinding.FragmentMatchesBinding
import javax.inject.Inject

class MatchesFragment : Fragment(R.layout.fragment_matches) {
    @Inject
    lateinit var soundManager: SoundManager
    @Inject
    lateinit var repo: Repo

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

        // init comp
        val matches: Matches = MatchesImp(repo)

        // init view
        val v = MatchesView(
            FragmentMatchesBinding.bind(view),
            number,
            burned,
            soundManager,
            object : MatchesView.Callback {
                override fun onBack() {
                    onBackPressed()
                }

                override fun onSoundDisallow() {
                    matches.disallowSound()
                }

                override fun onSoundAllow() {
                    matches.allowSound()
                }

            })
        matches.getSoundIsAllow().observe(viewLifecycleOwner, v::setSoundIsAllow)
    }
}
