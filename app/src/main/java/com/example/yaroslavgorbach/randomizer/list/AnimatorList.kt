package com.example.yaroslavgorbach.randomizer.list

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation
import java.util.*

class AnimatorList{
    private val mItems = mutableListOf<ListItemModel>()


    fun inflateItems(parent: ViewGroup, listOfItems: MutableList<String>) {
        listOfItems.shuffle()
        val inflater = LayoutInflater.from(parent.context)
        for (i in listOfItems.indices) {
            val itemV: View = inflater.inflate(R.layout.list_i, parent, false)
            val listItemBackground: TextView = itemV.findViewById(R.id.list_item)
            listItemBackground.setBackgroundColor(getRandomColor())
            val listItem = ListItemModel(listItemBackground, listOfItems[i], false)
            itemV.setOnClickListener {
                rotateItem(listItem)
            }

            mItems.add(listItem)
            parent.addView(itemV)
        }
    }

    fun rotateButton(view: View){
        val rotate = ObjectAnimator.ofFloat(view, View.ROTATION, -720f, 0f).apply {
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
        }
        rotate.duration = 700
        rotate.interpolator = AccelerateDecelerateInterpolator()
        rotate.disableViewDuringAnimation(view)
        rotate.start()
    }

    fun showItem() {
        val indexToShow = (mItems.indices).random()
        for (i in mItems.indices){
            rotateItems(mItems[i].parent, indexToShow)
        }
    }

    private fun rotateItem(item: ListItemModel) {
    val rotateY = ObjectAnimator.ofFloat(item.parent, View.ROTATION_Y, -740f, 0f).apply {
        disableViewDuringAnimation(item.parent)
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                item.parent.text = item.text
            }
        })
    }

    val rotateX = ObjectAnimator.ofFloat(item.parent, View.ROTATION_Y, 740f, 0f).apply {
        disableViewDuringAnimation(item.parent)
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                if (!item.isSelected)
                    Handler().postDelayed({item.parent.text = null}, 200)
            }
        })
    }

    AnimatorSet().apply {
        playSequentially(rotateY, rotateX)
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                if(!item.isSelected)
                    Handler().postDelayed({item.parent.text = null}, 200)
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                showItem(item)
            }
        })
        duration = 700
        interpolator = AccelerateDecelerateInterpolator()
        start()
    }
    }

    private fun rotateItems(list: TextView, indexToShow: Int) {
        val rotateY = ObjectAnimator.ofFloat(list, View.ROTATION_Y, -740f, 0f).apply {
            disableViewDuringAnimation(list)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    for (i in mItems.indices) {
                        showItem(mItems[i])
                    }
                }
            })
        }
        val rotateX = ObjectAnimator.ofFloat(list, View.ROTATION_Y, 740f, 0f).apply {
            disableViewDuringAnimation(list)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    hideAllItems()
                }
            })
        }

    AnimatorSet().apply {
        playSequentially(rotateY, rotateX)
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                hideAllItems()
                mItems.shuffle()
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                showItem(mItems[indexToShow])
            }
        })
        duration = 700
        interpolator = AccelerateDecelerateInterpolator()
        start()
    }
    }

    private fun hideAllItems() {
        for (i in mItems.indices){
            Handler().postDelayed({mItems[i].parent.text = null},200)
        }
    }

    private fun showItem(item: ListItemModel){
        //item.background.setBackgroundResource(R.drawable.ic_dice_1)
            item.parent.text = item.text
            item.isSelected = true
    }


  private fun MutableList<ListItemModel>.shuffle(){
       val listText = mutableListOf<String>()
       for (i in this.indices){
           listText.add(mItems[i].text)
       }
       listText.shuffle()

       for (i in listText.indices){
           mItems[i].text = listText[i]
       }
   }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}





