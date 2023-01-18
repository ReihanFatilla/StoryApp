package com.reift.storyapp.presentation.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.location.Location
import com.reift.storyapp.domain.usecase.location.LocationUsecase

class LocationViewModel(
    val locationUsecase: LocationUsecase
): ViewModel() {

    fun getPhotoLocation(): LiveData<Resource<List<Location>>>{
        return LiveDataReactiveStreams.fromPublisher(locationUsecase.getPhotoLocation())
    }

}