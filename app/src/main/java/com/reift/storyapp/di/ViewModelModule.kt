package com.reift.storyapp.di

import com.reift.storyapp.presentation.detail.DetailViewModel
import com.reift.storyapp.presentation.login.LoginViewModel
import com.reift.storyapp.presentation.home.HomeViewModel
import com.reift.storyapp.presentation.location.LocationViewModel
import com.reift.storyapp.presentation.post.PostViewModel
import com.reift.storyapp.presentation.register.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { PostViewModel(get()) }
    viewModel { LocationViewModel(get()) }
}