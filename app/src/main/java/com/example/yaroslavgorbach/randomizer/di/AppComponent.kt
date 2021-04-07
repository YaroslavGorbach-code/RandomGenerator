package com.example.yaroslavgorbach.randomizer.di

import android.content.Context
import com.example.yaroslavgorbach.randomizer.list.CreateEditListDialog
import com.example.yaroslavgorbach.randomizer.RandomListFragment
import com.example.yaroslavgorbach.randomizer.coin.CoinFragment
import com.example.yaroslavgorbach.randomizer.dice.DiceFragment
import com.example.yaroslavgorbach.randomizer.list.ListFragment
import com.example.yaroslavgorbach.randomizer.matches.MatchesFragment
import com.example.yaroslavgorbach.randomizer.number.NumberFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataModule::class, AppModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: ListFragment)
    fun inject(fragment: RandomListFragment)
    fun inject(dialogFragment: CreateEditListDialog)
    fun inject(coinFragment: CoinFragment)
    fun inject(diceFragment: DiceFragment)
    fun inject(matchesFragment: MatchesFragment)
    fun inject(numberFragment: NumberFragment)


}