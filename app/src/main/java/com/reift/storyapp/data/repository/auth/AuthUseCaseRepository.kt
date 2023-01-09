package com.reift.storyapp.data.repository.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.response.login.Login
import com.reift.storyapp.data.remote.response.login.LoginResponse
import com.reift.storyapp.data.remote.response.login.LoginResult
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.data.remote.response.register.RegisterResponse
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.usecase.auth.AuthUseCase
import com.reift.storyapp.mapper.AuthMapper.map
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthUseCaseRepository(
    val apiService: ApiService,
    val localDataSource: LocalDataSource
): AuthUseCase {

    override fun registerUser(
        name: String,
        email: String,
        password: String
    ): LiveData<Register> {
        val register = MutableLiveData<Register>()
        apiService.registerUser(name, email, password).enqueue(
            object: Callback<RegisterResponse>{
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if(response.isSuccessful){
                        register.value = response.body()?.map()
                    } else {
                        register.value = RegisterResponse(error = true, message = "Please use valid email format").map()
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    register.value = Register(true, t.message.toString())
                }

            }
        )
        return register
    }

    override fun loginUser(email: String, password: String): LiveData<Login> {
        val login = MutableLiveData<Login>()
        apiService.loginUser(email, password).enqueue(
            object: Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful) {
                        val body = response.body()?.map()
                        login.value = response.body()?.map()
                        if(body?.isError == false){
                            localDataSource.login(body.token)
                        }
                    } else {
                        login.value = LoginResponse(LoginResult(), error = true, message = "Incorrect email or password").map()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    login.value = LoginResponse(LoginResult(), error = true, message = t.message.toString()).map()
                }

            }
        )
        return login
    }

    override fun isUserLogin(): Boolean {
        return localDataSource.isUserLogin()
    }

}