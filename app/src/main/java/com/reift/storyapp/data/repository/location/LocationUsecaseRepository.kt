package com.reift.storyapp.data.repository.location

import com.reift.storyapp.data.NetworkResource
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.response.story.StoryResponse
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.location.Location
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.domain.usecase.location.LocationUsecase
import com.reift.storyapp.mapper.StoryMapper.mapLocation
import io.reactivex.rxjava3.core.Flowable

class LocationUsecaseRepository(
    val apiService: ApiService,
    val localDataSource: LocalDataSource
): LocationUsecase {

    private val authToken = "Bearer " + localDataSource.getAuthToken()

    override fun getPhotoLocation(): Flowable<Resource<List<Location>>> {
        return object: NetworkResource<List<Location>, StoryResponse>(){
            override fun createResult(data: StoryResponse): List<Location> {
                return data.mapLocation()
            }

            override fun createCall(): Flowable<StoryResponse> {
                return apiService.getStoriesLocation(authToken)
            }

        }.asFlowable()
    }
}
