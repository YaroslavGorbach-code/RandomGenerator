package com.example.yaroslavgorbach.randomizer.screen

import androidx.fragment.app.Fragment

interface Navigation {
    fun openCoin()
    fun openList(title: String)
    fun openNumber(max: Long, min: Long, results: Long)
    fun openDice(number: Int)
    fun openMatches(number: Int, burned: Int)
    fun showMatchesDialog()
    fun showNumberDialog()
    fun showDicesDialog()
    fun showChangeThemeDialog()
    fun showChoseListDialog()
    fun showCreateEditListDialog(title: String?)
}
val Fragment.nav get() = activity as Navigation
