package com.app.yaroslavgorbach.randomizer.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.app.yaroslavgorbach.randomizer.R
import com.app.yaroslavgorbach.randomizer.databinding.FragmentRandomListBinding
import com.app.yaroslavgorbach.randomizer.di.appComponent
import kotlinx.android.synthetic.main.fragment_random_list.*

class MainFragment : Fragment(R.layout.fragment_random_list) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentRandomListBinding.bind(view)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.changeTheme -> {
                }
                R.id.rate -> {
                }
                R.id.share -> {
                }
                R.id.removeAd -> {
                }
            }
            true
        }

        open_dice.setOnClickListener { nav.showDicesDialog() }

        open_coin.setOnClickListener { nav.openCoin() }

        open_number.setOnClickListener { nav.showNumberDialog() }

        open_list.setOnClickListener { nav.showChoseListDialog() }

        open_matches.setOnClickListener { nav.showMatchesDialog() }
    }
}
