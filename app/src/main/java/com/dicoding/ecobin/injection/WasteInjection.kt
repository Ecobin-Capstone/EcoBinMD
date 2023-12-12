package com.dicoding.ecobin.injection

import android.content.Context
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.retrofit.ApiConfig

object WasteInjection {
    fun provideRepository(context: Context): WasteRepository {
        val apiService = ApiConfig.getApiService()
        return WasteRepository.getInstance(apiService)
    }
}