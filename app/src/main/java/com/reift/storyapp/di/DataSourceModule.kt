package com.reift.storyapp.di

import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.retrofit.ApiService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    factory { LocalDataSource(get()) }
}