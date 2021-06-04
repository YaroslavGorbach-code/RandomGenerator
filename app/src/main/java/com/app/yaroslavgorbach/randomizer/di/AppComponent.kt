package com.app.yaroslavgorbach.randomizer.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.app.yaroslavgorbach.randomizer.App
import com.app.yaroslavgorbach.randomizer.MainActivity
import com.app.yaroslavgorbach.randomizer.screen.list.CreateEditListDialog
import com.app.yaroslavgorbach.randomizer.screen.MainFragment
import com.app.yaroslavgorbach.randomizer.screen.coin.CoinFragment
import com.app.yaroslavgorbach.randomizer.screen.dice.DiceFragment
import com.app.yaroslavgorbach.randomizer.screen.list.ChoseListDialog
import com.app.yaroslavgorbach.randomizer.screen.list.ListFragment
import com.app.yaroslavgorbach.randomizer.screen.matches.MatchesFragment
import com.app.yaroslavgorbach.randomizer.screen.number.NumberFragment
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
    fun inject(fragment: MainFragment)
    fun inject(dialogFragment: CreateEditListDialog)
    fun inject(coinFragment: CoinFragment)
    fun inject(diceFragment: DiceFragment)
    fun inject(matchesFragment: MatchesFragment)
    fun inject(numberFragment: NumberFragment)
    fun inject(choseListDialog: ChoseListDialog)
    fun inject(activity: MainActivity)

}

val Fragment.appComponent get() =
    (activity?.application as App).appComponent
val Activity.appComponent get() =
    (application as App).appComponent