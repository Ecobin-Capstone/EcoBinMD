package com.dicoding.ecobin.ui

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.WasteRepository
import com.dicoding.ecobin.data.response.ProfileResponse

class ProfileViewModel (private val repository: WasteRepository) : ViewModel() {
    suspend fun updateProfile(id: String, name: String, phoneNumber: String, email: String): ProfileResponse {
        return repository.updateProfile(id,name,phoneNumber,email)
    }
}