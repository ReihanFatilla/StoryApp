package com.reift.storyapp.di

import androidx.room.Room
import com.reift.storyapp.data.local.room.database.StoryDatabase
import com.reift.storyapp.data.local.sharedpreferences.PreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { PreferenceHelper(androidContext()) }
    single {
        Room.databaseBuilder(
            androidContext(),
            StoryDatabase::class.java, "story.database"
        ).fallbackToDestructiveMigration().build()
    }
}