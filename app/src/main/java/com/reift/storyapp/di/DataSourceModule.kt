package com.reift.storyapp.di

import com.reift.storyapp.data.local.LocalDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { LocalDataSource(get(), get()) }
}