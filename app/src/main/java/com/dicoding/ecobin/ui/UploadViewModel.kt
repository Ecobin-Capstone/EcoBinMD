package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository
import okhttp3.MultipartBody

class UploadViewModel (private val repository: WasteRepository) : ViewModel() {
    suspend fun uploadImage(multipartBody: MultipartBody.Part)= repository.uploadImage(multipartBody)
}