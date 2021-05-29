package com.example.yaroslavgorbach.randomizer.feature.colorPicker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.yaroslavgorbach.randomizer.R
import com.example.yaroslavgorbach.randomizer.util.ThemesUtils

class ScrollColorPicker(content: ViewGroup) : ColorPicker {
    private val colors: IntArray = intArrayOf(
        ContextCompat.getColor(content.context, R.color.colorAccent) ,
        ContextCompat.getColor(content.context, R.color.colorAccent_red),
        ContextCompat.getColor(content.context, R.color.colorAccent_orange),
        ContextCompat.getColor(content.context, R.color.colorAccent_blue),
        ContextCompat.getColor(content.context, R.color.colorAccent_yellow),
        ContextCompat.getColor(content.context, R.color.colorAccent_purple),
        ContextCompat.getColor(content.context, R.color.colorAccent_blue_l),
    )
    private val marks: Array<View?> = arrayOfNulls(colors.size)
    private var mSelectedColor = 0

    init {
        val inflater = LayoutInflater.from(content.context)
        colors.indices.forEach {i->
            val item: View = inflater.inflate(R.layout.color_picker_i, content, false)
            val bg = item.findViewById<ImageView>(R.id.color)
            val mark = item.findViewById<ImageView>(R.id.mark)
            marks[i] = mark
            val color = colors[i]
            bg.setColorFilter(color)
            item.setOnClickListener { setColor(color) }
            content.addView(item, i)
        }
        setColor(ThemesUtils.getCurrentAccentColor(content.context))
    }

    override fun setColor(color: Int) {
        mSelectedColor = color
        colors.indices.forEach {i->
            if (color == colors[i]) {
                marks[i]!!.visibility = View.VISIBLE
            } else {
                marks[i]!!.visibility = View.GONE
            }
        }
    }

    override fun getColor(context: Context): Int {
       return mSelectedColor
    }

}