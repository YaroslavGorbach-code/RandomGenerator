package com.example.yaroslavgorbach.randomizer.data.ThemePref

interface ThemeStorage {
    fun changeTheme(color: Int)
    fun getTheme(): Int
    fun changeNightMode(boolean: Boolean)
    fun getNightMode(): Boolean
}