package com.reift.storyapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import androidx.room.withTransaction
import com.reift.storyapp.data.local.room.database.StoryDatabase
import com.reift.storyapp.data.local.room.entity.RemoteKeys
import com.reift.storyapp.data.local.room.entity.StoryEntity
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.mapper.StoryMapper.mapEntity
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
) : RxRemoteMediator<Int, StoryEntity>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, StoryEntity>
    ): Single<MediatorResult> {

        val page = when (loadType) {
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKeys?.prevKey
                    ?: return Single.just(MediatorResult.Success(endOfPaginationReached = remoteKeys != null))
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)

                val nextKey = remoteKeys?.nextKey
                    ?: return Single.just(MediatorResult.Success(endOfPaginationReached = remoteKeys != null))
                nextKey
            }
        }

        return apiService.getStories(authToken, page, state.config.pageSize)
            .subscribeOn(Schedulers.io())
            .map {
                val endOfPaginationReached = (it.listStory ?: 0) == 0
                CoroutineScope(Dispatchers.IO).launch {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.remoteKeysDao().deleteRemoteKeys()
                            database.storyDao().deleteAll()
                        }
                        val prevKey = if (page == 1) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1
                        val keys = it.listStory?.map {
                            RemoteKeys(id = it.id.orEmpty(), prevKey = prevKey, nextKey = nextKey)
                        }
                        keys?.let { listRemote -> database.remoteKeysDao().insertAll(listRemote) }
                        database.storyDao().insertStory(it.mapEntity())
                    }
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, StoryEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

}