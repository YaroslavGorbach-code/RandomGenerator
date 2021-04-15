package com.example.yaroslavgorbach.randomizer.screen.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import com.example.yaroslavgorbach.randomizer.MyApplication
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.data.database.Repo
import com.example.yaroslavgorbach.randomizer.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.setIconMusicOn
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.data.soundPref.SoundPreferences
import com.example.yaroslavgorbach.randomizer.screen.number.NumberFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.*
import javax.inject.Inject

class ListFragment : Fragment() {
    private lateinit var mLIstAnimator: AnimatorList
    private lateinit var mAnimateAllItems: ExtendedFloatingActionButton
    private lateinit var mFinalTextView: TextView
    private lateinit var mFinalItem: ScrollView
    private lateinit var mGrid: GridLayout
    private lateinit var mToolbar: Toolbar
    private lateinit var mBackground: ConstraintLayout

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
        (activity?.application as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        mAnimateAllItems = view.findViewById(R.id.animateAllItems)
        mToolbar = view.findViewById(R.id.materialToolbar)
        mGrid = view.findViewById(R.id.grid)
        mBackground = view.findViewById(R.id.background)
        mFinalItem = view.findViewById(R.id.finalItem)
        mFinalTextView = view.findViewById(R.id.finalText)
        mLIstAnimator = AnimatorList(mBackground, mFinalItem, mFinalTextView, soundManager)

        if (soundPreferences.getState(SoundPreferences.LIST_SOUND_KEY)) mToolbar.setIconMusicOn()
        else mToolbar.setIconMusicOff()

        mToolbar.setNavigationOnClickListener {

        }

       GlobalScope.launch {
            val result = async {
                repo.getItemsByTitle(title)
            }
            withContext(Dispatchers.Main) {
                mLIstAnimator.inflateItems(mGrid, mAnimateAllItems, listOfItems = result.await())
            }
        }

        mToolbar.setOnMenuItemClickListener {
            if (soundPreferences.getState(SoundPreferences.LIST_SOUND_KEY)){
                mToolbar.setIconMusicOff()
                soundPreferences.disallowSound(SoundPreferences.LIST_SOUND_KEY)
            }else{
                mToolbar.setIconMusicOn()
                soundPreferences.allowSound(SoundPreferences.LIST_SOUND_KEY)
            }
            true
        }

        mAnimateAllItems.setOnClickListener {
            mLIstAnimator.showResult(mAnimateAllItems)
        }

        mBackground.setOnClickListener {
            mLIstAnimator.hideFinalItem()
        }

        mGrid.setOnClickListener {
            mLIstAnimator.hideFinalItem()
        }
        return view
    }

}
