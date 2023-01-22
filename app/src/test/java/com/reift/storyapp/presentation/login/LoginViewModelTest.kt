package com.reift.storyapp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.repository.auth.AuthUseCaseRepository
import com.reift.storyapp.utils.DummyData
import com.reift.storyapp.utils.getOrAwaitValue
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authUseCaseRepository: AuthUseCaseRepository

    private lateinit var viewModel: LoginViewModel

    private var dummyLogin = DummyData.generatorDummyLogin()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(authUseCaseRepository)
    }

    @Test
    fun `when Login should not error`() {
        val email = "user@gmail.com"
        val password = "user123"

        val expectedLogin = MutableLiveData<Login>()
        expectedLogin.value = dummyLogin

        `when`(authUseCaseRepository.loginUser(email, password)).thenReturn(expectedLogin)

        val actualLogin = viewModel.loginUser(email, password).getOrAwaitValue()
        Mockito.verify(authUseCaseRepository).loginUser(email, password)
        assertNotNull(actualLogin)
        assertEquals(actualLogin.isError, false)
        assertEquals(expectedLogin.value, actualLogin)

    }

    @Test
    fun isUserLogin() {
    }
}