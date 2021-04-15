package com.example.yaroslavgorbach.randomizer.util

import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.toLong(): Long =
    text.toString().toLong()

fun TextInputEditText.toInt(): Int =
    text.toString().toInt()
