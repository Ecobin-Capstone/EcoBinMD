package com.dicoding.ecobin.injection

import android.content.Context
import com.dicoding.ecobin.data.repository.MLRepository
import com.dicoding.ecobin.data.retrofit.ApiConfigML

object MLInjection {
    fun provideRepository(context: Context): MLRepository {
        val apiService = ApiConfigML.getApiServiceML()
        return MLRepository.getInstance(apiService)
    }
}