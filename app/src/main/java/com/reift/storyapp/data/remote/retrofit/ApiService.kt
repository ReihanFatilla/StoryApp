package com.reift.storyapp.data.remote.retrofit

import com.reift.storyapp.data.remote.response.login.LoginResponse
import com.reift.storyapp.data.remote.response.post.PostResponse
import com.reift.storyapp.data.remote.response.register.RegisterResponse
import com.reift.storyapp.data.remote.response.story.StoryResponse
import io.reactivex.rxjava3.core.Flowable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("stories")
    fun getStories(
        @Header("Authorization") authToken: String
    ) : Flowable<StoryResponse>

    @GET("stories")
    fun getStoriesLocation(
        @Header("Authorization") authToken: String,
        @Query("location") lat: String = "1",
    ) : Flowable<StoryResponse>

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>

    @Multipart
    @POST("stories")
    fun postStory(
        @Header("Authorization") userToken: String,
        @Part image: MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : Flowable<PostResponse>

}