package com.example.yaroslavgorbach.randomizer.data.SoundPref

interface SoundPreferences {
    companion object{
        const val COIN_SOUND_KEY = "COIN_SOUND_KEY"
        const val DICE_SOUND_KEY = "DICE_SOUND_KEY"
        const val LIST_SOUND_KEY = "LIST_SOUND_KEY"
        const val NUMBER_SOUND_KEY = "NUMBER_SOUND_KEY"
        const val MATCHES_SOUND_KEY = "MATCHES_SOUND_KEY"
    }
    fun allowSound(key: String)
    fun disallowSound(key: String)
    fun getState(key: String): Boolean
}