package com.example.yaroslavgorbach.randomizer.screen

import androidx.fragment.app.Fragment

interface Navigation {
    fun openCoin()
    fun openList(title: String)
    fun openNumber(max: Long, min: Long, results: Long)
    fun openDice(number: Int)
    fun openMatches(number: Int, burned: Int)

}
val Fragment.nav get() = activity as Navigation
