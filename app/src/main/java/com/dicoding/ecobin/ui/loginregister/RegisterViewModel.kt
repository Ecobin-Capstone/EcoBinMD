package com.dicoding.ecobin.ui.loginregister

import androidx.lifecycle.ViewModel
import com.dicoding.ecobin.data.repository.UserRepository

class RegisterViewModel (private val repository: UserRepository) : ViewModel() {
    suspend fun registerUser(name: String,phone: String, email: String, password: String, latitude : Double?, longitude: Double?)= repository.registerUser(name, email, phone, password, latitude, longitude)
}