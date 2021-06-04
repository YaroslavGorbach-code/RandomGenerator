package com.app.yaroslavgorbach.randomizer.component.coin

import android.widget.ImageView
import androidx.lifecycle.LiveData

interface Coin {
    fun getSoundIsAllow(): LiveData<Boolean>
    fun disallowSound()
    fun allowSound()
    fun animate(coin: ImageView, fon: ImageView)
}