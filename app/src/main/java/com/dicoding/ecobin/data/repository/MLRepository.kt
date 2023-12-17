package com.dicoding.ecobin.data.repository

import com.dicoding.ecobin.data.response.ClassifierResponse
import com.dicoding.ecobin.data.retrofit.ApiServiceML
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.HttpException

class MLRepository private constructor(
    private val apiServiceML: ApiServiceML,
){
    suspend fun uploadImage(multipartBody: MultipartBody.Part): ClassifierResponse {
        try {
            val successResponse = apiServiceML.uploadImage(multipartBody)
            if (successResponse.isSuccessful) {
                val responseBody = successResponse.body()
                if (responseBody != null) {
                    return responseBody
                } else {
                    return ClassifierResponse()
                }
            } else {
                return ClassifierResponse()
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ClassifierResponse::class.java)
            return errorResponse
        } catch (e: Exception) {
            return ClassifierResponse()
        }
    }
    companion object {
        @Volatile
        private var instance: MLRepository? = null
        fun getInstance(
            apiServiceML: ApiServiceML
        ): MLRepository =
            instance ?: synchronized(this) {
                instance ?: MLRepository(apiServiceML)
            }.also { instance = it }
    }
}