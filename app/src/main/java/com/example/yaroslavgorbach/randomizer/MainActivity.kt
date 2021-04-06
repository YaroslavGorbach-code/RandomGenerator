package com.example.yaroslavgorbach.randomizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yaroslavgorbach.randomizer.themes.ThemesUtils


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemesUtils.setCurrentTheme(this)
        setContentView(R.layout.activity_main)
    }
}
