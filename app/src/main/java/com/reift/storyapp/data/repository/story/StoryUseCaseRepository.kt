package com.reift.storyapp.data.repository.story

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.reift.storyapp.data.StoryPagingSource
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.domain.usecase.story.StoryUseCase
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