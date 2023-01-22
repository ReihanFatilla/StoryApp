package com.reift.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import androidx.room.withTransaction
import com.reift.storyapp.data.StoryPagingSource.Companion.INITIAL_PAGE_INDEX
import com.reift.storyapp.data.local.room.database.StoryDatabase
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.mapper.StoryMapper.mapEntity
import com.reift.storyapp.mapper.StoryMapper.mapStory
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    val database: StoryDatabase,
    val apiService: ApiService,
    val authToken: String
) : RxRemoteMediator<Int, Story>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Story>
    ): Single<MediatorResult> {
        return apiService.getStories(authToken, INITIAL_PAGE_INDEX, state.config.pageSize)
            .subscribeOn(Schedulers.io())
            .map {
                val endOfPaginationReached = (it.listStory ?: 0) == 0
                CoroutineScope(Dispatchers.IO).launch {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.storyDao().deleteAll()
                        }
                        database.storyDao().insertQuote(it.mapEntity())
                    }
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}