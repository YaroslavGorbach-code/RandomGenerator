package com.example.yaroslavgorbach.randomizer.data.themePref

interface ThemeStorage {
    fun changeTheme(color: Int)
    fun getTheme(): Int
    fun changeNightMode(boolean: Boolean)
    fun getNightMode(): Boolean
}