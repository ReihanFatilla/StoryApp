package com.reift.storyapp.data.repository.story

import com.reift.storyapp.data.NetworkResource
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.response.story.Story
import com.reift.storyapp.data.remote.response.story.StoryResponse
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.usecase.story.StoryUseCase
import com.reift.storyapp.mapper.StoryMapper.map
import io.reactivex.rxjava3.core.Flowable

class StoryUseCaseRepository(
    val apiService: ApiService,
    val localDataSource: LocalDataSource
): StoryUseCase {

    val authToken = localDataSource.getAuthToken()

    override fun getAllStories(): Flowable<Resource<List<Story>>> {
        return object: NetworkResource<List<Story>, StoryResponse>(){
            override fun createResult(data: StoryResponse): List<Story> {
                return data.map()
            }

            override fun createCall(): Flowable<StoryResponse> {
                return apiService.getStories(authToken)
            }

        }.asFlowable()
    }
}