package com.reift.storyapp.presentation.detail

import androidx.lifecycle.ViewModel
import com.reift.storyapp.domain.usecase.story.StoryUseCase

class DetailViewModel(
    val storyUseCase: StoryUseCase
): ViewModel()