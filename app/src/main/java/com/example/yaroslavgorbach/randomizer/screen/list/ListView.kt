package com.example.yaroslavgorbach.randomizer.screen.list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.databinding.FragmentListBinding
import com.example.yaroslavgorbach.randomizer.feature.SoundManager
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOff
import com.example.yaroslavgorbach.randomizer.util.setIconMusicOn

@SuppressLint("ClickableViewAccessibility")
class ListView(
    private val binding: FragmentListBinding,
    private val soundManager: SoundManager,
    private val callback: Callback
) {
    interface Callback {
        fun onSoundDisallow()
        fun onSoundAllow()
        fun onBack()
    }

    data class ListItem(
        var textView: TextView,
        var text: String,
        var isSelected: Boolean,
        var color: Int
    )

    private var finalItemIsOnScreen = false
    private var soundIsAllow: Boolean = false
    private val mItems = mutableListOf<ListItem>()
    private val listAnimator: ListAnimator = ListAnimator()

    init {
        binding.toolbar.setOnMenuItemClickListener {
            if (soundIsAllow) callback.onSoundDisallow()
            else callback.onSoundAllow()
            true
        }
        binding.toolbar.setNavigationOnClickListener { callback.onBack() }
        binding.animate.setOnClickListener {
            animateAll()
        }
        binding.fon.setOnClickListener {
            hideFinalItem()
        }
        binding.grid.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP)
            hideFinalItem()
            true
        }
    }

    fun setSoundIsAllow(isAllow: Boolean) {
        soundIsAllow = isAllow
        if (isAllow) binding.toolbar.setIconMusicOn()
        else binding.toolbar.setIconMusicOff()
    }

    fun inflateItems(items: List<String>) {
        val inflater = LayoutInflater.from(binding.root.context)
        for (i in items.indices) {
            val itemV: View = inflater.inflate(R.layout.item_list, binding.grid, false)
            val listItemTv: TextView = itemV.findViewById(R.id.list_item)
            val color = Color.argb(255, (0..200).random(), (0..100).random(), (0..200).random())
            listItemTv.background.setTint(color)
            val listItem = ListItem(listItemTv, items[i], false, color)

            itemV.setOnClickListener {
                if (listItem.textView.text.isNotEmpty()) {
                    listAnimator.hideItem(listItem.textView, onHideItem = {
                        listItem.textView.text = null
                    })
                } else {
                    animateItem(listItem, false)
                }
            }
            binding.grid.addView(itemV)
            mItems.add(listItem)
        }
        mItems.shuffle()
    }

    private fun animateItem(item: ListItem, showFinal: Boolean) {

        listAnimator.rotateItem(item.textView, object :
            ListAnimator.Callback {
            override fun showItemText() {
                item.textView.text = item.text
            }

            override fun hideItemText() {
                item.textView.text = null
            }

            override fun showResult() {
                item.textView.text = item.text
                if (showFinal) showFinalItem(item)
                soundManager.listBellSoundPlay()
            }
        })

    }

    private fun animateAll() {
        hideFinalItem()
        listAnimator.rotateButton(binding.animate)
        for (i in mItems.indices) {
            if (mItems[i].textView.text.isNotEmpty()) {
                listAnimator.hideItem(mItems[i].textView, onHideItem = {
                    mItems[i].textView.text = null
                })
            }
        }
        val itemIndex = (mItems.indices).random()
        mItems.shuffle()
        animateItem(mItems[itemIndex], true)
    }

    private fun showFinalItem(item: ListItem) {
        listAnimator.showFinalItem(binding.finalItem.finalItem, onAnimationStart = {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && !finalItemIsOnScreen) {
                listAnimator.setDarkForeground(binding.background)
            }
            binding.finalItem.finalItem.background.setTint(item.color)
            binding.finalItem.finalText.text = item.text
            binding.finalItem.finalItem.visibility = View.VISIBLE
        })
        finalItemIsOnScreen = true
        for (i in mItems) {
            i.textView.isClickable = false
        }
    }

    private fun hideFinalItem() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && finalItemIsOnScreen) {
            listAnimator.setLightForeground(binding.background)
        }
        listAnimator.hideFinalItemAnimation(binding.finalItem.finalItem, onAnimationEnd = {
            binding.finalItem.finalItem.visibility = View.GONE
            binding.finalItem.finalText.text = null
        })
        finalItemIsOnScreen = false
        for (i in mItems) {
            i.textView.isClickable = true
        }
    }

    private fun List<ListItem>.shuffle() {
        val listText = mutableListOf<String>()
        for (i in this.indices) {
            listText.add(mItems[i].text)
        }
        listText.shuffle()
        for (i in listText.indices) {
            mItems[i].text = listText[i]
        }
    }
}
