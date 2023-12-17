package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.MLRepository
import okhttp3.MultipartBody

class UploadViewModel (private val repository: MLRepository) : ViewModel() {
    suspend fun uploadImage(multipartBody: MultipartBody.Part)= repository.uploadImage(multipartBody)

}