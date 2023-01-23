package com.reift.storyapp.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.data.repository.auth.AuthUseCaseRepository
import com.reift.storyapp.utils.DummyData
import com.reift.storyapp.utils.getOrAwaitValue
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authUseCaseRepository: AuthUseCaseRepository

    private lateinit var viewModel: RegisterViewModel

    private var dummyRegister = DummyData.generatorDummyRegister()

    @Before
    fun setUp() {
        viewModel = RegisterViewModel(authUseCaseRepository)
    }

    @Test
    fun `when Register should not error`() {
        val name = "user"
        val email = "user@gmail.com"
        val password = "user123"

        val expectedRegister = MutableLiveData<Register>()
        expectedRegister.value = dummyRegister

        Mockito.`when`(authUseCaseRepository.registerUser(name, email, password)).thenReturn(expectedRegister)

        val actualRegister = viewModel.registerUser(name, email, password).getOrAwaitValue()
        Mockito.verify(authUseCaseRepository).registerUser(name, email, password)
        Assert.assertNotNull(actualRegister)
        Assert.assertEquals(actualRegister.isError, false)
        Assert.assertEquals(expectedRegister.value, actualRegister)
    }
}