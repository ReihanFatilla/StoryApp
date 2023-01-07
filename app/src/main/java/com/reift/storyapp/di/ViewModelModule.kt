package com.reift.storyapp.di

import com.reift.storyapp.domain.usecase.story.StoryUseCase
import com.reift.storyapp.presentation.detail.DetailViewModel
import com.reift.storyapp.presentation.login.LoginViewModel
import com.reift.storyapp.presentation.main.MainViewModel
import com.reift.storyapp.presentation.post.PostViewModel
import com.reift.storyapp.presentation.register.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { PostViewModel(get()) }
}