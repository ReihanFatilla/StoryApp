package com.reift.storyapp.data.repository.story

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.rxjava3.flowable
import com.reift.storyapp.data.NetworkResource
import com.reift.storyapp.data.StoryPagingSource
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.response.story.StoryResponse
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.domain.usecase.story.StoryUseCase
import com.reift.storyapp.mapper.StoryMapper.mapStory
import io.reactivex.rxjava3.core.Flowable

class StoryUseCaseRepository(
    val apiService: ApiService,
    val localDataSource: LocalDataSource
): StoryUseCase {

    val authToken = "Bearer "+ localDataSource.getAuthToken()

    override fun getAllStories(): Flowable<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, authToken)
            }
        ).flowable

    }

    override fun logout() {
        localDataSource.logout()
    }
}