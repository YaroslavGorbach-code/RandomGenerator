package com.example.yaroslavgorbach.randomizer.screen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.screen.list.CreateEditListDialog
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.screen.list.ListTitlesAdapter
import com.example.yaroslavgorbach.randomizer.databinding.FragmentRandomListBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_random_list.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                    nav.showChangeThemeDialog()
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
