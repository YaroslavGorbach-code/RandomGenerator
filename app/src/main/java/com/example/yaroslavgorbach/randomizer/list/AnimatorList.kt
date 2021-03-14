package com.example.yaroslavgorbach.randomizer.list

import android.animation.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.disableViewDuringAnimation
import java.util.*


class AnimatorList(parent: ConstraintLayout, finalItem: TextView){
    private val mItems = mutableListOf<ListItemModel>()
    private val mParent = parent
    private val mFinalItem = finalItem
    private var mIsForegroundDark = false

    private fun manuallyShowItemRotate(item: ListItemModel) {
        if (!mIsForegroundDark){
            if(!item.isSelected) {
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
                                Handler().postDelayed({ item.parent.text = null }, 200)
                        }
                    })
                }

                AnimatorSet().apply {
                    playSequentially(rotateY, rotateX)
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator?) {
                            super.onAnimationStart(animation)
                            if (!item.isSelected)
                                Handler().postDelayed({ item.parent.text = null }, 200)
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
            }else{
                showFinalItem(item)
            }
        }else{
            hideFinalItem()
        }

    }

    private fun autoShowItemRotate(list: TextView, indexToShow: Int) {
        val rotateY = ObjectAnimator.ofFloat(list, View.ROTATION_Y, -740f, 0f).apply {
            disableViewDuringAnimation(list)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    for (i in mItems.indices) {
                        mItems[i].parent.text = mItems[i].text
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
                    showFinalItem(mItems[indexToShow])
                }
            })
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }


    private fun setDarkForeground(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && !mIsForegroundDark) {
            ValueAnimator.ofInt(0, 200).apply {
                addUpdateListener { animator ->
                    mParent.foreground = (ColorDrawable(Color.argb(animator.animatedValue as Int, 0, 0, 0)))
                }
                duration = 300
                start()
            }

            mIsForegroundDark = true
        }
    }

    private fun setLightForeground(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && mIsForegroundDark) {
            ValueAnimator.ofInt(200, 0).apply {
                addUpdateListener { animator ->
                    mParent.foreground = (ColorDrawable(Color.argb(animator.animatedValue as Int, 0, 0, 0)))
                }
                duration = 300
                start()
                mIsForegroundDark = false
            }
        }
    }

    private fun showFinalItem(item: ListItemModel) {
        setDarkForeground()
        mFinalItem.setBackgroundColor(item.color)
        mFinalItem.text = item.text
        mFinalItem.visibility = View.VISIBLE

        val scaleX = ObjectAnimator.ofFloat(mFinalItem, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(mFinalItem, View.SCALE_Y, 0f, 1f)
        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 300
            start()
        }
    }

     fun hideFinalItem(){
        setLightForeground()
        val scaleX = ObjectAnimator.ofFloat(mFinalItem, View.SCALE_X, 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(mFinalItem, View.SCALE_Y, 1f, 0f)
        AnimatorSet().apply {
            addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mFinalItem.visibility = View.GONE
                    mFinalItem.text = null
                }
            })
            playTogether(scaleX, scaleY)
            duration = 300
            start()
        }
    }

    private fun hideAllItems() {
        for (i in mItems.indices){
            Handler().postDelayed({ mItems[i].parent.text = null }, 200)
        }
    }

    private fun showItem(item: ListItemModel){
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
            mItems[i].isSelected = false
        }
    }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    fun inflateItems(parent: ViewGroup, listOfItems: MutableList<String>) {
        listOfItems.shuffle()
        val inflater = LayoutInflater.from(parent.context)
        for (i in listOfItems.indices) {
            val itemV: View = inflater.inflate(R.layout.list_i, parent, false)
            val listItemBackground: TextView = itemV.findViewById(R.id.list_item)
            val color = getRandomColor()
            listItemBackground.setBackgroundColor(color)
            val listItem = ListItemModel(listItemBackground, listOfItems[i], false, color)
            itemV.setOnClickListener {
                manuallyShowItemRotate(listItem)
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
        hideFinalItem()
    }

    fun showItem() {
        val indexToShow = (mItems.indices).random()
        for (i in mItems.indices){
            autoShowItemRotate(mItems[i].parent, indexToShow)
        }
    }

}





