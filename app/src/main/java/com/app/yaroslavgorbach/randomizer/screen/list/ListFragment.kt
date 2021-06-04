package com.app.yaroslavgorbach.randomizer.screen.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.app.yaroslavgorbach.randomizer.R
import com.app.yaroslavgorbach.randomizer.component.list.ListComp
import com.app.yaroslavgorbach.randomizer.component.list.ListCompImp
import com.app.yaroslavgorbach.randomizer.data.local.Repo
import com.app.yaroslavgorbach.randomizer.di.appComponent
import com.app.yaroslavgorbach.randomizer.feature.SoundManager
import com.app.yaroslavgorbach.randomizer.util.onBackPressed
import com.app.yaroslavgorbach.randomizer.databinding.FragmentListBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_list) {
    @Inject
    lateinit var repo: Repo

    @Inject
    lateinit var soundManager: SoundManager

    companion object Args {
        fun argsOf(title: String) = bundleOf("title" to title)
        private val ListFragment.title get() = requireArguments()["title"] as String
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init component
        val listComp: ListComp = ListCompImp(repo, title, lifecycleScope)

        // init view
        val v = ListView(FragmentListBinding.bind(view), soundManager, object : ListView.Callback {
            override fun onSoundDisallow() {
                listComp.disallowSound()
            }

            override fun onSoundAllow() {
                listComp.allowSound()
            }

            override fun onBack() {
                onBackPressed()
            }
        })
        listComp.getSoundIsAllow().observe(viewLifecycleOwner, v::setSoundIsAllow)
        lifecycleScope.launch { v.inflateItems(listComp.words.await()) }
    }
}
