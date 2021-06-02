package com.example.yaroslavgorbach.randomizer.feature

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.example.yaroslavgorbach.randomizer.data.local.soundPref.SoundPrefs
import com.example.yaroslavgorbach.randomizer.R
import javax.inject.Inject


class SoundManager @Inject constructor(context: Context, soundPrefs: SoundPrefs) {

    private var mSoundPool: SoundPool
    private var mSoundFlipCoin = 0
    private var mSoundFallCoin = 0
    private var mSoundRollAllDices = 0
    private var mSoundNumberSwipe = 0
    private var mSoundListBell = 0
    private var mSoundMatch = 0
    private val mSoundPreferences = soundPrefs

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()

        mSoundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(audioAttributes)
            .build()

        mSoundRollAllDices = mSoundPool.load(context, R.raw.roll_all_dices, 1)
        mSoundNumberSwipe = mSoundPool.load(context, R.raw.number_swipe, 1)
        mSoundFallCoin = mSoundPool.load(context, R.raw.fall_coin, 1)
        mSoundFlipCoin = mSoundPool.load(context, R.raw.flip_coin, 1)
        mSoundListBell = mSoundPool.load(context, R.raw.list_bell, 1)
        mSoundMatch = mSoundPool.load(context, R.raw.match, 1)
    }


    fun flipCoinSoundPlay() {
        if (mSoundPreferences.getState(SoundPrefs.COIN_SOUND_KEY))
        mSoundPool.play(mSoundFlipCoin, 1f, 1f, 1, 0, 1f)
    }

    fun fallCoinSoundPlay() {
        if (mSoundPreferences.getState(SoundPrefs.COIN_SOUND_KEY))
            mSoundPool.play(mSoundFallCoin, 1f, 1f, 1, 0, 1f)
    }

    fun rollAllDicesSoundPlay() {
        if (mSoundPreferences.getState(SoundPrefs.DICE_SOUND_KEY))
            mSoundPool.play(mSoundRollAllDices, 1f, 1f, 1, 0, 1f)
    }

    fun numberSwipeSoundPlay() {
        if (mSoundPreferences.getState(SoundPrefs.NUMBER_SOUND_KEY))
        mSoundPool.play(mSoundNumberSwipe, 1f, 1f, 1, 0, 1f)
    }

    fun listBellSoundPlay() {
        if (mSoundPreferences.getState(SoundPrefs.LIST_SOUND_KEY))
        mSoundPool.play(mSoundListBell, 1f, 1f, 1, 0, 1f)
    }

    fun matchSoundPlay(){
        if (mSoundPreferences.getState(SoundPrefs.MATCHES_SOUND_KEY))
        mSoundPool.play(mSoundMatch, 1f, 1f, 1, 0, 1f)
    }
}