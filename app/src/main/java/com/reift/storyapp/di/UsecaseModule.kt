package com.reift.storyapp.di

import com.reift.storyapp.data.repository.auth.AuthUseCaseRepository
import com.reift.storyapp.data.repository.post.PostUseCaseRepository
import com.reift.storyapp.data.repository.story.StoryUseCaseRepository
import com.reift.storyapp.domain.usecase.auth.AuthUseCase
import com.reift.storyapp.domain.usecase.post.PostUseCase
import com.reift.storyapp.domain.usecase.story.StoryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<AuthUseCase> { AuthUseCaseRepository(get(), get()) }
    single<PostUseCase> { PostUseCaseRepository(get(), get()) }
    single<StoryUseCase> { StoryUseCaseRepository(get(), get()) }
}