package com.reift.storyapp.domain.usecase.post

import com.google.android.gms.maps.model.LatLng
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.post.Post
import io.reactivex.rxjava3.core.Flowable
import java.io.File

interface PostUseCase {
    fun postStory(description: String, photo: File, latLng: LatLng? = null): Flowable<Resource<Post>>
}