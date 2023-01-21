package com.reift.storyapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.reift.storyapp.data.repository.story.StoryUseCaseRepository
import com.reift.storyapp.domain.entity.story.Story
import com.reift.storyapp.presentation.home.adapter.StoryRxAdapter
import com.reift.storyapp.utils.CoroutinesTestRule
import com.reift.storyapp.utils.DummyData
import com.reift.storyapp.utils.PagedTestDataSource
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var storyUseCaseRepository: StoryUseCaseRepository

    private lateinit var homeViewModel: HomeViewModel
    private val dummyStory = DummyData.generateDummyStory()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(storyUseCaseRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when get story should not return null and return success`() {
        val storyPaging = PagedTestDataSource.snapshot(dummyStory)
        val expectedStory = Flowable.just(storyPaging)
        `when`(storyUseCaseRepository.getAllStories()).thenReturn(expectedStory)

        homeViewModel.getAllStories().subscribe { actualStory ->
            runTest{
                val differ = AsyncPagingDataDiffer(
                    diffCallback = StoryRxAdapter.DIFF_CALLBACK,
                    updateCallback = noopListUpdateCallback,
                    mainDispatcher = coroutinesTestRule.testDispatcher,
                    workerDispatcher = coroutinesTestRule.testDispatcher
                )

                differ.submitData(actualStory)

                Mockito.verify(storyUseCaseRepository).getAllStories()
                assertNotNull(differ.snapshot())
                assertEquals(dummyStory.size, differ.snapshot().size)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when Story Empty Should Return No Data`() {
        val emptyStoryPaging: PagingData<Story> = PagingData.from(emptyList())
        val expectedQuote = Flowable.just(emptyStoryPaging)
        `when`(storyUseCaseRepository.getAllStories()).thenReturn(expectedQuote)

        homeViewModel.getAllStories().subscribe { actualStory ->
            runTest{
                val differ = AsyncPagingDataDiffer(
                    diffCallback = StoryRxAdapter.DIFF_CALLBACK,
                    updateCallback = noopListUpdateCallback,
                    mainDispatcher = coroutinesTestRule.testDispatcher,
                    workerDispatcher = coroutinesTestRule.testDispatcher
                )

                differ.submitData(actualStory)

                assertEquals(0, differ.snapshot().size)
            }
        }
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
    
}