package com.example.yaroslavgorbach.randomizer.number

import android.content.Context
import android.graphics.Point
import android.view.Display
import android.view.WindowManager


class NumberUtils {
    companion object{
         fun getScreenHeight(context: Context): Int {
             val display = context.display
             val size = Point()
              display?.getRealSize(size)
             return size.x
         }
    }
}