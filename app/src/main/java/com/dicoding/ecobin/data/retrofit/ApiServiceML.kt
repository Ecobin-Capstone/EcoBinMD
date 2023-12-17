package com.dicoding.ecobin.data.retrofit

import com.dicoding.ecobin.data.response.ClassifierResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceML {
    @Multipart
    @POST("api")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
    ): Response<ClassifierResponse>
}