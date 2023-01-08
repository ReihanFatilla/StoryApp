package com.reift.storyapp.data.repository.auth

import com.reift.storyapp.data.NetworkResource
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.login.LoginResponse
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.data.remote.response.register.RegisterResponse
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.usecase.auth.AuthUseCase
import com.reift.storyapp.mapper.AuthMapper.map
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class AuthUseCaseRepository(
    val apiService: ApiService,
    val localDataSource: LocalDataSource
): AuthUseCase {

    override fun registerUser(
        name: String,
        email: String,
        password: String
    ): Flowable<Resource<Register>> {
        return object : NetworkResource<Register, RegisterResponse>(){
            override fun createResult(data: RegisterResponse): Register {
                return data.map()
            }

            override fun createCall(): Flowable<RegisterResponse> {
                return apiService.registerUser(name, email, password)
            }

        }.asFlowable()
    }

    override fun loginUser(email: String, password: String): Flowable<Resource<Login>> {
        return object : NetworkResource<Login, LoginResponse>(){
            override fun createResult(data: LoginResponse): Login {
                return data.map()
            }

            override fun createCall(): Flowable<LoginResponse> {
                val result = apiService.loginUser(email, password)

                result.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            if(it.error == false){
                                localDataSource.login(it.loginResult.token.orEmpty())
                            }
                        },
                        {}
                    )

                return result
            }

        }.asFlowable()
    }

    override fun isUserLogin(): Boolean {
        return localDataSource.isUserLogin()
    }

}