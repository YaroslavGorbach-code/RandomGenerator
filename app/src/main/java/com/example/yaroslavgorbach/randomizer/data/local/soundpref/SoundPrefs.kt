package com.example.yaroslavgorbach.randomizer.data.local.soundpref

interface SoundPrefs {
    companion object{
        const val COIN_SOUND_KEY = "COIN_SOUND_KEY"
        const val DICE_SOUND_KEY = "DICE_SOUND_KEY"
        const val LIST_SOUND_KEY = "LIST_SOUND_KEY"
        const val NUMBER_SOUND_KEY = "NUMBER_SOUND_KEY"
        const val MATCHES_SOUND_KEY = "MATCHES_SOUND_KEY"
    }
    fun allow(key: String)
    fun disallow(key: String)
    fun getState(key: String): Boolean
}