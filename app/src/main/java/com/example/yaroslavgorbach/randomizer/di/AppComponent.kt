package com.example.yaroslavgorbach.randomizer.di

import android.content.Context
import androidx.fragment.app.DialogFragment
import com.example.yaroslavgorbach.randomizer.CreateEditListDialog
import com.example.yaroslavgorbach.randomizer.RandomListFragment
import com.example.yaroslavgorbach.randomizer.list.ListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RoomModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: ListFragment)
    fun inject(fragment: RandomListFragment)
    fun inject(dialogFragment: CreateEditListDialog)


}