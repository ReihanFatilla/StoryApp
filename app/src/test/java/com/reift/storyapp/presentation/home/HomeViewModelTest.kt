package com.reift.storyapp.presentation.home

import com.reift.storyapp.data.repository.story.StoryUseCaseRepository
import com.reift.storyapp.domain.usecase.story.StoryUseCase
import com.reift.storyapp.utils.DummyData
import com.reift.storyapp.utils.PagedTestDataSource
import io.reactivex.rxjava3.core.Flowable
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var storyUseCaseRepository: StoryUseCaseRepository

    private lateinit var homeViewModel: HomeViewModel
    private val dummyStory = DummyData.generateDummyStory()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(storyUseCaseRepository)
    }

    @Test
    fun `when get story should not return null and return success`() {
        val storyPaging = PagedTestDataSource.snapshot(dummyStory)
        val expectedStory = Flowable.just(storyPaging)
        `when`(storyUseCaseRepository.getAllStories()).thenReturn(expectedStory)

        homeViewModel.getAllStories().subscribe { actualKisah ->
            Mockito.verify(storyUseCaseRepository).getAllStories()
            assertNotNull(actualKisah)
        }
    }
}