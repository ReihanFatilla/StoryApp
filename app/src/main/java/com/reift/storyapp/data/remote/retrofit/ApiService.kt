package com.reift.storyapp.data.remote.retrofit

import com.reift.storyapp.data.remote.response.login.LoginResponse
import com.reift.storyapp.data.remote.response.post.Post
import com.reift.storyapp.data.remote.response.register.Register
import com.reift.storyapp.data.remote.response.story.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") authToken: String
    ) : Call<StoryResponse>

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<Register>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @FormUrlEncoded
    @POST("stories")
    fun postStory(
        @Header("Authorization") userToken: String,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : Call<Post>
}