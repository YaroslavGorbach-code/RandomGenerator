package com.example.yaroslavgorbach.randomizer.screen.number

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.component.number.NumberCom
import com.example.yaroslavgorbach.randomizer.component.number.NumberComImp
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.databinding.FragmentNumberBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import javax.inject.Inject

class NumberFragment : Fragment(R.layout.fragment_number) {
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var repo: Repo

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

        // init comp
        val number: NumberCom = NumberComImp(repo, max, min, results)

        // init v
        val v = NumberView(FragmentNumberBinding.bind(view), soundManager, object :NumberView.Callback{
            override fun onSoundDisallow() {
                number.disallowSound()
            }

            override fun onBack() {
            }

            override fun onSoundAllow() {
                number.allowSound()
            }

            override fun onGenerateValue() {
                number.onGenerateValue()
            }
        })
        number.getSoundIsAllow().observe(viewLifecycleOwner, v::setSoundIsAllow)
        number.value.observe(viewLifecycleOwner, v::setValue)
    }
}