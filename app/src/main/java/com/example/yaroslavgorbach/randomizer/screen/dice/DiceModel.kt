package com.example.yaroslavgorbach.randomizer.screen.dice

import android.widget.ImageView

data class DiceModel(var imageView: ImageView, var points: Int, var sideIsChanged:Boolean = false)