package com.reift.storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.mapper.StoryMapper.mapStory
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class StoryPagingSource(val apiService: ApiService, val authToken: String) :
    RxPagingSource<Int, Story>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Story>> {
        val position = params.key ?: 1

        return apiService.getStories(authToken, position, params.loadSize)
            .subscribeOn(Schedulers.io())
            .map { it.mapStory() }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: List<Story>, position: Int): LoadResult<Int, Story> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (data.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}