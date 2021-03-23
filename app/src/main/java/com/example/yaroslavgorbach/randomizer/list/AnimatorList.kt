package com.example.yaroslavgorbach.randomizer.list

import android.graphics.Color
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.yaroslavgorbach.randomizer.R


class AnimatorList(parent: ConstraintLayout, finalItem: TextView){
    private val mItems = mutableListOf<ListItemModel>()
    private val mParent = parent
    private val mFinalItem = finalItem
    private var mFinalItemIsOnScreen = false
    private val mAnimationsList = AnimationsList()


    private fun showResult(item: ListItemModel){
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

    private fun showFinalItem(item: ListItemModel){
        mAnimationsList.showFinalItemAnimation(mFinalItem, onAnimationStart = {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && !mFinalItemIsOnScreen) {
                mAnimationsList.setDarkForeground(mParent)
            }
            mFinalItem.background.setTint(item.color)
            mFinalItem.text = item.text
            mFinalItem.movementMethod = ScrollingMovementMethod()
            mFinalItem.visibility = View.VISIBLE
        })
        mFinalItemIsOnScreen = true
    }

    fun hideFinalItem(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && mFinalItemIsOnScreen) {
            mAnimationsList.setLightForeground(mParent)
        }
        mAnimationsList.hideFinalItemAnimation(mFinalItem, onAnimationEnd = {
                mFinalItem.visibility = View.GONE
                mFinalItem.text = null
            })
        mFinalItemIsOnScreen = false
    }

    fun inflateItems(parent: ViewGroup, button: View, listOfItems: MutableList<String>) {
        listOfItems.shuffle()
        val inflater = LayoutInflater.from(parent.context)
        for (i in listOfItems.indices) {
            val itemV: View = inflater.inflate(R.layout.list_i, parent, false)
            val listItemTv: TextView = itemV.findViewById(R.id.list_item)
            val color = Color.argb(255, (0..200).random(), (0..100).random(), (0..200).random())
            listItemTv.background.setTint(color)
            val listItem = ListItemModel(listItemTv, listOfItems[i], false, color)

            itemV.setOnClickListener {
                if (!mFinalItemIsOnScreen){
                    if (!listItem.isSelected){
                        mAnimationsList.manuallyShowItemRotate(listItem.parent, button, object: ListAnimationsManuallyListener {
                            override fun showItemText() {
                                listItem.parent.text = listItem.text
                            }
                            override fun hideItemText() {
                                if (!listItem.isSelected)
                                   listItem.parent.text = null
                            }
                            override fun showResult() {
                                showResult(listItem)
                            }
                        })
                    }else{
                        showFinalItem(listItem)
                    }
                }else{
                    hideFinalItem()
                }

            }

            mItems.add(listItem)
            parent.addView(itemV)
        }
    }

    fun showResult(button: View) {
        hideFinalItem()
        mAnimationsList.buttonRotateAnimation(button)
        val indexToShow = (mItems.indices).random()
        if(mItems.size<=9){
            for (i in mItems.indices){
                mAnimationsList.autoShowItemRotate(mItems[i].parent, button, object : ListAnimationsRandomListener{
                    override fun showAllItemsText() {
                        for (item in mItems.indices) {
                            mItems[item].parent.text = mItems[item].text
                        }
                    }

                    override fun hideAllItemsText() {
                        for (item in mItems.indices){
                            mItems[item].parent.text = null
                        }
                    }

                    override fun hideAllItems() {
                        for (item in mItems.indices){
                            mItems[item].isSelected = false
                            mItems[item].parent.text = null
                        }
                        mItems.shuffle()
                    }

                    override fun showResult() {
                        showResult(mItems[indexToShow])
                        showFinalItem(mItems[indexToShow])
                    }
                })
            }
        }else{
            for (i in mItems.indices){
                if (mItems[i].isSelected){
                    mAnimationsList.hideItem(mItems[i].parent, onHideItem = {
                        mItems[i].parent.text = null
                        mItems[i].isSelected = false
                    })
                }
            }
            mItems.shuffle()

            mAnimationsList.manuallyShowItemRotate(mItems[indexToShow].parent, button,
                    object: ListAnimationsManuallyListener{
                override fun showItemText() {
                    mItems[indexToShow].parent.text = mItems[indexToShow].text
                }
                override fun hideItemText() {
                    if (!mItems[indexToShow].isSelected)
                        mItems[indexToShow].parent.text = null
                }
                override fun showResult() {
                    showResult(mItems[indexToShow])
                    showFinalItem(mItems[indexToShow])
                }

            })
        }

    }

}





