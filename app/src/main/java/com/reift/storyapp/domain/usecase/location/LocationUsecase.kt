package com.reift.storyapp.domain.usecase.location

import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.location.Location
import io.reactivex.rxjava3.core.Flowable

interface LocationUsecase {
    fun getPhotoLocation(): Flowable<Resource<List<Location>>>
}