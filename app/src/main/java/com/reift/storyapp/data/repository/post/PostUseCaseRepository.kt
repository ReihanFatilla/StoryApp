package com.reift.storyapp.data.repository.post

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.reift.storyapp.data.NetworkResource
import com.reift.storyapp.data.local.LocalDataSource
import com.reift.storyapp.data.remote.response.post.PostResponse
import com.reift.storyapp.data.remote.retrofit.ApiService
import com.reift.storyapp.domain.entity.Resource
import com.reift.storyapp.domain.entity.post.Post
import com.reift.storyapp.domain.usecase.post.PostUseCase
import com.reift.storyapp.mapper.PostMapper.map
import io.reactivex.rxjava3.core.Flowable
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class PostUseCaseRepository(
    val apiService: ApiService,
    val localDataSource: LocalDataSource
): PostUseCase {

    private val authToken = "Bearer " + localDataSource.getAuthToken()

    override fun postStory(
        description: String,
        photo: File,
        latLng: LatLng?
    ): Flowable<Resource<Post>> {
        return object : NetworkResource<Post, PostResponse>() {
            override fun createResult(data: PostResponse): Post {
                return data.map()
            }

            override fun createCall(): Flowable<PostResponse> {
                val description = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = photo.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    photo.name,
                    requestImageFile
                )
                return if(latLng != null){
                    val lat = latLng.latitude.toString().toRequestBody("text/plain".toMediaType())
                    val lon = latLng.longitude.toString().toRequestBody("text/plain".toMediaType())
                    apiService.postStoryWithLocation(authToken, imageMultipart, description, lat, lon)
                } else {
                    apiService.postStory(authToken, imageMultipart, description)
                }
            }

        }.asFlowable()
    }

}