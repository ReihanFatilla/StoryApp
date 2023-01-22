package com.reift.storyapp.data.repository.story

import androidx.paging.*
import androidx.paging.rxjava3.flowable
import com.reift.storyapp.data.StoryRemoteMediator
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.domain.usecase.story.StoryUseCase
import com.reift.storyapp.mapper.StoryMapper.mapStory
import io.reactivex.rxjava3.core.Flowable

class StoryUseCaseRepository(
    val apiService: ApiService,
    val localDataSource: LocalDataSource
) : StoryUseCase {

    val authToken = "Bearer " + localDataSource.getAuthToken()

    override fun getAllStories(): Flowable<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5
            ),
            remoteMediator = StoryRemoteMediator(
                localDataSource.storyDatabase,
                apiService,
                authToken
            ),
            pagingSourceFactory = {
                localDataSource.storyDatabase.storyDao().getAllStory()
            }
        ).flowable.map { it.map { it.mapStory() } }
    }

    override fun logout() {
        localDataSource.logout()
    }
}