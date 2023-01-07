package com.reift.storyapp.di

import com.reift.storyapp.data.local.sharedpreferences.PreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    factory { PreferenceHelper(androidContext()) }
}