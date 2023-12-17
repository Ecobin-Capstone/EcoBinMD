package com.dicoding.ecobin.injection

import android.content.Context
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.retrofit.ApiConfigML

object MLInjection {
    fun provideRepository(context: Context): WasteRepository {
        val apiService = ApiConfigML.getApiServiceML()
        return WasteRepository.getInstance(apiService)
    }
}