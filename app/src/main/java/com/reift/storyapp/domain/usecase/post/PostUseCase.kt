package com.reift.storyapp.domain.usecase.post

import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.post.Post
import io.reactivex.rxjava3.core.Flowable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import java.io.File
import java.util.concurrent.Flow

interface PostUseCase {
    fun postStory(description: String, photo: File): Flowable<Resource<Post>>
}