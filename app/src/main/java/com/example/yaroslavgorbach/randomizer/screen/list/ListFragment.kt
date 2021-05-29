package com.example.yaroslavgorbach.randomizer.screen.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.component.AnimatorList
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.databinding.FragmentListBinding
import com.example.yaroslavgorbach.randomizer.di.appComponent
import kotlinx.coroutines.*
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_list) {
    @Inject lateinit var repo: Repo
    @Inject lateinit var soundManager: SoundManager
    @Inject lateinit var soundPreferences: SoundPreferences

    companion object Args {
        fun argsOf(title: String)
                = bundleOf("title" to title)
        private val ListFragment.title get() = requireArguments()["title"] as String
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(FragmentListBinding.bind(view)){
            val animatorList = AnimatorList(background, finalItem, finalText, soundManager)

            if (soundPreferences.getState(SoundPreferences.LIST_SOUND_KEY)) toolbarList.setIconMusicOn()
            else toolbarList.setIconMusicOff()

            toolbarList.setNavigationOnClickListener {
            }

            GlobalScope.launch {
                val result = async {
                    repo.getItemsByTitle(title)
                }
                withContext(Dispatchers.Main) {
                    animatorList.inflateItems(grid, animateList, listOfItems = result.await())
                }
            }

            toolbarList.setOnMenuItemClickListener {
                if (soundPreferences.getState(SoundPreferences.LIST_SOUND_KEY)){
                    toolbarList.setIconMusicOff()
                    soundPreferences.disallowSound(SoundPreferences.LIST_SOUND_KEY)
                }else{
                    toolbarList.setIconMusicOn()
                    soundPreferences.allowSound(SoundPreferences.LIST_SOUND_KEY)
                }
                true
            }

            animateList.setOnClickListener {
                animatorList.showResult(it)
            }

            background.setOnClickListener {
                animatorList.hideFinalItem()
            }

            grid.setOnClickListener {
                animatorList.hideFinalItem()
            }
        }
    }
}
