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
    // Classes that can be injected by this Component

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: ListFragment)
    fun inject(fragment: RandomListFragment)
    fun inject(dialogFragment: CreateEditListDialog)


}