package com.reift.storyapp.presentation.post

import androidx.lifecycle.ViewModel
import com.reift.storyapp.domain.usecase.post.PostUseCase

class PostViewModel(
    val postUseCase: PostUseCase
): ViewModel() {
}