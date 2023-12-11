package com.dicoding.ecobin.injection

import android.content.Context
import com.dicoding.ecobin.data.repository.UserRepository
import com.dicoding.ecobin.data.retrofit.ApiConfig
import com.dicoding.ecobin.pref.UserPreference
import com.dicoding.ecobin.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}