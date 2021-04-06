package com.example.yaroslavgorbach.randomizer.themes.themeStorage

interface ThemeStorage {
    fun changeTheme(color: Int)
    fun getTheme(): Int
    fun changeNightMode(boolean: Boolean)
    fun getNightMode(): Boolean
}